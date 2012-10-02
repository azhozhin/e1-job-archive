package ru.xrm.app;
/*
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.xrm.app.httpclient.CachingHttpFetcher;
*/
public class Try {

	public static void main(String[] args){
/*
		CachingHttpFetcher hf=CachingHttpFetcher.getInstance();

		String content;
		try {
			content = hf.fetch("http://www.e1.ru/business/job/vacancy.detail.php?id=859856", "windows-1251");

			Document doc=Jsoup.parse(content);
			Elements elems=doc.select("input[type=hidden][name=url][value~=/business/job/vacancy.detail.php\\?id=\\d]");

			for (Element e:elems){
				System.out.format("%s \n",e.attr("value"));					
				String[] parts=e.attr("value").split("=");
				System.out.format("%s \n",parts[parts.length-1]);
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
	}
}
