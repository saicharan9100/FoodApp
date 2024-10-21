package com.example.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

import com.example.domain.MenuItem;
public interface MenuServiceAsync {
	void getAllMenuItems(AsyncCallback<List<MenuItem>> callback);
	void getMenuItemById(Long id,AsyncCallback<MenuItem> callback);
	void addMenuItem(MenuItem item, AsyncCallback<Void> callback);
	void updateMenuItem(MenuItem item,AsyncCallback<Void> callback);
	void deleteMenuItem(MenuItem item,AsyncCallback<Void> callback);
}
