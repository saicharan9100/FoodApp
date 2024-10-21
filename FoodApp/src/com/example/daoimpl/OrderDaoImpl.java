package com.example.daoimpl;

import java.util.HashSet;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.dao.OrderDao;
import com.example.domain.Order;

public class OrderDaoImpl implements OrderDao {
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}

	@Override
	public void updateOrder(Order order) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			session.update(order);
			tx.commit();			
		}
		catch (HibernateException e) {
			tx.rollback();
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
	@Override
	public List<Order> getOrderByStatus(String status) {
		 Session session = sessionFactory.openSession();
		 List<Order> orders = null;
		 try {
			 Query<Order> query = session.createQuery("from Order where status = :status", Order.class);
			 query.setParameter("status", status);
			 orders = query.getResultList();
			 for (Order order : orders) {
				 order.detach();   
			 }   
			 return orders;
		 } catch (HibernateException e) {
			 e.printStackTrace();
		 } finally {
			 session.close();
		 }
		 return orders;
	}
	@Override
	public List<Order> getOrdersByUserId(Long userId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		List<Order> orders = null;
		try{
			Query<Order> query=session.createQuery("from Order where user.id=:userId",Order.class);
			query.setParameter("userId", userId);
			orders = query.getResultList();
			 for (Order order : orders) {
				 order.detach();   
			 }  
			 return orders;
		}catch(HibernateException e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}
}
