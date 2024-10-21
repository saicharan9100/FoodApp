package com.example.client.gui;

import com.example.client.service.AuthService;
import com.example.client.service.AuthServiceAsync;
import com.example.client.service.CartService;
import com.example.client.service.CartServiceAsync;
import com.example.client.service.MenuService;
import com.example.client.service.MenuServiceAsync;
import com.example.domain.CartItem;
import com.example.domain.MenuItem;
import com.example.domain.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends Composite {
	private final MenuServiceAsync menuService = GWT.create(MenuService.class);
	private final CartServiceAsync cartService=GWT.create(CartService.class);
	private final AuthServiceAsync authService = GWT.create(AuthService.class);
	User user;
	private Label showMessage = new Label();
    private FlowPanel menuPanel=new FlowPanel();
    public HomePage() {
        initWidget(menuPanel);
        fetchCurrentUser();
        fetchMenuItems();
        setupMessage();
    }
    private void fetchCurrentUser() {
        authService.getCurrentUser(new AsyncCallback<User>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to fetch user: " + caught.getMessage());
            }
            @Override
            public void onSuccess(User result) {
                user = result;
            }
        });
    }
    public void fetchMenuItems(){
    	menuService.getAllMenuItems(new AsyncCallback<List<MenuItem>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Failed to fetch menu items: " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<MenuItem> result) {
				// TODO Auto-generated method stub
				displayMenuItems(result);
			}
    		
    	});
    }
    private void setupMessage() {
    	showMessage.setStyleName("toast");
        RootPanel.get().add(showMessage);
    }
    private void showMessageOfCart(String message) {
    	showMessage.setText(message);
    	showMessage.addStyleName("show");

        new Timer() {
            @Override
            public void run() {
            	showMessage.removeStyleName("show");
            }
        }.schedule(3000);
    }
    public void searchMenuItems(final String searchText){
    	menuService.getAllMenuItems(new AsyncCallback<List<MenuItem>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Failed to fetch menu items: " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<MenuItem> result) {
				// TODO Auto-generated method stub
				List<MenuItem> filteredItems=new ArrayList<>();
				for(MenuItem item:result){
					if(item.getItemName().toLowerCase().contains(searchText.toLowerCase())){
						filteredItems.add(item);
					}
				}
				displayMenuItems(filteredItems);
			}
    		
    	});
    }
	private void displayMenuItems(List<MenuItem> menuItems) {
		menuPanel.clear();
		menuPanel.setStyleName("menu-panel");
		for(final MenuItem item:menuItems){
			if(!item.isAvailable()){
				continue;
			}
			FlowPanel itemPanel=new FlowPanel();
			itemPanel.setStyleName("menu-item");
			
			Image itemImage=new Image(item.getImageURL());
			itemImage.setStyleName("menu-item-image");
			
			
			HTML menuItemHtml = new HTML("<h3>" + item.getItemName() + "</h3><p>" + item.getDescription() + "</p><p>Price: Rs." + item.getPrice() + "/- </p>");
			
			Button addButton = new Button("Add");
			addButton.setStyleName("menuItemButton");
			addButton.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					addItemToCart(item);
				}
				
			});
			itemPanel.add(itemImage);
			itemPanel.add(menuItemHtml);
			itemPanel.add(addButton);
			
			menuPanel.add(itemPanel);
		}
	}
	private void addItemToCart(MenuItem item) {
	    
	    Long userId = user.getUserId(); 

	    CartItem cartItem = new CartItem();
	    cartItem.setUserId(userId);
	    cartItem.setItemId(item.getItemId()); 
	    cartItem.setQuantity(1);

	    cartService.addCartItem(cartItem, new AsyncCallback<Void>() {
	        @Override
	        public void onFailure(Throwable caught) {
	        	showMessageOfCart("Failed to add item to cart.");
	        }

	        @Override
	        public void onSuccess(Void result) {
	        	showMessageOfCart("Item added to cart successfully!");
	        }
	    });
	}

}
