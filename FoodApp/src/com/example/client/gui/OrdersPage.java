package com.example.client.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.client.service.AuthService;
import com.example.client.service.AuthServiceAsync;
import com.example.client.service.MenuService;
import com.example.client.service.MenuServiceAsync;
import com.example.client.service.OrderItemService;
import com.example.client.service.OrderItemServiceAsync;
import com.example.client.service.OrderService;
import com.example.client.service.OrderServiceAsync;
import com.example.domain.MenuItem;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.User;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class OrdersPage extends Composite {
    private final OrderServiceAsync orderService = GWT.create(OrderService.class);
    private final MenuServiceAsync menuService=GWT.create(MenuService.class);
    User user;
    private FlowPanel contentPanel = new FlowPanel();  
    private FlowPanel ordersPanel = new FlowPanel(); 
    private Label showMessage = new Label();
    List<MenuItem> menuItems=new ArrayList<>();

    private Button incomingButton;
    private Button processingButton;
    private Button completedButton;

    public OrdersPage() {
    	fetchMenuItems();
        initWidget(contentPanel);
        contentPanel.setStyleName("orders-page");

        HorizontalPanel navPanel = new HorizontalPanel();
        navPanel.setStyleName("nav-panel");

        incomingButton = new Button("Incoming Orders");
        processingButton = new Button("Processing Orders");
        completedButton = new Button("Completed Orders");

        navPanel.add(incomingButton);
        navPanel.add(processingButton);
        navPanel.add(completedButton);

        contentPanel.add(navPanel);
        contentPanel.add(ordersPanel);
        setupMessage();
        displayIncomingOrders();
        incomingButton.addStyleName("incoming-active");

        incomingButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                clearActiveStates();
                incomingButton.addStyleName("incoming-active");
                displayIncomingOrders();
            }
        });

        processingButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                clearActiveStates();
                processingButton.addStyleName("processing-active");
                displayProcessingOrders();
            }
        });

        completedButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                clearActiveStates();
                completedButton.addStyleName("completed-active");
                displayCompletedOrders();
            }
        });
    }

    private void clearActiveStates() {
        incomingButton.removeStyleName("incoming-active");
        processingButton.removeStyleName("processing-active");
        completedButton.removeStyleName("completed-active");
    }
    
    private void displayIncomingOrders() {
        ordersPanel.clear();
        orderService.getIncomingOrders(new AsyncCallback<List<Order>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to fetch incoming orders: " + caught.getMessage());
            }

            @Override
            public void onSuccess(List<Order> result) {
                for (final Order order : result) {             
                    final FlowPanel orderBox = createOrderBox(order);
                    FlowPanel actionsPanel = new FlowPanel();
                    actionsPanel.setStyleName("actions-panel");

                    Button acceptButton = new Button("Accept");
                    acceptButton.setStyleName("accept-button");
                    actionsPanel.add(acceptButton);
                    acceptButton.addClickHandler(new ClickHandler(){

						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							order.setStatus("Processing");
							orderService.updateOrderStatus(order,new AsyncCallback<Void>(){

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void onSuccess(Void result) {
									// TODO Auto-generated method stub
									showAcceptMessage("Order Accepted");
									ordersPanel.remove(orderBox);
								}
								
							});
						}
                    	
                    });
                    Button rejectButton = new Button("Reject");
                    rejectButton.addClickHandler(new ClickHandler(){

						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							order.setStatus("Rejected");
							orderService.updateOrderStatus(order, new AsyncCallback<Void>(){

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void onSuccess(Void result) {
									// TODO Auto-generated method stub
									ordersPanel.remove(orderBox);
								}
								
							});
						}
                    	
                    });
                    rejectButton.setStyleName("reject-button");
                    actionsPanel.add(rejectButton);

                    orderBox.add(actionsPanel);
                    ordersPanel.add(orderBox);
                }
            }
        });
    }
    private void displayProcessingOrders() {
        ordersPanel.clear();
        orderService.getProcessingOrders(new AsyncCallback<List<Order>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to fetch processing orders: " + caught.getMessage());
            }

            @Override
            public void onSuccess(List<Order> result) {
                for (final Order order : result) {
                    final FlowPanel orderBox = createOrderBox(order);
                    FlowPanel actionsPanel = new FlowPanel();
                    actionsPanel.setStyleName("actions-panel");
                    
                    Button preparingButton = new Button("Preparing");
                    preparingButton.setStyleName("preparing-button");
                    preparingButton.addClickHandler(new ClickHandler(){

						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							order.setStatus("Completed");
							orderService.updateOrderStatus(order, new AsyncCallback<Void>(){

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void onSuccess(Void result) {
									// TODO Auto-generated method stub
									ordersPanel.remove(orderBox);
								}
								
							});
						}
                    	
                    });
                    actionsPanel.add(preparingButton);
                    
                    orderBox.add(actionsPanel);
                    ordersPanel.add(orderBox);
                }
            }
        });
    }

    private void displayCompletedOrders() {
        ordersPanel.clear();
        orderService.getCompletedOrders(new AsyncCallback<List<Order>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to fetch completed orders: " + caught.getMessage());
            }

            @Override
            public void onSuccess(List<Order> result) {
                for (Order order : result) {
                    FlowPanel orderBox = createOrderBox(order);
                    FlowPanel actionsPanel = new FlowPanel();
                    actionsPanel.setStyleName("actions-panel");
                    
                    Button completedButton = new Button("Completed");
                    completedButton.setStyleName("completed-button");
                    actionsPanel.add(completedButton);

                    orderBox.add(actionsPanel);
                    ordersPanel.add(orderBox);
                }
            }
        });
    }
    private void setupMessage() {
    	showMessage.setStyleName("toast");
        RootPanel.get().add(showMessage);
    }
    private void showAcceptMessage(String message) {
    	showMessage.setText(message);
    	showMessage.addStyleName("show");

        new Timer() {
            @Override
            public void run() {
            	showMessage.removeStyleName("show");
            }
        }.schedule(3000);
    }
    public void fetchMenuItems(){
    	menuService.getAllMenuItems(new AsyncCallback<List<MenuItem>>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed to fetch menu items: " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<MenuItem> result) {
				menuItems=result;
			}
    		
    	});
    }
    private FlowPanel createOrderBox(Order order) {
        FlowPanel orderBox = new FlowPanel();
        orderBox.setStyleName("order-box");
        HorizontalPanel orderIdPanel = new HorizontalPanel();
        Label orderIdLabel = new Label("Order ID: ");
        
        Label orderIdText = new Label(Long.toString(order.getOrderId()));

        orderIdPanel.add(orderIdLabel);
        orderIdPanel.add(orderIdText);
        orderBox.add(orderIdPanel);
        
        HorizontalPanel usernamePanel = new HorizontalPanel();
        Label usernameLabel = new Label("Username: ");
        Label usernameText = new Label(order.getUser().getUsername());
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameText);
        orderBox.add(usernamePanel);

        Label priceLabel = new Label("Price: Rs. " + order.getAmount() + "/-");
        priceLabel.setStyleName("order-price");
        orderBox.add(priceLabel);
        final Button toggleButton = new Button("Show Details");
        toggleButton.setStyleName("toggle-button");
        final FlexTable detailsTable = new FlexTable();
        detailsTable.setStyleName("details-table"); 
        detailsTable.setVisible(false);
        Set<OrderItem> orderItems=new HashSet<>(order.getOrderItems());
        int row = 0;
        for (OrderItem orderItem : orderItems) {
        	
            detailsTable.setText(row, 0, "Item ID: " + orderItem.getItemId());
            String itemName=findItemNameByItemId(orderItem.getItemId());
            detailsTable.setText(row, 1, "ItemName: "+itemName);
            detailsTable.setText(row, 2, "Quantity: " + orderItem.getQuantity());
            detailsTable.setText(row, 3, "Price: Rs. " + orderItem.getPrice());
            row++;
        }
        toggleButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                boolean isVisible = detailsTable.isVisible();
                detailsTable.setVisible(!isVisible);
                toggleButton.setText(isVisible ? "Show Details" : "Hide Details");
            }
        });

        orderBox.add(toggleButton);
        orderBox.add(detailsTable);
        detailsTable.setBorderWidth(1);
        return orderBox;
    }
    private String findItemNameByItemId(Long itemId){
    	for(MenuItem item:menuItems){
    		if(item.getItemId().equals(itemId)){
    			return item.getItemName();
    		}
    	}
    	return null;
    }
}