package com.example.dao;

import java.util.List;

import com.example.domain.CartItem;

public interface CartItemDao {
	CartItem getCartItemById(int cartItemId);
	List<CartItem> getCartItemsByUserId(Long userId);
	void saveCartItem(CartItem cartItem);
	void updateCartItem(CartItem cartItem);
	void deleteCartItem(int cartItemId);
}
