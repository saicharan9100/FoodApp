package com.example.server;

import java.util.List;

import com.example.client.service.OrderItemService;
import com.example.daoimpl.OrderItemDaoImpl;
import com.example.domain.OrderItem;

public class OrderItemServiceImpl implements OrderItemService {
	private OrderItemDaoImpl orderItemDao;
	@Override
	public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
		orderItemDao=(OrderItemDaoImpl)ApplicationContextListener.applicationContext.getBean("orderItemDao");
		return orderItemDao.getOrderItemsByOrderId(orderId);
	}
	
}
