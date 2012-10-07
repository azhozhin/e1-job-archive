package ru.xrm.app.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;

public interface GenericDAO<ID extends Serializable, T> {
	 
    public void save(T entity);
 
    public void merge(T entity);
 
    public void delete(T entity);
  
    public T findOne(Query query);
    
    public T findOne(Class clazz, Criterion... crits);
    
    public T findOne(Criterion... crits);
 
    @SuppressWarnings("rawtypes")
	public T findByID(Class clazz, ID id);
    
    public List<T> findMany(Query query);
    
    public List<T> findMany(Class clazz, Criterion... crits);
    
    public List<T> findMany(Criterion... crits);
    
    @SuppressWarnings("rawtypes")
	public List<T> findAll(Class clazz);
    
    public List<T> findAll();
}