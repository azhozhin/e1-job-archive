package ru.xrm.app.dao.hibernate.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import ru.xrm.app.dao.VacancyDAO;
import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.util.HibernateUtil;

public class VacancyDAOHibernateImpl extends GenericDAOHibernateImpl<Long, Vacancy> implements VacancyDAO {

	public Integer countByCategoryId(Long categoryId) {
		Session session=HibernateUtil.getSession();
		Integer result = ((Long) session.getNamedQuery("Vacancy.countByCategoryId")
				.setParameter("id", categoryId)
				.uniqueResult())
				.intValue();
		return result;
	}

	public List<Vacancy> findByCategoryIdPagination(Long categoryId, Integer startPosition, Integer perPage) {
		Session session = HibernateUtil.getSession();
		List<Vacancy> result = (List<Vacancy>)session.getNamedQuery("Vacancy.findByCategoryId")
				.setParameter("id", categoryId)
				.setFirstResult(startPosition)
				.setMaxResults(perPage)
				.list();
		return result;
	}

	public List<Vacancy> findAll() {
		return this.findAll(Vacancy.class);
	}

	public Vacancy findOne(Criterion... crits) {
		return this.findOne(Vacancy.class, crits);
	}

	public List<Vacancy> findMany(Criterion... crits) {
		return this.findMany(Vacancy.class, crits);
	}

	
}
