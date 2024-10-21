package com.example.dao;

import java.util.List;

import com.example.domain.Order;

public interface OrderDao {
	void updateOrder(Order order);
	List<Order> getOrderByStatus(String status);
	List<Order> getOrdersByUserId(Long userId);
}
