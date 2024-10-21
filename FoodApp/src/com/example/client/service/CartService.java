package com.example.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

import com.example.domain.*;

@RemoteServiceRelativePath("cartservice")
public interface CartService extends RemoteService{
	List<CartItem> getCartItemsByUserId(Long id);
	Long placeOrder(Order order);
	void addOrderItem(OrderItem orderItem);
	void clearCart(Long userId);
	void addCartItem(CartItem cartItem);
}