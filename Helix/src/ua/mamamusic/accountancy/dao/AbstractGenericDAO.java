package ua.mamamusic.accountancy.dao;


import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ua.mamamusic.accountancy.HibernateUtil;

public abstract class AbstractGenericDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {

	protected Session getSession(){
		return HibernateUtil.getSession();
	}

	@Override
	public void save(T entity) {
		Session session = this.getSession();
		session.saveOrUpdate(entity);
	}

	@Override
	public void merge(T entity) {
		Session session = this.getSession();
		session.merge(entity);		
	}

	@Override
	public void update(T entity) {
		Session session = this.getSession();
		session.update(entity);	
	}

	@Override
	public void delete(T entity) {
		Session session = this.getSession();
		session.delete(entity);		
	}

	@Override
	public List<T> findMany(Query query) {
		List<T> list;
		list = (List<T>) query.list();
		return list;
	}

	@Override
	public T findOne(Query query) {
		T t;
		t = (T) query.uniqueResult();
		return t;
	}

	@Override
	public List<T> findAll(Class<T> clazz) {
		Session session = this.getSession();
		List<T> list;
		Query q = session.createQuery("from "+ clazz.getName());
		list = q.list();
		return list;
	}

	@Override
	public T findById(Class<T> clazz, long id) {
		Session session = this.getSession();
		T t = null;
		t = (T) session.get(clazz, id);
		return t;
	}
	
	
}
