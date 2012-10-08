package ru.xrm.app.httpclient;

import static org.junit.Assert.*;

import org.junit.Test;

public class UrlHelperTest {

	@Test
	public void test() {
		UrlHelper helper=UrlHelper.getInstance();
		assertEquals("http://ya.ru", helper.getBasename("http://ya.ru/search"));
		assertEquals("http://ya.ru/index.html", helper.constructAbsoluteUrl("/index.html", "http://ya.ru"));
		assertEquals("http://ya.ru/index.html", helper.constructAbsoluteUrl("http://ya.ru/index.html", ""));
		UrlHelper helper2=UrlHelper.getInstance();
		assertEquals("1b556b44a4ee73524fb009e11918fb4f", helper2.encode("http://ya.ru"));
		assertEquals("c7b920f57e553df2bb68272f61570210", helper2.encode("http://google.com"));
	}

}
