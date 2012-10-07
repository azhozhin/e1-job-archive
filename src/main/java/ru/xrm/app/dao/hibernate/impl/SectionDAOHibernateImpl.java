package ru.xrm.app.dao.hibernate.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;

import ru.xrm.app.dao.SectionDAO;
import ru.xrm.app.domain.Section;

public class SectionDAOHibernateImpl extends GenericDAOHibernateImpl<Long, Section> implements SectionDAO {

	public List<Section> findAll() {
		return this.findAll(Section.class);
	}

	public Section findOne(Criterion... crits) {
		return this.findOne(Section.class, crits);
	}

	public List<Section> findMany(Criterion... crits) {
		return this.findMany(Section.class, crits);
	}

}
