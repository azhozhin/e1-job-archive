package ru.xrm.app;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import ru.xrm.app.config.Config;
import ru.xrm.app.config.Entry;
import ru.xrm.app.domain.VacancySection;
import ru.xrm.app.httpclient.CachingHttpFetcher;
import ru.xrm.app.httpclient.UrlEncoder;


/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
    	Config config=new Config();
    	try{
    		config.load("config.xml");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	CachingHttpFetcher hf=new CachingHttpFetcher("cache");
    	Date d1=new Date();
    	Date d2;

    	String content = hf.fetch("http://e1.ru/business/job","windows-1251");
    	
    	d2=new Date();
    	System.out.format("Fetching time %s\n",d2.getTime()-d1.getTime());
    	
    	VacancySectionParser sp=new VacancySectionParser(config, content);
    	VacancySection s=sp.parse();
    	
    	System.out.println(s);
    	//UrlEncoder ue=new UrlEncoder();
    	
    }
}
