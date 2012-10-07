package ru.xrm.app.dao.hibernate.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;

import ru.xrm.app.dao.CityDAO;
import ru.xrm.app.domain.City;

public class CityDAOHibernateImpl extends GenericDAOHibernateImpl<Long, City> implements CityDAO {

	public List<City> findAll() {
		return this.findAll(City.class);
	}

	public City findOne(Criterion... crits) {
		return this.findOne(City.class, crits);
	}

	public List<City> findMany(Criterion... crits) {
		return this.findMany(City.class, crits);
	}

}
