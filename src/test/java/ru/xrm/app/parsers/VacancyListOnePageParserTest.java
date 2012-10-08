package ru.xrm.app.parsers;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.xrm.app.config.Config;
import ru.xrm.app.util.VacancyPage;

public class VacancyListOnePageParserTest {

	String html;
	
	@Before
	public void setup() throws IOException{
		InputStream in=getClass().getResourceAsStream("/onepageparser/index.htm");
		BufferedReader br=new BufferedReader(new InputStreamReader(in,"windows-1251"));
		String line;
		StringBuilder sb=new StringBuilder();
		while ((line=br.readLine())!=null){
			sb.append(line);
		}
		br.close();
		
		html=sb.toString();
	}
	

	@Test
	public void test() {
		Config config=Config.getInstance();
		try{
			config.load("config.xml");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		VacancyListOnePageParser parser=new VacancyListOnePageParser(config, html);
		List<VacancyPage> list=parser.getPages();
		assertEquals(8, list.size());
		assertEquals("/business/job/vacancy.search.php?section=23&sex=l&search_by=1&show_for=30&search=yes&page=1",list.get(0).getHref());
		assertEquals("/business/job/vacancy.search.php?section=23&sex=l&search_by=1&show_for=30&search=yes&page=2",list.get(1).getHref());
		assertEquals("/business/job/vacancy.search.php?section=23&sex=l&search_by=1&show_for=30&search=yes&page=3",list.get(2).getHref());
		assertEquals("/business/job/vacancy.search.php?section=23&sex=l&search_by=1&show_for=30&search=yes&page=4",list.get(3).getHref());
		assertEquals("/business/job/vacancy.search.php?section=23&sex=l&search_by=1&show_for=30&search=yes&page=5",list.get(4).getHref());
		assertEquals("/business/job/vacancy.search.php?section=23&sex=l&search_by=1&show_for=30&search=yes&page=6",list.get(5).getHref());
		assertEquals("/business/job/vacancy.search.php?section=23&sex=l&search_by=1&show_for=30&search=yes&page=7",list.get(6).getHref());
		assertEquals("/business/job/vacancy.search.php?section=23&sex=l&search_by=1&show_for=30&search=yes&page=8",list.get(7).getHref());
	}

}
