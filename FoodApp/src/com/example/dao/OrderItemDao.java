package com.example.dao;

import java.util.List;

import com.example.domain.OrderItem;

public interface OrderItemDao {
	List<OrderItem> getOrderItemsByOrderId(Long orderId);
	void saveOrderItem(OrderItem orderItem);
}
