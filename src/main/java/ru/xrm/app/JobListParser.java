package ru.xrm.app;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JobListParser {

	public void parse(){
		File input = new File("input/section-23.html");
		Document doc;
		try {
			doc = Jsoup.parse(input, "windows-1251", "http://www.e1.ru");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}

		Elements links = doc.select("div[id~=vacancy_item_\\d] a[href~=id=\\d]"); 

		for (Element e:links){
			//System.out.format("%s \n",e.outerHtml());
			System.out.format("[%s] %s\n",e.attr("href"),e.html());
		}
	}
}
