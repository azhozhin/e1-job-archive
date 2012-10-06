package ru.xrm.app.dao.hibernate.impl;

import org.hibernate.Session;

import ru.xrm.app.dao.VacancyDAO;
import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.util.HibernateUtil;

public class VacancyDAOHibernateImpl extends GenericDAOHibernateImpl<Long, Vacancy> implements VacancyDAO {

	@Override
	public Integer countByCategoryId(Long id) {
		Session session=HibernateUtil.getSession();
		Integer result = ((Long) session.getNamedQuery("Vacancy.countByCategoryId").setParameter("id", id).uniqueResult()).intValue();
		return result;
	}
}
