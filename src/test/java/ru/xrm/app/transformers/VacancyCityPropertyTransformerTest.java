package ru.xrm.app.transformers;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.xrm.app.domain.City;
import ru.xrm.app.domain.Schedule;

public class VacancyCityPropertyTransformerTest {

	@Test
	public void test() {
		VacancyCityPropertyTransformer t = new VacancyCityPropertyTransformer();
		
		City city=new City(0L,"123");
		assertEquals(city, t.transform("123"));
		assertNotNull(t.transform("321"));
		assertEquals(city, t.transform("123"));
	}

}
