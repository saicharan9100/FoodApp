package com.example.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.dao.OrderItemDao;
import com.example.domain.OrderItem;
import com.example.domain.User;

public class OrderItemDaoImpl implements OrderItemDao {
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
	@Override
	public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
		// TODO Auto-generated method stub
		List<OrderItem> orderItems=null;
		Session session=sessionFactory.openSession();
		Transaction tx=null;
		try{
			Query<OrderItem> query = session.createQuery("from OrderItem where order.id = :orderId", OrderItem.class);
	        query.setParameter("orderId", orderId);
	        System.out.println("got from orderitem table");
	        orderItems = query.getResultList();
		}
		catch(HibernateException e){
			tx.rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return orderItems;
	}
	@Override
	public void saveOrderItem(OrderItem orderItem) {
		// TODO Auto-generated method stub
		
	}

}
