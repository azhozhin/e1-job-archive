package ru.xrm.app.dao.hibernate.impl;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;

import ru.xrm.app.dao.VacancyDAO;
import ru.xrm.app.domain.Vacancy;
import ru.xrm.app.util.HibernateUtil;

public class VacancyDAOHibernateImpl extends GenericDAOHibernateImpl<Long, Vacancy> implements VacancyDAO {

	public Long countByCategoryId(Long categoryId) {
		Session session=HibernateUtil.getSession();
		Long result = (Long) session.getNamedQuery("Vacancy.countByCategoryId")
				.setParameter("id", categoryId)
				.uniqueResult();
		return result;
	}

	public List<Vacancy> findByCategoryIdPagination(Long categoryId, Long startPosition, Long perPage) {
		Session session = HibernateUtil.getSession();
		@SuppressWarnings("unchecked")
		List<Vacancy> result = (List<Vacancy>)session.getNamedQuery("Vacancy.findByCategoryId")
				.setParameter("id", categoryId)
				.setFirstResult(startPosition.intValue())
				.setMaxResults(perPage.intValue())
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

	public Long countByCriterions(Criterion... crits) {
		Session session=HibernateUtil.getSession();
		Criteria criteria=session.createCriteria(Vacancy.class);
		for (Criterion c:crits){
			criteria.add(c);
		}
		criteria.setProjection(Projections.rowCount());
		Long result = (Long)criteria.uniqueResult();
		return result;
	}

	public List<Vacancy> findManyPagination(Long startPosition, Long perPage, Criterion... crits) {
		Session session=HibernateUtil.getSession();
		Criteria criteria=session.createCriteria(Vacancy.class);
		for (Criterion c:crits){
			criteria.add(c);
		}
		criteria.setFirstResult(startPosition.intValue());
		criteria.setMaxResults(perPage.intValue());
		@SuppressWarnings("unchecked")
		List<Vacancy> result=(List<Vacancy>)criteria.list();
		return result;
	}

	
}
