package ru.xrm.app;

import java.util.List;

import ru.xrm.app.config.Config;

import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.domain.VacancyLink;
import ru.xrm.app.domain.VacancySection;
import ru.xrm.app.httpclient.CachingHttpFetcher;
import ru.xrm.app.httpclient.UrlHelper;

public class App 
{
	
    public static void main( String[] args )
    {
    	UrlHelper urlHelper= new UrlHelper();
    	Config config=new Config();
    	try{
    		config.load("config.xml");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	String homePage="http://e1.ru/business/job";
    	
    	CachingHttpFetcher hf=new CachingHttpFetcher("cache");

    	// home page
    	String content = hf.fetch(homePage,"windows-1251");
    	
    	VacancySectionParser vsp=new VacancySectionParser(config, content);
    	List<VacancySection> s=vsp.parse();
    	
    	// get one kindOfBusiness
    	String vacancyListUrl=s.get(0).getHref();
    	
    	if (vacancyListUrl.charAt(0)=='/'){
    		// relative url
    		vacancyListUrl=urlHelper.getBasename(homePage)+vacancyListUrl;
    	}
    	
    	// get first page of vacancies
    	content=hf.fetch(vacancyListUrl,"windows-1251");
    	VacancyListOnePageParser vlopp=new VacancyListOnePageParser(config, content);
    	List<VacancyLink> lvl = vlopp.parse();

       	for (VacancyLink vl: lvl){
    		
    		String link=vl.getHref();
    		if (link.charAt(0)=='/'){
    			link=urlHelper.getBasename(homePage) + link;
    		}
    		System.out.format("Fetching %s\n",link);
    		
    		// get vacancy itself
    		content=hf.fetch(link, "windows-1251");
        	VacancyParser vacancyParser=new VacancyParser(config, content);
        	Vacancy v=vacancyParser.parse();
        	System.out.println(v);
    	}
    	
    }
}
