package com.example.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;

import com.example.domain.Order;
import com.example.domain.OrderItem;
@RemoteServiceRelativePath("orderservice")
public interface OrderService extends RemoteService{
	List<Order> getAllOrders();
	List<Order> getIncomingOrders();
	List<Order> getProcessingOrders();
	List<Order> getCompletedOrders();
	List<Order> getOrdersByUserId(Long userId);
	void updateOrderStatus(Order order);
}
