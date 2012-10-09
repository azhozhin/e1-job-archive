package ru.xrm.app.transformers;

import static org.junit.Assert.*;

import org.junit.Test;

public class VacancyExperiencePropertyTransformerTest {

	@Test
	public void testTransform() {
		VacancyExperiencePropertyTransformer transformer=new VacancyExperiencePropertyTransformer();
		assertEquals(new Long(0), transformer.transform("без опыта работы"));
		assertEquals(new Long(1), transformer.transform("1 год"));
		assertEquals(new Long(2), transformer.transform("2 года"));
		assertEquals(new Long(5), transformer.transform("5 лет"));
		assertEquals(new Long(0), transformer.transform("-2 года"));
	}

}
