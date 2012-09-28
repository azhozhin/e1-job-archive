package ru.xrm.app;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SectionParser {

	private static final String E1_SECTION_TAG_SEARCH = "div[id~=secion_\\d] a[href~=section=\\d]";
	public void parse(){
		Map<String, String> sections = new HashMap<String,String>();  
		File input = new File("input/job-index.html");
		Document doc;
		try {
			doc = Jsoup.parse(input, "windows-1251", "http://www.e1.ru/business/job");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}

		Elements links = doc.select(E1_SECTION_TAG_SEARCH); 

		for (Element e:links){
			//System.out.format("%s \n",e.outerHtml());
			System.out.format("[%s] %s\n",e.attr("href"),e.html());
			sections.put(e.attr("href"), e.html());
		}
	}
}
