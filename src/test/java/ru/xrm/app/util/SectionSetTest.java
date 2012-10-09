package ru.xrm.app.util;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.xrm.app.domain.Section;

public class SectionSetTest {

	@Test
	public void test() {
		SectionSet s=SectionSet.getInstance();
		s.reset();
		Section sect1=new Section("sect1");
		Section sect2=new Section("sect2");
		Section sect3=new Section("sect3");
		
		assertEquals(0, s.getSections().size());
		
		s.add(sect1);
		assertEquals(1, s.getSections().size());
		assertEquals(sect1, s.getByIndex(0L));
		assertEquals(sect1, s.getByName("sect1"));
		
		s.add(sect2);
		assertEquals(2, s.getSections().size());
		assertEquals(sect2, s.getByIndex(1L));
		assertEquals(sect2, s.getByName("sect2"));
		
		s.add(sect3);
		assertEquals(3, s.getSections().size());
		assertEquals(sect3, s.getByIndex(2L));
		assertEquals(sect3, s.getByName("sect3"));
	}

}
