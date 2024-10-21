package com.example.client.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.client.service.AuthService;
import com.example.client.service.AuthServiceAsync;
import com.example.client.service.CartService;
import com.example.client.service.CartServiceAsync;
import com.example.client.service.MenuService;
import com.example.client.service.MenuServiceAsync;
import com.example.domain.CartItem;
import com.example.domain.MenuItem;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class CartPage extends Composite {
    private CartServiceAsync cartService = GWT.create(CartService.class);
    private MenuServiceAsync menuService = GWT.create(MenuService.class);
    private final AuthServiceAsync authService = GWT.create(AuthService.class);
    private User user = null;
    private FlowPanel mainPanel = new FlowPanel();
    private final FlexTable cartTable;
    final Label itemTotal;
    final Label total;
    List<MenuItem> menuItems=new ArrayList<>();
    final Label statusLabel;
    public CartPage() {
        fetchCurrentUser();
        mainPanel.setStyleName("cartMainPanel");
        HorizontalPanel cartLayout = new HorizontalPanel();
        cartLayout.setStyleName("cartLayout");
        VerticalPanel cartPanel = new VerticalPanel();
        cartPanel.setStyleName("cartPanel");
        cartTable= new FlexTable();
        cartTable.setStyleName("cartTable");
        cartTable.setWidth("100%");
        cartTable.setText(0, 0, "Food Item");
        cartTable.setText(0, 1, "Quantity");
        cartTable.setText(0, 2, "Price");
        statusLabel = new Label();
        statusLabel.setStyleName("statusLabel");
        cartPanel.add(statusLabel);
        cartPanel.add(cartTable);
        cartLayout.add(cartPanel);
        VerticalPanel billPanel = new VerticalPanel();
        billPanel.setStyleName("billPanel");

        Label billDetailsTitle = new Label("Bill Details:");
        billDetailsTitle.setStyleName("billDetailsTitle");

        itemTotal = new Label("Item Total: Rs.0");
        final Label platformFee = new Label("Platform Fee: Rs.10");
        final Label gstTax = new Label("GST and Canteen Tax: Rs.30");
        total= new Label("To Pay: Rs.0");

        billPanel.add(billDetailsTitle);
        billPanel.add(itemTotal);
        billPanel.add(platformFee);
        billPanel.add(gstTax);
        billPanel.add(total);

        Label paymentLabel = new Label("Payment Options:");
        ListBox paymentSelect = new ListBox();
        paymentSelect.addItem("Select Payment Method");
        paymentSelect.addItem("PhonePe");
        paymentSelect.addItem("Paytm");
        paymentSelect.addItem("Google Pay");
        paymentSelect.addItem("Cash");

        billPanel.add(paymentLabel);
        billPanel.add(paymentSelect);

        cartLayout.add(billPanel);
        mainPanel.add(cartLayout);
        Button placeOrderButton = new Button("Place Order");
        placeOrderButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                placeOrder(cartTable, itemTotal, total);
            }
        });

        billPanel.add(placeOrderButton);
        initWidget(mainPanel);
        
    }

    private void fetchCurrentUser() {
        authService.getCurrentUser(new AsyncCallback<User>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to fetch user: " + caught.getMessage());
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
                fetchCartItems(cartTable, itemTotal, total);
			}
    		
    	});
    }
    private MenuItem findMenuItemById(Long itemId) {
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getItemId().equals(itemId)) {
                return menuItem;
            }
        }
        return null;
    }
    private void fetchCartItems(final FlexTable cartTable, final Label itemTotal, final Label total) {  	
        cartService.getCartItemsByUserId(user.getUserId(), new AsyncCallback<List<CartItem>>() {
        	int row = 1;
            double totalAmount = 0.0;
          
            @Override
            public void onFailure(Throwable caught) {
            	Window.alert("Error"+ caught.getCause());
                GWT.log("Failed to fetch cart items", caught);
            }
            @Override
            public void onSuccess(List<CartItem> result) {            	
                for (final CartItem cartItem : result) {
                  MenuItem menuItem=findMenuItemById(cartItem.getItemId());
                  Image itemImage = new Image(menuItem.getImageURL());
                  cartTable.setWidget(row, 0, createItemWidget(itemImage, menuItem.getItemName()));
                  cartTable.setText(row, 1, String.valueOf(cartItem.getQuantity()));
                  cartTable.setText(row, 2, "Rs." + menuItem.getPrice());
                  totalAmount += menuItem.getPrice() * cartItem.getQuantity();
                  row++;
                  itemTotal.setText("Item Total: Rs." + totalAmount);
                  total.setText("To Pay: Rs." + (totalAmount + 10 + 30));
                }
            }
        });
    }

    private HorizontalPanel createItemWidget(Image image, String itemName) {
        HorizontalPanel itemPanel = new HorizontalPanel();
        image.setStyleName("cartItemImage");
        Label itemNameLabel = new Label(itemName);
        itemNameLabel.setStyleName("cartItemName");
        itemPanel.add(image);
        itemPanel.add(itemNameLabel);
        return itemPanel;
    }

    private void placeOrder(final FlexTable cartTable, final Label itemTotal, final Label total) {
        cartService.getCartItemsByUserId(user.getUserId(), new AsyncCallback<List<CartItem>>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Failed to fetch cart items", caught);
            }

            @Override
            public void onSuccess(final List<CartItem> cartItems) {
                if (cartItems.isEmpty()) {
                    Window.alert("Empty Cart");
                    return;
                }

                final Order newOrder = new Order();
                newOrder.setUser(user);
                newOrder.setUserId(user.getUserId());
                newOrder.setStatus("pending");
                newOrder.setAmount(calculateTotalAmount(cartItems));
                cartService.placeOrder(newOrder, new AsyncCallback<Long>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Long result) {
						// TODO Auto-generated method stub
						Set<OrderItem> orderItems=new HashSet<>();
		                for(int i=0;i<cartItems.size();i++){		                	
		                	OrderItem orderItem=new OrderItem();
		                	orderItem.setOrderId(result);
		                	orderItem.setItemId(cartItems.get(i).getItemId());
		                	orderItem.setQuantity(cartItems.get(i).getQuantity());
		                	orderItem.setPrice(findMenuItemById(cartItems.get(i).getItemId()).getPrice()); 
		                	orderItems.add(orderItem);
		                	cartService.addOrderItem(orderItem, new AsyncCallback<Void>(){
								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									
								}
								@Override
								public void onSuccess(Void result) {
									// TODO Auto-generated method stub
									
								}
			                	
			                });
		                }
						clearCart();
						statusLabel.setText("Order placed successfully!");
					}

                });
            }
        });
    }
    private void clearCart() {
        cartService.clearCart(user.getUserId(), new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to clear cart: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Void result) {
                cartTable.removeAllRows();
                
                itemTotal.setText("Item Total: Rs.0");
                total.setText("To Pay: Rs.0");
            }
        });
    }

    private double calculateTotalAmount(final List<CartItem> cartItems) {
        double totalAmount =0.0;
        for (final CartItem cartItem : cartItems) {
        	MenuItem menuItem=findMenuItemById(cartItem.getItemId());
        	totalAmount+=menuItem.getPrice() * cartItem.getQuantity();
        }
        return totalAmount+40;
    }
}

