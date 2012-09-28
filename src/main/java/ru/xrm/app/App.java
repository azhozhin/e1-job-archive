package ru.xrm.app;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
    	Config c=new Config();
    	try{
    		c.load("config.xml");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
