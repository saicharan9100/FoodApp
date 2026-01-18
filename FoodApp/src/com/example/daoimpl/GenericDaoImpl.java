package com.example.daoimpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.example.dao.GenericDao;

public class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {

    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void save(T entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    public void update(T entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    @Override
    public void delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    @Override
    public T findById(Class<T> type, ID id) {
        return sessionFactory.getCurrentSession().get(type, id);
    }

    @Override
    public List<T> findAll(Class<T> type) {
        Query<T> query = sessionFactory.getCurrentSession().createQuery("from " + type.getName(), type);
        return query.getResultList();
    }
}
