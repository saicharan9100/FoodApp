package com.example.server;

import java.util.List;

import com.example.client.service.OrderService;
import com.example.daoimpl.OrderDaoImpl;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class OrderServiceImpl extends RemoteServiceServlet implements OrderService {
    private static final long serialVersionUID = 1L;
    private OrderDaoImpl orderDao;

    private OrderDaoImpl getOrderDao() {
        if (orderDao == null) {
            orderDao = (OrderDaoImpl) ApplicationContextListener.applicationContext.getBean("orderDao");
        }
        return orderDao;
    }

    @Override
    public List<Order> getIncomingOrders() {
        return TransactionUtil.doInTransaction(() -> {
            List<Order> orders = getOrderDao().getOrderByStatus("pending");
            detachOrders(orders);
            return orders;
        });
    }

    @Override
    public List<Order> getProcessingOrders() {
        return TransactionUtil.doInTransaction(() -> {
             return getOrderDao().getOrderByStatus("Processing");
        });
    }

    @Override
    public List<Order> getCompletedOrders() {
         return TransactionUtil.doInTransaction(() -> {
            List<Order> orders = getOrderDao().getOrderByStatus("Completed");
            // Assuming no detach needed or if needed, call it here
            return orders;
         });
    }

    @Override
    public void updateOrderStatus(Order order) {
        TransactionUtil.doInTransaction(() -> {
            getOrderDao().updateOrder(order);
        });
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return TransactionUtil.doInTransaction(() -> {
            List<Order> orders = getOrderDao().getOrdersByUserId(userId);
            detachOrders(orders);
            return orders;
        });
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    private void detachOrders(List<Order> orders) {
        if (orders != null) {
            for (Order order : orders) {
                HibernateDetachUtility.detachOrder(order);
                // OrderItems are handled inside detachOrder
            }
        }
    }
}
