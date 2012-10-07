package ru.xrm.app.util;

import ru.xrm.app.dao.*;
import ru.xrm.app.dao.hibernate.impl.*;

public class DAOUtil {

	private final VacancyDAO vacancyDAO;
	private final SectionDAO sectionDAO;
	private final ScheduleDAO scheduleDAO;
	private final EmployerDAO employerDAO;
	private final EducationDAO educationDAO;
	private final DutyTypeDAO dutyTypeDAO;
	private final CityDAO cityDAO;
	
	private static DAOUtil instance;
	public static synchronized DAOUtil getInstance(){
		if (instance==null){
			instance = new DAOUtil();
		}
		return instance;
	}

	private DAOUtil(){
		vacancyDAO = DAOFactory.getInstance().getDao(VacancyDAOHibernateImpl.class);
		sectionDAO = DAOFactory.getInstance().getDao(SectionDAOHibernateImpl.class);
		scheduleDAO = DAOFactory.getInstance().getDao(ScheduleDAOHibernateImpl.class);
		employerDAO = DAOFactory.getInstance().getDao(EmployerDAOHibernateImpl.class);
		educationDAO = DAOFactory.getInstance().getDao(EducationDAOHibernateImpl.class);
		dutyTypeDAO = DAOFactory.getInstance().getDao(DutyTypeDAOHibernateImpl.class);
		cityDAO = DAOFactory.getInstance().getDao(CityDAOHibernateImpl.class);
	}

	public void beginTransaction(){
		HibernateUtil.beginTransaction();
	}
	
	public void commitTransaction(){
		HibernateUtil.commitTransaction();
	}
	
	public VacancyDAO getVacancyDAO() {
		return vacancyDAO;
	}

	public SectionDAO getSectionDAO() {
		return sectionDAO;
	}
	public ScheduleDAO getScheduleDAO() {
		return scheduleDAO;
	}
	public EmployerDAO getEmployerDAO() {
		return employerDAO;
	}
	public EducationDAO getEducationDAO() {
		return educationDAO;
	}
	public DutyTypeDAO getDutyTypeDAO() {
		return dutyTypeDAO;
	}
	public CityDAO getCityDAO() {
		return cityDAO;
	}
	
}
