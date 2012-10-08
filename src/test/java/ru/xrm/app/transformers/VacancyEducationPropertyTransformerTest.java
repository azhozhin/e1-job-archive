package ru.xrm.app.transformers;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.xrm.app.domain.Education;

public class VacancyEducationPropertyTransformerTest {

	@Test
	public void test() {
		VacancyEducationPropertyTransformer t=new VacancyEducationPropertyTransformer();
		
		Education education=new Education(0L,"123");
		assertEquals(education, t.transform("123"));
		assertNotNull(t.transform("321"));
		assertEquals(education, t.transform("123"));
	}

}
