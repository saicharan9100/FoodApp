package com.example.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

import com.example.domain.Order;
import com.example.domain.OrderItem;
public interface OrderServiceAsync {
	void getAllOrders(AsyncCallback<List<Order>> callback);
	void getIncomingOrders(AsyncCallback<List<Order>> callback);
	void getProcessingOrders(AsyncCallback<List<Order>> callback);
	void getCompletedOrders(AsyncCallback<List<Order>> callback);
	void updateOrderStatus(Order order,AsyncCallback<Void> callback);
	void getOrdersByUserId(Long userId,AsyncCallback<List<Order>> callback);
}
