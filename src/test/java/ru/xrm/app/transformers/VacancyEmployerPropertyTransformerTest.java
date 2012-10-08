package ru.xrm.app.transformers;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.xrm.app.domain.Employer;
import ru.xrm.app.domain.Schedule;

public class VacancyEmployerPropertyTransformerTest {

	@Test
	public void test() {
		VacancyEmployerPropertyTransformer t = new VacancyEmployerPropertyTransformer();
		
		Employer employer=new Employer(0L,"123");
		assertEquals(employer, t.transform("123"));
		assertNotNull(t.transform("321"));
		assertEquals(employer, t.transform("123"));
	}

}
