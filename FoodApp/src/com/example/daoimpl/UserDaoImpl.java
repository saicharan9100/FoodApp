package com.example.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.dao.UserDao;
import com.example.domain.User;

public class UserDaoImpl implements UserDao{
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		Transaction tx=null;
		Session session=sessionFactory.openSession();
		try{
			if (session == null) {
	            throw new IllegalStateException("SessionFactory not initialized");
	        }
			tx=session.beginTransaction();
			session.save(user);
			tx.commit();
		}
		catch(HibernateException e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally{
			session.close();
		}
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		Transaction tx=null;
		Session session=sessionFactory.openSession();
		try{
			tx=session.beginTransaction();
			session.delete(user);
			tx.commit();
		}
		catch(HibernateException e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally{
			session.close();
		}
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
	}

	@Override
	public User getUserById(Long id) {
		Transaction tx = null;
		Session session = sessionFactory.openSession();
		User user = null;
		try {
			tx = session.beginTransaction();
			user = session.get(User.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        List<User> users = null;
        try {
            Query<User> query = session.createQuery("from User", User.class);
            users = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		User user=null;
		Transaction tx=null;
		Session session=sessionFactory.openSession();
		try{
			tx=session.beginTransaction();
			Query<User> query=(Query<User>) session.createQuery("from User where username=:username", User.class);
			query.setParameter("username",username);
			user=query.uniqueResult();
			tx.commit();
		}
		catch(HibernateException e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return user;
	}

}
