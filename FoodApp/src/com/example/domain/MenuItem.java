package com.example.domain;

import java.io.Serializable;

public class MenuItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long itemId;
	private String itemName;
	private String description;
	private double price;
	private String imageURL;
	private boolean Available;
	public MenuItem() {}
	public MenuItem(Long itemId, String itemName, String description, double price, String imageURL,
			boolean isAvailable) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.description = description;
		this.price = price;
		this.imageURL = imageURL;
		this.Available = isAvailable;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public boolean isAvailable() {
		return Available;
	}
	public void setAvailable(boolean available) {
		Available = available;
	}
	@Override
	public String toString() {
		return "MenuItem [itemId=" + itemId + ", itemName=" + itemName + ", description=" + description + ", price="
				+ price + ", imageURL=" + imageURL + ", Available=" + Available + "]";
	}
	
}

