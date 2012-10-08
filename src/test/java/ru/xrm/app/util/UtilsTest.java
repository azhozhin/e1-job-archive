package ru.xrm.app.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void test() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		VacancyLink link=new VacancyLink();
		link.setHref("http://ya.ru");
		assertEquals("http://ya.ru", link.getHref());
		link.setProperty("href","http://google.com");
		assertEquals("http://google.com", link.getHref());
		
		VacancyPage page=new VacancyPage("http://www.ru");
		assertEquals("http://www.ru", page.getHref());
		page.setHref("http://ya.ru");
		assertEquals("http://ya.ru", page.getHref());
		page.setProperty("href","http://google.com");
		assertEquals("http://google.com", page.getHref());
	}

}
