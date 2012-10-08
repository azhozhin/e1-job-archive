package ru.xrm.app.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class DAOUtilTest {

	@Test
	public void test() {
		assertEquals(DAOUtil.getInstance().getCityDAO().getClass().getName(), "ru.xrm.app.dao.hibernate.impl.CityDAOHibernateImpl");
		assertEquals(DAOUtil.getInstance().getDutyTypeDAO().getClass().getName(), "ru.xrm.app.dao.hibernate.impl.DutyTypeDAOHibernateImpl");
		assertEquals(DAOUtil.getInstance().getEducationDAO().getClass().getName(), "ru.xrm.app.dao.hibernate.impl.EducationDAOHibernateImpl");
		assertEquals(DAOUtil.getInstance().getEmployerDAO().getClass().getName(), "ru.xrm.app.dao.hibernate.impl.EmployerDAOHibernateImpl");
		assertEquals(DAOUtil.getInstance().getScheduleDAO().getClass().getName(), "ru.xrm.app.dao.hibernate.impl.ScheduleDAOHibernateImpl");
		assertEquals(DAOUtil.getInstance().getSectionDAO().getClass().getName(), "ru.xrm.app.dao.hibernate.impl.SectionDAOHibernateImpl");
		assertEquals(DAOUtil.getInstance().getVacancyDAO().getClass().getName(), "ru.xrm.app.dao.hibernate.impl.VacancyDAOHibernateImpl");
	}

}
