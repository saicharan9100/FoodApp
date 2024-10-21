package com.example.server;

import java.util.ArrayList;
import java.util.List;

import com.example.client.service.CartService;
import com.example.daoimpl.CartItemDaoImpl;
import com.example.domain.CartItem;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CartServiceImpl extends RemoteServiceServlet implements CartService{
	 private CartItemDaoImpl cartItemDao;

	@Override
	public List<CartItem> getCartItemsByUserId(Long id) {
		cartItemDao =(CartItemDaoImpl)ApplicationContextListener.applicationContext.getBean("cartItemDao");
		return cartItemDao.getCartItemsByUserId(id);
	}

	@Override
	public Long placeOrder(Order order) {
		cartItemDao =(CartItemDaoImpl)ApplicationContextListener.applicationContext.getBean("cartItemDao");
		return cartItemDao.placeOrder(order);
	}

	@Override
	public void addOrderItem(OrderItem orderItem) {
		cartItemDao =(CartItemDaoImpl)ApplicationContextListener.applicationContext.getBean("cartItemDao");
		cartItemDao.addOrderItem(orderItem);
	}

	@Override
	public void clearCart(Long userId) {
		cartItemDao =(CartItemDaoImpl)ApplicationContextListener.applicationContext.getBean("cartItemDao");
		cartItemDao.clearCart(userId);
	}

	@Override
	public void addCartItem(CartItem cartItem) {
		cartItemDao =(CartItemDaoImpl)ApplicationContextListener.applicationContext.getBean("cartItemDao");
		cartItemDao.addCartItem(cartItem);
	}
}