package ru.xrm.app;

import java.util.List;

import ru.xrm.app.config.Config;
import ru.xrm.app.config.VacancySectionProperty;

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
    	
    	List<VacancySectionProperty> listVacancySectionProperties=config.getVacancySectionProperties();
    	
    	for (VacancySectionProperty vsp:listVacancySectionProperties){
    		System.out.format("%s: %s\n", vsp.getKey(), vsp.getCssQuery());
    	}
    }
}
