package com.example.client.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.example.client.service.AuthService;
import com.example.client.service.AuthServiceAsync;
import com.example.client.service.MenuService;
import com.example.client.service.MenuServiceAsync;
import com.example.client.service.OrderService;
import com.example.client.service.OrderServiceAsync;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.example.domain.User;
import com.example.domain.MenuItem;
import com.example.domain.Order;
import com.example.domain.OrderItem;
public class PreviousOrders extends Composite {
    private final AuthServiceAsync userService = GWT.create(AuthService.class);
    private final OrderServiceAsync orderService = GWT.create(OrderService.class);
    private final MenuServiceAsync menuService=GWT.create(MenuService.class);
    private final FlowPanel contentPanel = new FlowPanel();
    private final FlowPanel ordersPanel = new FlowPanel();
    User user;
    private List<Order> ordersList;
    List<MenuItem> menuItems=new ArrayList<>();
    public PreviousOrders() {
        initWidget(contentPanel);
        contentPanel.setStyleName("previous-orders-page");

        Label pageTitle = new Label("Previous Orders");
        pageTitle.setStyleName("page-title");
        contentPanel.add(pageTitle);

        contentPanel.add(ordersPanel);

        fetchCurrentUser();
    }

    private void fetchCurrentUser() {
        userService.getCurrentUser(new AsyncCallback<User>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to fetch current user");
            }

            @Override
            public void onSuccess(User result) {
            	user=result;
            	fetchMenuItems();
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
				menuItems=result;
				fetchOrdersForUser(user.getUserId());
			}
    		
    	});
    }
    private String findItemNameByItemId(Long itemId){
    	for(MenuItem item:menuItems){
    		if(item.getItemId().equals(itemId)){
    			return item.getItemName();
    		}
    	}
    	return null;
    }
    private void fetchOrdersForUser(Long userId) {
        orderService.getOrdersByUserId(userId, new AsyncCallback<List<Order>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to fetch orders: " + caught.getMessage());
            }

            @Override
            public void onSuccess(List<Order> result) {
                if (result != null && !result.isEmpty()) {
                	 Collections.sort(result, new Comparator<Order>() {
                         @Override
                         public int compare(Order order1, Order order2) {
                             return Long.compare(order2.getOrderId(), order1.getOrderId());
                         }
                     });
                    ordersList = result;
                    displayOrders(result);
                } else {
                    ordersPanel.add(new Label("No orders found"));
                }
            }
        });
    }

    private void displayOrders(List<Order> orders) {
        for (Order order : orders) {
            FlowPanel orderBox = createOrderBox(order);
            ordersPanel.add(orderBox);
        }
    }

    private FlowPanel createOrderBox(Order order) {
        FlowPanel orderBox = new FlowPanel();
        orderBox.setStyleName("previousOrderBox");

        HorizontalPanel orderIdPanel = new HorizontalPanel();
        Label orderIdLabel = new Label("Order ID: ");
        Label orderIdValue = new Label(order.getOrderId().toString());
        orderIdLabel.setStyleName("order-id-label"); 
        orderIdValue.setStyleName("order-id-value");
        orderIdPanel.add(orderIdLabel);
        orderIdPanel.add(orderIdValue);

        Label totalAmountLabel = new Label("Total Amount: Rs. " + order.getAmount() + "/-");
        totalAmountLabel.setStyleName("order-amount");

        Label orderStatusLabel = new Label("Status: " + order.getStatus());
        orderStatusLabel.setStyleName("order-status");

        orderBox.add(orderIdPanel);
        orderBox.add(totalAmountLabel);
        orderBox.add(orderStatusLabel);

        final Button toggleButton = new Button("Show Details");
        toggleButton.setStyleName("toggle-button");
        final FlexTable detailsTable = createDetailsTable(order.getOrderItems());
        detailsTable.setStyleName("details-table");
        detailsTable.setVisible(false);

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
        return orderBox;
    }

    private FlexTable createDetailsTable(Set<OrderItem> orderItems) {
        FlexTable detailsTable = new FlexTable();
        detailsTable.setBorderWidth(1);

        detailsTable.setText(0, 0, "Item ID");
        detailsTable.setText(0, 1, "Item Name");
        detailsTable.setText(0, 2, "Quantity");
        detailsTable.setText(0, 3, "Price");

        int row = 1;
        for (OrderItem orderItem : orderItems) {
            detailsTable.setText(row, 0, orderItem.getItemId().toString());
            detailsTable.setText(row, 1, findItemNameByItemId(orderItem.getItemId()));
            detailsTable.setText(row, 2, Integer.toString(orderItem.getQuantity()));
            detailsTable.setText(row, 3, Double.toString(orderItem.getPrice()));
            row++;
        }

        return detailsTable;
    }
}

