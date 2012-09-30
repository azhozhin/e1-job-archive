package ru.xrm.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ru.xrm.app.config.Config;
import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.domain.VacancyPage;
import ru.xrm.app.domain.VacancySection;
import ru.xrm.app.httpclient.CachingHttpFetcher;
import ru.xrm.app.httpclient.UrlHelper;
import ru.xrm.app.threads.OnePageWorker;

public class App 
{

	public static void main( String[] args )
	{
		UrlHelper urlHelper= UrlHelper.getInstance();
		CachingHttpFetcher cf;
		Config config=new Config();
		try{
			config.load("config.xml");
		}catch(Exception e){
			e.printStackTrace();
		}

		String homePage="http://e1.ru/business/job";
		String basename=urlHelper.getBasename(homePage);

		cf=CachingHttpFetcher.getInstance();
		
		

		// home page
		String content = cf.fetch(homePage,"windows-1251");

		VacancySectionParser vsp=new VacancySectionParser(config, content);
		List<VacancySection> sections=vsp.parse();

		Date d1=new Date();
		ExecutorService executorService=Executors.newFixedThreadPool(20);

		List<Future<List<Vacancy>>> allVacancyListParts=new LinkedList<Future<List<Vacancy>>>();

		// for all sections
		for (VacancySection section:sections){
			System.out.format("\n*** NEW SECTION: %s ***\n",section.getName());
			String vacancyListCurrentPageUrl=section.getHref();

			vacancyListCurrentPageUrl=urlHelper.constructAbsoluteUrl(vacancyListCurrentPageUrl, basename);

			// get first page of vacancies
			content=cf.fetch(vacancyListCurrentPageUrl,"windows-1251");
			VacancyListOnePageParser onePageParser=new VacancyListOnePageParser(config, content);
			// get pages except current, link to first page we'll get from other pages
			List<VacancyPage> pages=onePageParser.getPages();		
			// if we have just one page, then getPages returns zero size list
			if (pages.size()==0){
				System.err.println("there is ONE page per Section");
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
				vacancyNextPageUrl=urlHelper.constructAbsoluteUrl(vacancyNextPageUrl, basename);

				System.out.format("%d ",pageCounter);
				// try to add some new pages
				content=cf.fetch(vacancyNextPageUrl, "windows-1251");
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

				Future<List<Vacancy>> partVacancyList=executorService.submit(new OnePageWorker(config, vacancyNextPageUrl, basename, "windows-1251", cf));
				allVacancyListParts.add(partVacancyList);

				pageCounter++;
				if (pageCounter%30==0){
					System.out.println();
				}
			}// loop pages
			System.out.println();
			break; // try one section
		}// loop sections

		// Wait for all threads ends
		boolean allReady=false;

		do{
			allReady=true;
			for (Future<List<Vacancy>> f:allVacancyListParts){
				if (!f.isDone()){
					allReady=false;
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print(".");
		}while(!allReady);

		List<Vacancy> allVacancies=new LinkedList<Vacancy>();
		for (Future<List<Vacancy>> f:allVacancyListParts){
			try {
				allVacancies.addAll(f.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.format("Total vacancies: %s\n", allVacancies.size());
		
		executorService.shutdown();
		Date d2=new Date();
		System.out.format("\n %d ms. \n",d2.getTime()-d1.getTime());
		System.out.println("Done!");
		
		
	    
	}
}
