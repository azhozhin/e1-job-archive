package ru.xrm.app.transformers;

import static org.junit.Assert.*;

import org.junit.Test;

public class VacancySalaryPropertyTransformerTest {

	@Test
	public void test() {
		VacancySalaryPropertyTransformer t=new VacancySalaryPropertyTransformer();
		assertEquals(new Long(-1), t.transform("договорная"));
		assertEquals(new Long(-1),t.transform(""));
		assertEquals(new Long(-1),t.transform(" "));
		assertEquals(new Long(10000),t.transform("10000 р"));
	}

}
