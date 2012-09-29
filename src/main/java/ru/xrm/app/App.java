package ru.xrm.app;

import java.util.Date;
import java.util.List;

import ru.xrm.app.config.Config;

import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.domain.VacancyLink;
import ru.xrm.app.domain.VacancySection;
import ru.xrm.app.httpclient.CachingHttpFetcher;
import ru.xrm.app.httpclient.UrlHelper;


/**
 * Hello world!
 *
 */
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
    	Date d1=new Date();
    	Date d2;

    	String content = hf.fetch(homePage,"windows-1251");
    	
    	d2=new Date();
    	System.out.format("Fetching time %s\n",d2.getTime()-d1.getTime());
    	
    	VacancySectionParser vsp=new VacancySectionParser(config, content);
    	List<VacancySection> s=vsp.parse();
    	
    	//System.out.println(s);
    	String vacancyListUrl=s.get(0).getHref();
    	
    	if (vacancyListUrl.charAt(0)=='/'){
    		// relative url
    		vacancyListUrl=urlHelper.getBasename(homePage)+vacancyListUrl;
    	}
    	content=hf.fetch(vacancyListUrl,"windows-1251");
    	VacancyListOnePageParser vlopp=new VacancyListOnePageParser(config, content);
    	List<VacancyLink> lvl = vlopp.parse();
    	//System.out.format("%s \n size: %d",lvl,lvl.size());
    	for (VacancyLink vl: lvl){
    		
    		String link=vl.getHref();
    		if (link.charAt(0)=='/'){
    			link=urlHelper.getBasename(homePage) + link;
    		}
    		System.out.format("Fetching %s\n",link);
    		content=hf.fetch(link, "windows-1251");
        	VacancyParser vacancyParser=new VacancyParser(config, content);
        	Vacancy v=vacancyParser.parse();
        	System.out.println(v);
    	}
    	//UrlEncoder ue=new UrlEncoder();
    	
    }
}
