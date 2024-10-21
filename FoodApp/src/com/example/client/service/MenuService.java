package com.example.client.service;
import com.example.domain.MenuItem;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("menuService")
public interface MenuService extends RemoteService{
	List<MenuItem> getAllMenuItems();
	MenuItem getMenuItemById(Long id);
	void addMenuItem(MenuItem item);
	void updateMenuItem(MenuItem item);
	void deleteMenuItem(MenuItem item);
}
