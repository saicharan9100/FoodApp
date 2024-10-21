package com.example.client;

import com.example.client.gui.foodUI;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
public class FoodApp implements EntryPoint{

	@Override
	public void onModuleLoad() {
		foodUI foodui=new foodUI();
	}
}

