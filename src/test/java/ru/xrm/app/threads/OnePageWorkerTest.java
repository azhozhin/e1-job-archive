package ru.xrm.app.threads;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ru.xrm.app.config.Config;
import ru.xrm.app.domain.Section;
import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.httpclient.CachingHttpFetcher;
import ru.xrm.app.util.SectionSet;


public class OnePageWorkerTest {

	
	Map<String,String> pageContent=new HashMap<String, String>();
	
	@Before
	public void setup() throws IOException{
		//prepare section
		SectionSet.getInstance().getSections().add(new Section("Государственная служба"));
				
		BufferedReader br;

		Map<String,String> urlMapper = new HashMap<String,String>();
		
		urlMapper.put("http://www.e1.ru/business/job/vacancy.search.php?search=yes&section=23", "/onepageparser/index.htm");
		urlMapper.put("http://www.e1.ru/business/job/vacancy.detail.php?id=856082", "/onepageparser/856082.htm");
		urlMapper.put("http://www.e1.ru/business/job/vacancy.detail.php?id=672576", "/onepageparser/672576.htm");
		urlMapper.put("http://www.e1.ru/business/job/vacancy.detail.php?id=860615", "/onepageparser/860615.htm");
		urlMapper.put("http://www.e1.ru/business/job/vacancy.detail.php?id=796540", "/onepageparser/796540.htm");
		urlMapper.put("http://www.e1.ru/business/job/vacancy.detail.php?id=798458", "/onepageparser/798458.htm");
		urlMapper.put("http://www.e1.ru/business/job/vacancy.detail.php?id=870011", "/onepageparser/870011.htm");
		urlMapper.put("http://www.e1.ru/business/job/vacancy.detail.php?id=859545", "/onepageparser/859545.htm");
		urlMapper.put("http://www.e1.ru/business/job/vacancy.detail.php?id=859516", "/onepageparser/859516.htm");
		urlMapper.put("http://www.e1.ru/business/job/vacancy.detail.php?id=859573", "/onepageparser/859573.htm");
		urlMapper.put("http://www.e1.ru/business/job/vacancy.detail.php?id=860588", "/onepageparser/860588.htm");
		
		// get all content
		for (String url:urlMapper.keySet()){
			String filename=urlMapper.get(url);
			
			InputStream in=getClass().getResourceAsStream(filename);
			br=new BufferedReader(new InputStreamReader(in, "windows-1251"));	
		
			StringBuilder content=new StringBuilder();
			String line;
			
			while((line=br.readLine())!=null){
				content.append(line);
			}
			br.close();

			pageContent.put(url, content.toString());
		}
	}
	
	@Test
	public void test() throws Exception {
		
		Config config=Config.getInstance();
		try{
			config.load("config.xml");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		CachingHttpFetcher fetcher=mock(CachingHttpFetcher.class);
		// Mock fetcher
		for (String url:pageContent.keySet()){
			when(fetcher.fetch(url, "windows-1251")).thenReturn(pageContent.get(url));
		}

		OnePageWorker worker=new OnePageWorker(config, "http://www.e1.ru/business/job/vacancy.search.php?search=yes&section=23", "http://www.e1.ru", "windows-1251", fetcher);
		
		List<Vacancy> list=worker.call();
		assertEquals(10, list.size());
		assertEquals("Сотрудник в офис",list.get(0).getJobTitle());
		assertEquals("Ведущий специалист по работе с проблемной задолженностью (выездная группа)", list.get(1).getJobTitle());
		assertEquals("Офис-менеджер",list.get(2).getJobTitle());
		assertEquals("помощник по организационным вопросам",list.get(3).getJobTitle());
		assertEquals("Административный помощник",list.get(4).getJobTitle());
		assertEquals("Сотрудник на телефон",list.get(5).getJobTitle());
		assertEquals("помощник офис-менеджера",list.get(6).getJobTitle());
		assertEquals("администратор",list.get(7).getJobTitle());
		assertEquals("помощник руководителя",list.get(8).getJobTitle());
		assertEquals("Оператор",list.get(9).getJobTitle());

	}

}
