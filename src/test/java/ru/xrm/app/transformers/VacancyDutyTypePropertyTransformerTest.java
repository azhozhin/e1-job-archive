package ru.xrm.app.transformers;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.xrm.app.domain.DutyType;
import ru.xrm.app.domain.Schedule;

public class VacancyDutyTypePropertyTransformerTest {

	@Test
	public void test() {
		VacancyDutyTypePropertyTransformer t=new VacancyDutyTypePropertyTransformer();
		
		DutyType dutyType=new DutyType(0L,"123");
		assertEquals(dutyType, t.transform("123"));
		assertNotNull(t.transform("321"));
		assertEquals(dutyType, t.transform("123"));
	}

}
