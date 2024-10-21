package com.example.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.dao.CartItemDao;
import com.example.domain.CartItem;
import com.example.domain.Order;
import com.example.domain.OrderItem;

public class CartItemDaoImpl implements CartItemDao {
	private SessionFactory sessionFactory;
	
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	@Override
	public CartItem getCartItemById(int cartItemId) {
		Transaction tx = null;
        Session session = sessionFactory.openSession();
        CartItem cartItem = null;
        try {
            tx = session.beginTransaction();
            cartItem = session.get(CartItem.class, cartItemId);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return cartItem;
	}

	@Override
	public List<CartItem> getCartItemsByUserId(Long userId) {
		List<CartItem> cartItems = null;
        Session session = sessionFactory.openSession();
        try {
            Query<CartItem> query = session.createQuery("from CartItem where userId = :userId", CartItem.class);
            query.setParameter("userId", userId);
            cartItems = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return cartItems;
	}

	@Override
	public void saveCartItem(CartItem cartItem) {
		 Transaction tx = null;
	        Session session = sessionFactory.openSession();
	        try {
	            tx = session.beginTransaction();
	            session.save(cartItem);
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
	public void updateCartItem(CartItem cartItem) {
		Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.update(cartItem);
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
	public void deleteCartItem(int cartItemId) {
		Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            CartItem cartItem = session.get(CartItem.class, cartItemId);
            if (cartItem != null) {
                session.delete(cartItem);
            }
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
	public Long placeOrder(Order order) {
        Transaction tx = null;
        Long orderId = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            orderId = (Long) session.save(order);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orderId;
    }

    public void addOrderItem(OrderItem orderItem) {
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.save(orderItem);
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
    public void clearCart(Long userId) {
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            Query<CartItem> query = session.createQuery("delete from CartItem where userId = :userId");
            query.setParameter("userId", userId);
            query.executeUpdate();
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
    public void addCartItem(CartItem cartItem) {
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.save(cartItem); 
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
}
