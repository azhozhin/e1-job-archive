package ru.xrm.app.threads;

import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.xrm.app.config.Config;
import ru.xrm.app.domain.Section;
import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.httpclient.CachingHttpFetcher;
import ru.xrm.app.httpclient.UrlHelper;
import ru.xrm.app.parsers.VacancyListOnePageParser;
import ru.xrm.app.parsers.VacancySectionParser;
import ru.xrm.app.util.SectionSet;
import ru.xrm.app.util.VacancyPage;

public class WholeSiteWorker implements Runnable {
	
	protected Logger log = LoggerFactory.getLogger( WholeSiteWorker.class );

	private List<Future<List<Vacancy>>> allVacancyListParts;
	private String encoding;
	private int threadNumber;

	public WholeSiteWorker(List<Future<List<Vacancy>>> allVacancyListParts, String encoding, int threadNumber){
		this.allVacancyListParts=allVacancyListParts;
		this.encoding=encoding;
		this.threadNumber=threadNumber;
	}

	public void run() {
		Date d1=new Date();
		
		String homePage="http://e1.ru/business/job";

		Config config=Config.getInstance();

		String basename=UrlHelper.getBasename(homePage);

		CachingHttpFetcher cf=CachingHttpFetcher.getInstance();

		// home page
		String content = cf.fetchWithRetry(homePage,encoding,1000);

		VacancySectionParser vacancySectionParser=new VacancySectionParser(config, content);
		vacancySectionParser.parse();

		List<Section> sections=SectionSet.getInstance().getSections();
		
		ExecutorService executorService=Executors.newFixedThreadPool(threadNumber);


		// for all sections
		for (Section section:sections){
			log.info(String.format("*** NEW SECTION: %s ***",section.getName()));
			String vacancyListCurrentPageUrl=section.getHref();

			vacancyListCurrentPageUrl=UrlHelper.constructAbsoluteUrl(vacancyListCurrentPageUrl, basename);

			// get first page of vacancies
			content=cf.fetchWithRetry(vacancyListCurrentPageUrl,encoding,1000);
			VacancyListOnePageParser onePageParser=new VacancyListOnePageParser(config, content);
			// get pages except current, link to first page we'll get from other pages
			List<VacancyPage> pages=onePageParser.getPages();		
			// if we have just one page, then getPages returns zero size list
			if (pages.size()==0){
				log.info("there is ONE page per Section");
				pages.add(new VacancyPage(vacancyListCurrentPageUrl));
			}

			List<VacancyPage> allSectionPages=new ArrayList<VacancyPage>(pages);

			Deque<VacancyPage> pageQueue = new LinkedList<VacancyPage>();
			// push all pages to pageQueue
			for (VacancyPage p:pages){
				pageQueue.addLast(p);
			}

			int pageCounter=0;

			// loop through all pages
			while(true){
				if (pageQueue.isEmpty())break;
				VacancyPage p=pageQueue.poll();
				String vacancyNextPageUrl=p.getHref();
				vacancyNextPageUrl=UrlHelper.constructAbsoluteUrl(vacancyNextPageUrl, basename);

				// try to add some new pages
				content=cf.fetchWithRetry(vacancyNextPageUrl, encoding, 1000);
				if (content==null){
					log.error(String.format("Cannot download page %s adding it to end of queue",vacancyNextPageUrl));
					pageQueue.addLast(p);
					continue;
				}

				onePageParser.setHtml(content);
				List<VacancyPage> newPages=onePageParser.getPages();
				for (VacancyPage newPage:newPages){
					boolean exists=false;
					for (VacancyPage ap:allSectionPages){
						if (ap.getHref().equals(newPage.getHref())){
							exists=true;
						}
					}

					if (!exists){
						allSectionPages.add(newPage);
						pageQueue.addLast(newPage);
					}
				}

				Future<List<Vacancy>> partVacancyList=executorService.submit(new OnePageWorker(config, vacancyNextPageUrl, basename, encoding, cf));
				allVacancyListParts.add(partVacancyList);

				pageCounter++;
			}// loop pages
		}// loop sections


		log.info("<<< Done filling pages >>>");

		executorService.shutdown();
		
		Date d2=new Date();
		log.info(String.format("Total time: %d ms. ",d2.getTime()-d1.getTime()));
	}
}
