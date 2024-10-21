package com.example.server;

import java.util.ArrayList;
import java.util.List;

import com.example.client.service.MenuService;
import com.example.daoimpl.MenuItemDaoImpl;
import com.example.domain.MenuItem;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MenuServiceImpl extends RemoteServiceServlet implements MenuService{
	private MenuItemDaoImpl menuItemDao;

	@Override
	public List<MenuItem> getAllMenuItems() {
		menuItemDao=(MenuItemDaoImpl)ApplicationContextListener.applicationContext.getBean("menuItemDao");
		return menuItemDao.getAllMenuItems();
	}

	@Override
	public MenuItem getMenuItemById(Long id) {
		menuItemDao=(MenuItemDaoImpl)ApplicationContextListener.applicationContext.getBean("menuItemDao");
		return menuItemDao.getMenuItemById(id);
	}

	@Override
	public void addMenuItem(MenuItem item) {
		menuItemDao=(MenuItemDaoImpl)ApplicationContextListener.applicationContext.getBean("menuItemDao");
		menuItemDao.saveMenuItem(item);
	}

	@Override
	public void updateMenuItem(MenuItem item) {
		menuItemDao=(MenuItemDaoImpl)ApplicationContextListener.applicationContext.getBean("menuItemDao");
		menuItemDao.updateMenuItem(item);
	}

	@Override
	public void deleteMenuItem(MenuItem item) {
		menuItemDao=(MenuItemDaoImpl)ApplicationContextListener.applicationContext.getBean("menuItemDao");
		menuItemDao.deleteMenuItem(item);
	}
}
