package com.hibernate.connect;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

public class Passerelle {
	 private static Session session = null;
	 private static SessionFactory sessionFactory = null;
	 private static Transaction transaction = null;
	
	static void initHibernate()
	{
		try
		{
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
	        session = sessionFactory.openSession();
		}
		catch (HibernateException ex)
		{
			throw new RuntimeException("Probleme de configuration : " + ex.getMessage(), ex);
		}  
	}
	
	public static void open()
	{
	    if (sessionFactory == null) {
	        initHibernate();    
	    }
	    if (!isOpened()){
	        session = sessionFactory.openSession();
	    }
	}

	public static boolean isOpened()
	{
		return session != null && session.isOpen();
	}

	public static void close()
	{
		if (isOpened()) {
			session.close();		
		}
	}
	
	public static void delete(Object o)
	{
		if (!isOpened()) {
			open();
		}
		
		transaction = session.beginTransaction();
		session.delete(o);
		transaction.commit();
		transaction = null;
		session.flush();
	}

	public static void save(Object o)
	{
		if (!isOpened()) {
			open();
		}
		
		session.beginTransaction();
		session.saveOrUpdate(o);
		session.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getData(String className)
	{
		if (!isOpened()) {
			open();
		}
		
		Query query = session.createQuery("from " + className);
		return new ArrayList<T>((List<T>) query.list());
	}

	@SuppressWarnings("unchecked")
	public static <T> T getData(String className, int id)
	{
		if (!isOpened()) {
			open();
		}
		
		Query query = session.createQuery("from " + className + " where num = " + id);
		return (T) (query.list().get(0));
	}
}
