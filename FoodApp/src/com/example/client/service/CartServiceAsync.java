package com.example.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

import com.example.domain.*;
public interface CartServiceAsync {
	void getCartItemsByUserId(Long id,AsyncCallback<List<CartItem>> callback);
	void placeOrder(Order order, AsyncCallback<Long> callback);
	void addOrderItem(OrderItem orderItem, AsyncCallback<Void> callback);
	void clearCart(Long userId, AsyncCallback<Void> callback);
	void addCartItem(CartItem cartItem, AsyncCallback<Void> callback);
}
