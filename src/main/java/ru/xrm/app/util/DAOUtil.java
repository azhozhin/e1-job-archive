package ru.xrm.app.util;

import ru.xrm.app.dao.*;
import ru.xrm.app.dao.hibernate.impl.*;

public class DAOUtil {

	private static VacancyDAO vacancyDAO = DAOFactory.getInstance().getDao(VacancyDAOHibernateImpl.class);
	private static SectionDAO sectionDAO = DAOFactory.getInstance().getDao(SectionDAOHibernateImpl.class);
	private static ScheduleDAO scheduleDAO = DAOFactory.getInstance().getDao(ScheduleDAOHibernateImpl.class);
	private static EmployerDAO employerDAO = DAOFactory.getInstance().getDao(EmployerDAOHibernateImpl.class);
	private static EducationDAO educationDAO = DAOFactory.getInstance().getDao(EducationDAOHibernateImpl.class);
	private static DutyTypeDAO dutyTypeDAO = DAOFactory.getInstance().getDao(DutyTypeDAOHibernateImpl.class);
	private static CityDAO cityDAO = DAOFactory.getInstance().getDao(CityDAOHibernateImpl.class);

	public static VacancyDAO getVacancyDAO() {
		return vacancyDAO;
	}

	public static SectionDAO getSectionDAO() {
		return sectionDAO;
	}
	public static ScheduleDAO getScheduleDAO() {
		return scheduleDAO;
	}
	public static EmployerDAO getEmployerDAO() {
		return employerDAO;
	}
	public static EducationDAO getEducationDAO() {
		return educationDAO;
	}
	public static DutyTypeDAO getDutyTypeDAO() {
		return dutyTypeDAO;
	}
	public static CityDAO getCityDAO() {
		return cityDAO;
	}
	
}
