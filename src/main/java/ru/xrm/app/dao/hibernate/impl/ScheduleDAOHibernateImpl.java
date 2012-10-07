package ru.xrm.app.dao.hibernate.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;

import ru.xrm.app.dao.ScheduleDAO;
import ru.xrm.app.domain.Schedule;

public class ScheduleDAOHibernateImpl extends GenericDAOHibernateImpl<Long, Schedule> implements ScheduleDAO {

	public List<Schedule> findAll() {
		return this.findAll(Schedule.class);
	}

	public Schedule findOne(Criterion... crits) {
		return this.findOne(Schedule.class, crits);
	}

	public List<Schedule> findMany(Criterion... crits) {
		return this.findMany(Schedule.class, crits);
	}

}
