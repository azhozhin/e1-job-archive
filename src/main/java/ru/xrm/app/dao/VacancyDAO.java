package ru.xrm.app.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

import ru.xrm.app.domain.Vacancy;

public interface VacancyDAO extends GenericDAO<Long, Vacancy>{
	public Long countByCategoryId(Long categoryId);
	public List<Vacancy> findByCategoryIdPagination(Long categoryId, Long startPosition, Long perPage);
	public Long countByCriterions(Criterion... crits);
	public List<Vacancy> findManyPagination(Long startPosition, Long perPage, Criterion... crits);
}
