package ru.xrm.app.dao;

import ru.xrm.app.domain.Vacancy;

public interface VacancyDAO extends GenericDAO<Long, Vacancy>{
	public Integer countByCategoryId(Long id);
}
