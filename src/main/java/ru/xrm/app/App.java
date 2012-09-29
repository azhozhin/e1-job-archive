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
    	JobParser jp=new JobParser();
    	jp.parse();

    }
}
