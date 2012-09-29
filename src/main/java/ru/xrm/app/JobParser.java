package ru.xrm.app;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.xrm.app.config.Config;
import ru.xrm.app.config.Entry;
import ru.xrm.app.config.VacancySectionProperty;
import ru.xrm.app.walkers.JobPropertyElementWalker;


public class JobParser {

	public void parse(){
		
		Config config=new Config();
    	try{
    		config.load("config.xml");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	List<Entry> listVacancyProperties=config.getVacancyProperties();
		
		File input = new File("input/job-859573.html");
		Document doc;
		try {
			doc = Jsoup.parse(input, "windows-1251", "http://www.e1.ru");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}

		for (Entry prop:listVacancyProperties){
			Elements elems=doc.select(prop.getCssQuery());
			String value="";
			for (Element e:elems){
			
				if (prop.getElementWalker() != null){
					e=prop.getElementWalker().walk(e);
				}
				if (prop.getElementEvaluator() !=null){
					value=prop.getElementEvaluator().evaluate(e);
				}
				if (prop.getPropertyTransformer() !=null){
					value = prop.getPropertyTransformer().transform(value).toString();
				}
				System.out.format("%s = %s\n",prop.getKey() ,value);
			}
		}
	}
}
