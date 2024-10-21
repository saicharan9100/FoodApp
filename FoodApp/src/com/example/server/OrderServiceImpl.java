package com.example.server;

import java.util.ArrayList;
import java.util.List;

import com.example.client.service.OrderService;
import com.example.daoimpl.OrderDaoImpl;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class OrderServiceImpl extends RemoteServiceServlet implements OrderService{
	private OrderDaoImpl orderDao;
	@Override
	public List<Order> getIncomingOrders() {
		orderDao=(OrderDaoImpl)ApplicationContextListener.applicationContext.getBean("orderDao");
		List<Order> orders= orderDao.getOrderByStatus("pending");
		for(Order order:orders){
			order.detach();
			for(OrderItem orderItem:order.getOrderItems()){
				orderItem.detach();
			}
		}
		return orders;
	}

	@Override
	public List<Order> getProcessingOrders() {
		orderDao=(OrderDaoImpl)ApplicationContextListener.applicationContext.getBean("orderDao");
		return orderDao.getOrderByStatus("Processing");
	}

	@Override
	public List<Order> getCompletedOrders() {
		orderDao=(OrderDaoImpl)ApplicationContextListener.applicationContext.getBean("orderDao");
		List<Order> orders= orderDao.getOrderByStatus("Completed");
		return orders;
	}
	@Override
	public void updateOrderStatus(Order order) {
		orderDao=(OrderDaoImpl)ApplicationContextListener.applicationContext.getBean("orderDao");
		orderDao.updateOrder(order);
	}
	@Override
	public List<Order> getOrdersByUserId(Long userId) {
		orderDao=(OrderDaoImpl)ApplicationContextListener.applicationContext.getBean("orderDao");
		List<Order> orders= orderDao.getOrdersByUserId(userId);
		for(Order order:orders){
			order.detach();
			for(OrderItem orderItem:order.getOrderItems()){
				orderItem.detach();
			}
		}
		return orders;
	}
	@Override
	public List<Order> getAllOrders() {
		return null;
	}
}
