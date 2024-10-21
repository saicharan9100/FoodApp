package com.example.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class NavBar extends Composite {
	private FlowPanel contentPanel = new FlowPanel();
	private FlowPanel navbarPanel = new FlowPanel();
	private FlowPanel mainPanel = new FlowPanel();
	private HTML activeLink;
	public NavBar(){
    	HorizontalPanel navBar=new HorizontalPanel();
    	navBar.setStyleName("nav-bar");
    	HorizontalPanel leftpanel = new HorizontalPanel();
    	leftpanel.setStyleName("left-panel");
    	
    	HorizontalPanel rightpanel = new HorizontalPanel();
    	rightpanel.setStyleName("right-panel");
    	
    	final TextBox searchBox = new TextBox();
        searchBox.getElement().setPropertyString("placeholder", "Search for food Items");
        searchBox.setStyleName("search-box");

        leftpanel.add(searchBox);
        searchBox.addKeyUpHandler(new KeyUpHandler(){

			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub
				String searchText=searchBox.getText();
				HomePage homePage=(HomePage)contentPanel.getWidget(0);
				if(event.getNativeKeyCode()==13){
					homePage.searchMenuItems(searchText);
				}
				else if(searchText.isEmpty()){
					homePage.fetchMenuItems();
				}
			}
        	
        });
        
        final HTML homeLink = new HTML("<a href='#home'>Home</a>");
        homeLink.setStyleName("nav-link");
        setActiveLink(homeLink);
        final HTML previousOrdersLink = new HTML("<a href='#orderstatus'>Orders</a>");
        previousOrdersLink.setStyleName("nav-link");
        final HTML cartLink = new HTML("<a href='#cart'>Cart</a>");
        cartLink.setStyleName("nav-link");
        final HTML accountLink = new HTML("<a href='#account'>Account</a>");
        accountLink.setStyleName("nav-link");
        homeLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setActiveLink(homeLink);
				showHomePage();				
			}
        });

        previousOrdersLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
            	setActiveLink(previousOrdersLink);
                showpreviousOrdersPage();
            }
        });

        cartLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
            	setActiveLink(cartLink);
                showCartPage();
            }
        });
        rightpanel.add(homeLink);
        rightpanel.add(previousOrdersLink);
        rightpanel.add(cartLink);
        final ListBox listBox=new ListBox();
        listBox.setStyleName("listStyle");
        listBox.addItem("Account");
        listBox.addItem("AccountInfo");
        listBox.addItem("Logout");
        rightpanel.add(listBox);
        listBox.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				setActiveLink(accountLink);
				int selectedIndex = listBox.getSelectedIndex();
	            String selectedItem = listBox.getItemText(selectedIndex);
	            if (selectedItem.equals("Logout")) {
	                Logout();
	            } else if(selectedItem.equals("AccountInfo")){
	                showAccountPage();
	            }
	            listBox.setSelectedIndex(0);
			}
        	
        });
        navBar.add(leftpanel);
        navBar.add(rightpanel);
        
        navBar.setCellHorizontalAlignment(rightpanel, HasHorizontalAlignment.ALIGN_RIGHT);
        navBar.setCellVerticalAlignment(rightpanel, HasVerticalAlignment.ALIGN_MIDDLE);
        
        mainPanel.add(navBar);
        mainPanel.add(contentPanel);
        initWidget(mainPanel);
        showHomePage();
    }
	private void setActiveLink(HTML link) {
        if (activeLink != null) {
            activeLink.removeStyleName("active");
        }
        activeLink = link;
        activeLink.addStyleName("active");
    }
	private void showHomePage() {
        contentPanel.clear();
        HomePage homePage = new HomePage();
        contentPanel.add(homePage);
    }

    private void showpreviousOrdersPage() {
        contentPanel.clear();
        PreviousOrders previousOrders = new PreviousOrders();
        contentPanel.add(previousOrders);
    }

    private void showCartPage() {
        contentPanel.clear();
        CartPage cartPage = new CartPage();
        contentPanel.add(cartPage);
    }

    private void showAccountPage() {
        contentPanel.clear();
        AccountPage accountPage = new AccountPage();
        contentPanel.add(accountPage);
    }
    private void Logout() {
    	RootPanel.get().clear();
        contentPanel.clear();
        foodUI logoutpage = new foodUI();
        contentPanel.add(logoutpage);
    }
}