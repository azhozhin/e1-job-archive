package ru.xrm.app.transformers;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class JobDatePropertyTransformerTest {

	@Test
	public void testTransform() {
		JobDatePropertyTransformer t=new JobDatePropertyTransformer();
		assertEquals(new Date(112,9,8,0,0,0).toString(),t.transform("08 октября 2012").toString());
		assertEquals(new Date(112,8,28,0,0,0).toString(),t.transform("28 сентября 2012").toString());
		assertEquals(new Date(70,0,1,0,0,0).toString(),t.transform("28 мартабря 2012").toString());
		assertEquals(new Date(70,0,1,0,0,0).toString(), t.transform("-1 января 2012").toString());
		assertEquals(new Date(70,0,1,0,0,0).toString(), t.transform("1 января 2012 года").toString());
		assertEquals(new Date(70,0,1,0,0,0).toString(),t.transform("28 января 2100").toString());
		assertEquals(new Date(70,0,1,0,0,0).toString(),t.transform("32 января 2012").toString());
		assertEquals(new Date(70,0,1,0,0,0).toString(),t.transform("31 декабря 1969").toString());
	}

}
