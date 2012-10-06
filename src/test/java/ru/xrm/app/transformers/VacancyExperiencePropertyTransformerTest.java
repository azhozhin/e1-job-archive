package ru.xrm.app.transformers;

import static org.junit.Assert.*;

import org.junit.Test;

public class VacancyExperiencePropertyTransformerTest {

	@Test
	public void testTransform() {
		VacancyExperiencePropertyTransformer transformer=new VacancyExperiencePropertyTransformer();
		assertEquals(0, transformer.transform("без опыта работы"));
		assertEquals(1, transformer.transform("1 год"));
		assertEquals(2, transformer.transform("2 года"));
		assertEquals(5, transformer.transform("5 лет"));
		assertEquals(0, transformer.transform("-2 года"));
	}

}
