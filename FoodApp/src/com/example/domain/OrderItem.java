package com.example.domain;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class OrderItem implements Serializable, IsSerializable {
	private static final long serialVersionUID = 1L;
	private Long orderItemId;
	private Long orderId;
	private Long itemId;
	private int quantity;
	private double price;

	public OrderItem(Long orderItemId, Long orderId, Long itemId, int quantity, double price) {
		this.orderItemId = orderItemId;
		this.orderId = orderId;
		this.itemId = itemId;
		this.quantity = quantity;
		this.price = price;
	}

	public OrderItem() {
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
