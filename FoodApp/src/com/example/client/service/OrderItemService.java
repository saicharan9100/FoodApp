package com.example.client.service;

import java.util.List;

import com.example.domain.OrderItem;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("orderitemservice")
public interface OrderItemService extends RemoteService {
	List<OrderItem> getOrderItemsByOrderId(Long orderId);
}