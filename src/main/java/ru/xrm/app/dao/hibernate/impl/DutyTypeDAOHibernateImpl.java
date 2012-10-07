
package ru.xrm.app.dao.hibernate.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;

import ru.xrm.app.dao.DutyTypeDAO;
import ru.xrm.app.domain.DutyType;


public class DutyTypeDAOHibernateImpl extends GenericDAOHibernateImpl<Long, DutyType> implements DutyTypeDAO {

	public List<DutyType> findAll() {
		return this.findAll(DutyType.class);
	}

	public DutyType findOne(Criterion... crits) {
		return this.findOne(DutyType.class, crits);
	}

	public List<DutyType> findMany(Criterion... crits) {
		return this.findMany(DutyType.class, crits);
	}

}
