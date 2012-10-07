package ru.xrm.app.dao;

import java.util.List;

import ru.xrm.app.domain.Vacancy;

public interface VacancyDAO extends GenericDAO<Long, Vacancy>{
	public Integer countByCategoryId(Long categoryId);
	public List<Vacancy> findByCategoryIdPagination(Long categoryId, Integer startPosition, Integer perPage);
}
