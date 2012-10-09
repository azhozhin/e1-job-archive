package ru.xrm.app.parsers;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.xrm.app.config.Config;
import ru.xrm.app.domain.City;
import ru.xrm.app.domain.DutyType;
import ru.xrm.app.domain.Education;
import ru.xrm.app.domain.Employer;
import ru.xrm.app.domain.Schedule;
import ru.xrm.app.domain.Section;
import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.util.SectionSet;

public class VacancyParserTest {

	BufferedReader br=null;
	
	@Before
	public void setup() throws IOException{
		InputStream in=getClass().getResourceAsStream("/892764.htm");
		br=new BufferedReader(new InputStreamReader(in,"windows-1251"));
	}
	
	@After
	public void teardown() throws IOException{
		if (br!=null){
			br.close();
		}
		br=null;
	}
	
	@Test
	public void test()  throws IOException{
		Config config=Config.getInstance();
		try{
			config.load("config.xml");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		SectionSet.getInstance().getSections().add(new Section("Информационные технологии и Интернет"));

		StringBuilder sb=new StringBuilder();

		String line;
		while((line=br.readLine())!=null){
			sb.append(line);
		}
		br.close();
		VacancyParser parser=new VacancyParser(config,sb.toString());
		assertEquals(sb.toString(),parser.getHtml());
		Vacancy v=parser.parse();
		assertEquals(new Long(892764), v.getId());
		assertEquals(new Long(35000),v.getSalary());
		assertEquals(new DutyType(0L, "полная"), v.getDutyType());
		assertEquals(new Education(0L,"среднее"), v.getEducation());
		assertEquals(new Long(0), v.getExperience());
		assertEquals(new Schedule(0L,"полный рабочий день"), v.getSchedule());
		assertEquals(new City(0L,"Екатеринбург"),v.getCity());
		assertEquals(new Employer(0L, "ГК \"ЭКСПРЕСС НЕДВИЖИМОСТЬ\""),v.getEmployer());
		assertEquals(new Date(112,9,8,0,0,0).toString(),v.getDate().toString());
		assertEquals("Диана, Телефон:, 89221938077", v.getContactInformation());
		assertEquals("Предприятием-работодателем",v.getPresentedBy());
		//assertEquals(v.getBody());
	}

}
