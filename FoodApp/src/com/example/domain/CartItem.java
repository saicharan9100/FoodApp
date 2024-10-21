package com.example.domain;

import java.io.Serializable;

import com.google.gwt.resources.client.ImageResource;

public class CartItem implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Long cartItemId;
		private Long userId;
		private Long itemId;
		private int quantity;
		public CartItem() {}
		public CartItem(Long cartItemId, Long userId, Long itemId, int quantity) {
			this.cartItemId = cartItemId;
			this.userId = userId;
			this.itemId = itemId;
			this.quantity = quantity;
		}
		public Long getCartItemId() {
			return cartItemId;
		}
		public void setCartItemId(Long cartItemId) {
			this.cartItemId = cartItemId;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public Long getItemId() {
			return itemId;
		}
		public void setItemId(Long itemId) {
			this.itemId = itemId;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public ImageResource getImageUrl() {
			return null;
		}
		@Override
		public String toString() {
			return "CartItem [cartItemId=" + cartItemId + ", userId=" + userId + ", itemId=" + itemId + ", quantity="
					+ quantity + "]";
		}
}