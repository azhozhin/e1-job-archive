package ru.xrm.app.dao.hibernate.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;

import ru.xrm.app.dao.EducationDAO;
import ru.xrm.app.domain.Education;

public class EducationDAOHibernateImpl extends GenericDAOHibernateImpl<Long, Education> implements EducationDAO {

	public List<Education> findAll() {
		return this.findAll(Education.class);
	}

	public Education findOne(Criterion... crits) {
		return this.findOne(Education.class, crits);
	}

	public List<Education> findMany(Criterion... crits) {
		return this.findMany(Education.class, crits);
	}

}
