package ru.xrm.app.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class CSSQueryArgTest {

	@Test
	public void testGetterAndSetter() {
		CSSQueryArg arg;
		arg=new CSSQueryArg(null);
		assertNull(arg.getArg());
		
		arg=new CSSQueryArg("arg0");
		assertEquals("arg0", arg.getArg());
		
		arg.setArg("argx");
		assertEquals("argx", arg.getArg());
		
		assertEquals("argx", arg.toString());
	}

}
