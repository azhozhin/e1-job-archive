package ru.xrm.app.dao.hibernate.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import ru.xrm.app.dao.GenericDAO;
import ru.xrm.app.util.HibernateUtil;

public abstract class GenericDAOHibernateImpl<ID extends Serializable, T> implements GenericDAO<ID,T> {

	public void save(T entity) {
		Session session=HibernateUtil.getSession();
		session.saveOrUpdate(entity);
	}

	public void merge(T entity) {
		Session session=HibernateUtil.getSession();
		session.merge(entity);
	}

	public void delete(T entity) {
		Session session = HibernateUtil.getSession();
		session.delete(entity);
	}

	
	@SuppressWarnings("unchecked")
	public T findOne(Query query) {
		T result=(T) query.uniqueResult();
		return result;
	}
	
	public T findOne(Class clazz, Criterion... crits){
		Session session=HibernateUtil.getSession();
		Criteria criteria=session.createCriteria(clazz);
		for (Criterion c:crits){
			criteria.add(c);
		}
		return (T) criteria.uniqueResult();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public T findByID(Class clazz, ID id) {
		Session session=HibernateUtil.getSession();
		T result = (T) session.get(clazz, id);
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<T> findMany(Query query) {
		List<T> result;
		result=(List<T>)query.list();
		return result;
	}
	
	public List<T> findMany(Class clazz, Criterion... crits){
		Session session=HibernateUtil.getSession();
		Criteria criteria=session.createCriteria(clazz);
		for (Criterion c:crits){
			criteria.add(c);
		}
		return (List<T>) criteria.list();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findAll(Class clazz) {
		Session session=HibernateUtil.getSession();
		List<T> result=session.createQuery("from "+clazz.getName()).list();
		return result;
	}

}
