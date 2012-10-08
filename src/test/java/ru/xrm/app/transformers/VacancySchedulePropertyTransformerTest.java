package ru.xrm.app.transformers;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.xrm.app.domain.Schedule;

public class VacancySchedulePropertyTransformerTest {

	@Test
	public void test() {
		VacancySchedulePropertyTransformer t=new VacancySchedulePropertyTransformer();
		
		Schedule schedule=new Schedule(0L,"123");
		assertEquals(schedule, t.transform("123"));
		assertNotNull(t.transform("321"));
		assertEquals(schedule, t.transform("123"));
	}

}
