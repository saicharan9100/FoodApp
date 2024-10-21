package com.example.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.dao.MenuItemDao;
import com.example.domain.MenuItem;

public class MenuItemDaoImpl implements MenuItemDao{
	private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	@Override
	public void saveMenuItem(MenuItem menuItem) {
		// TODO Auto-generated method stub
		Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.save(menuItem);
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
	public void deleteMenuItem(MenuItem menuItem) {
		// TODO Auto-generated method stub
		Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.delete(menuItem);
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
	public void updateMenuItem(MenuItem menuItem) {
		// TODO Auto-generated method stub
		Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.update(menuItem);
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
	public List<MenuItem> getAllMenuItems() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        List<MenuItem> menuItems = null;
        try {
            Query<MenuItem> query = session.createQuery("from MenuItem", MenuItem.class);
            menuItems = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return menuItems;
	}

	@Override
	public MenuItem getMenuItemById(Long id) {
		// TODO Auto-generated method stub
		Transaction tx = null;
        Session session = sessionFactory.openSession();
        MenuItem menuItem = null;
        try {
            tx = session.beginTransaction();
            menuItem = session.get(MenuItem.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return menuItem;
	}

}
