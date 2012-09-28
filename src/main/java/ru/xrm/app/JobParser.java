package ru.xrm.app;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.xrm.app.walkers.JobPropertyElementWalker;


public class JobParser {

	public void parse(){
		
		File input = new File("input/job-859573.html");
		Document doc;
		try {
			doc = Jsoup.parse(input, "windows-1251", "http://www.e1.ru");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}

		String[] keys=new String[]{ 
				"Должность", 
				"Занятость", 
				"Образование", 
				"Опыт работы", 
				"График работы", 
				"Отрасль", 
				"Город", 
				"Работодатель",
				"Дата",
				"Контактная информация", 
				"Вакансия предоставлена"};
		JobPropertyElementWalker jobPropertyTransform=new JobPropertyElementWalker();
		for (String key: keys){
			Elements elems= doc.select(String.format("td[width=30%%][align=right][valign=top] > strong:contains(%s)",key)); 
		
			for (Element e:elems){
				Element jobPropertyValue=jobPropertyTransform.walk(e);
				String value=jobPropertyValue.text();
				System.out.format("%s = %s\n",key ,value);
			}
		}
		
		Elements elems= doc.select("td[colspan=2][valign=top]");
		for (Element e:elems){
			System.out.format("%s\n", e.html());
		}
	}
}
