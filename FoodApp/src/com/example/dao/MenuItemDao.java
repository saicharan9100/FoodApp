package com.example.dao;

import java.util.List;

import com.example.domain.MenuItem;

public interface MenuItemDao {
	void saveMenuItem(MenuItem menuItem);
	void deleteMenuItem(MenuItem menuItem);
	void updateMenuItem(MenuItem menuItem);
	List<MenuItem> getAllMenuItems();
	MenuItem getMenuItemById(Long id);
}
