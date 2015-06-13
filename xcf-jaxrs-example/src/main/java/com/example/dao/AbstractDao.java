package com.example.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

public abstract class AbstractDao<T extends Object> implements BaseDao<T> {

//	@Autowired
//	private SessionFactory sessionFactory;
	
	@PersistenceContext
	private EntityManager entityManager;

	private Class<T> domainClass;

//	protected Session getSession() {
//		return sessionFactory.getCurrentSession();
//	}

	private Class<T> getDomainClass() {
		if (domainClass == null) {
			ParameterizedType thisType = (ParameterizedType) getClass()
					.getGenericSuperclass();
			this.domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
		}

		return domainClass;
	}

	private String getDomainClassName() {
		return getDomainClass().getName();
	}

	@Override
	public void create(T t) {
		Method method = ReflectionUtils.findMethod(getDomainClass(),
				"setDateCreated", new Class[] { Date.class });
		if (method != null) {
			try {
				method.invoke(t, new Date());
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		entityManager.persist(t);
	}

	@Override
	public T get(Serializable id) {
		return (T) entityManager.find(getDomainClass(), id);
	}

	@Override
	public T load(Serializable id) {
		return (T) entityManager.find(getDomainClass(), id);
	}

	@Override
	public List<T> getAll() {
		return entityManager.createQuery("from " + getDomainClassName()).getResultList();
	}

	@Override
	public void update(T t) {
		entityManager.persist(t);
	}

	@Override
	public void delete(T t) {
		entityManager.remove(t);
	}
	
	@Override
	public void deleteById(Serializable id) {
		delete(load(id));
	}

	@Override
	public void deleteAll() {
		entityManager.createQuery("delete "+getDomainClassName()).executeUpdate();
	}
	
	@Override
	public long count() {
		return (Long) entityManager.createQuery("select count(*) from "+getDomainClassName()).getSingleResult();
	}
	
	@Override
	public boolean exist(Serializable id) {
		return (get(id) != null);
	}
}
