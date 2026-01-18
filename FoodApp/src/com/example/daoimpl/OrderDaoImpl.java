package com.example.daoimpl;

import java.util.List;

import org.hibernate.query.Query;

import com.example.dao.OrderDao;
import com.example.domain.Order;

public class OrderDaoImpl extends GenericDaoImpl<Order, Long> implements OrderDao {

    @Override
    public void updateOrder(Order order) {
        update(order);
    }

    @Override
    public List<Order> getOrderByStatus(String status) {
        Query<Order> query = sessionFactory.getCurrentSession().createQuery("from Order where status = :status", Order.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        Query<Order> query = sessionFactory.getCurrentSession().createQuery("from Order where user.id=:userId", Order.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
