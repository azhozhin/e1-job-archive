package ru.xrm.app.transformers;

import static org.junit.Assert.*;

import org.junit.Test;

public class VacancySectionIdTest {

	@Test
	public void test() {
		VacancySectionId t=new VacancySectionId();
		assertEquals(889480L, t.transform("http://www.e1.ru/business/job/vacancy.detail.php?id=889480"));
		assertEquals(830433L, t.transform("http://www.e1.ru/business/job/vacancy.detail.php?id=830433"));
	}

}
