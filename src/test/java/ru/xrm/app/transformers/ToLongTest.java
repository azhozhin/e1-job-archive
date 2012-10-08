package ru.xrm.app.transformers;

import static org.junit.Assert.*;

import org.junit.Test;

public class ToLongTest {

	@Test
	public void test() {
		ToLong t=new ToLong();
		assertEquals(123456789L, t.transform("123456789"));
	}

}
