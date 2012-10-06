package ru.xrm.app.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;
	
	static{
		try{
			sessionFactory=new AnnotationConfiguration().configure().buildSessionFactory();
		}catch(Throwable e){
			System.err.println("Initial SessionFactory creation failed." + e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	public static Session beginTransaction(){
		Session session=HibernateUtil.getSession();
		session.beginTransaction();
		return session;
	}
	
	public static void commitTransaction(){
		HibernateUtil.getSession().getTransaction().commit();
	}
	
	public static void rollbackTransaction(){
		HibernateUtil.getSession().getTransaction().rollback();
	}
	
	public static void closeSession(){
		HibernateUtil.getSession().close();
	}
	
	public static Session getSession(){
		Session session=sessionFactory.getCurrentSession();
		return session;
	}
}
