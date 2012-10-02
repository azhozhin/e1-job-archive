package ru.xrm.app.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NamedCSSQueryTest {

	@Test
	public void testGettersAndSetters() {
		NamedCSSQuery q;

		q=new NamedCSSQuery("", "", 0);

		assertEquals("", q.getName());
		assertEquals("", q.getCssQuery());
		assertEquals(0, q.getCssArgsCount());
		
		q.setName("query1");
		assertEquals("query1", q.getName());
		
		final String QUERY_SIMPLE="query simple";
		q.setCssQuery(QUERY_SIMPLE);
		assertEquals(QUERY_SIMPLE, q.getCssQuery());
		

		q.setCssArgsCount(1);

		assertEquals(1, q.getCssArgsCount());
		
		q=new NamedCSSQuery("", "", 0);
		q.setCssArgsCount(-1);
		
		assertEquals(0, q.getCssArgsCount());
	}

}
