package com.example.client.service;

import java.util.List;

import com.example.domain.OrderItem;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface OrderItemServiceAsync {
	void  getOrderItemsByOrderId(Long orderId,AsyncCallback<List<OrderItem>> callback);
}
