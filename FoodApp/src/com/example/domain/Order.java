package com.example.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Order implements Serializable, IsSerializable {

	private static final long serialVersionUID = 1L;
	private Long orderId;
	private Long userId; // Restored field
	private String status;
	private double amount;
	private User user;
	private Set<OrderItem> orderItems = new HashSet<>();

	public Order() {
	}

	public Order(Long orderId, Long userId, String status, double amount) {
		this.orderId = orderId;
		this.userId = userId;
		this.status = status;
		this.amount = amount;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		if (user != null) {
			return user.getUserId();
		}
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", userId=" + getUserId() + ", status=" + status + ", amount=" + amount + "]";
	}
}
