package ru.xrm.app.dao.hibernate.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;

import ru.xrm.app.dao.EmployerDAO;
import ru.xrm.app.domain.Employer;

public class EmployerDAOHibernateImpl extends GenericDAOHibernateImpl<Long, Employer> implements EmployerDAO {

	public List<Employer> findAll() {
		return this.findAll(Employer.class);
	}

	public Employer findOne(Criterion... crits) {
		return this.findOne(Employer.class, crits);
	}

	public List<Employer> findMany(Criterion... crits) {
		return this.findMany(Employer.class, crits);
	}
}
