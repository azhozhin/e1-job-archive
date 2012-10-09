package ru.xrm.app.transformers;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.xrm.app.domain.Section;
import ru.xrm.app.util.SectionSet;

public class VacancySectionPropertyTransformerTest {

	@Test
	public void test() {
		VacancySectionPropertyTransformer t=new VacancySectionPropertyTransformer();
		
		Section section1=new Section("section1");
		Section section2=new Section("section2");
		SectionSet s=SectionSet.getInstance();
		s.add(section1);
		s.add(section2);
		
		assertEquals(section1, t.transform("section1"));
		assertEquals(section2, t.transform("section2"));
		assertNull(t.transform("section3"));
		
		
	}

}
