package ru.xrm.app.transformers;

import static org.junit.Assert.*;

import org.junit.Test;

public class VacancySalaryPropertyTransformerTest {

	@Test
	public void test() {
		VacancySalaryPropertyTransformer t=new VacancySalaryPropertyTransformer();
		assertEquals(-1, t.transform("договорная"));
		assertEquals(-1,t.transform(""));
		assertEquals(-1,t.transform(" "));
		assertEquals(10000,t.transform("10000 р"));
	}

}
