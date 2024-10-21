package com.example.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class AdminNavBar extends Composite {
	private FlowPanel contentPanel = new FlowPanel();
	private FlowPanel navbarPanel = new FlowPanel();
	private FlowPanel mainPanel = new FlowPanel();
	private HTML activeLink;
	public AdminNavBar(){
    	HorizontalPanel navBar=new HorizontalPanel();
    	navBar.setStyleName("nav-bar");
    	HorizontalPanel leftpanel = new HorizontalPanel();
    	leftpanel.setStyleName("left-panel");
    	
    	HorizontalPanel rightpanel = new HorizontalPanel();
    	rightpanel.setStyleName("right-panel");
    	
    	TextBox searchBox = new TextBox();
        searchBox.getElement().setPropertyString("placeholder", "Search for food Items");
        searchBox.setStyleName("search-box");

        leftpanel.add(searchBox);
        
        
        final HTML ordersLink = new HTML("<a href='#Orders'>Orders</a>");
        ordersLink.setStyleName("nav-link");
        setActiveLink(ordersLink);
        final HTML menuLink = new HTML("<a href='#Menu'>Menu</a>");
        menuLink.setStyleName("nav-link");
        final HTML accountLink = new HTML("<a href='#account'>Account</a>");
        accountLink.setStyleName("nav-link");
        final HTML logoutLink = new HTML("<a href='#Logout'>Logout</a>");
        logoutLink.setStyleName("nav-link");
        
        ordersLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setActiveLink(ordersLink);
				showordersPage();				
			}
        });

        menuLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
            	setActiveLink(menuLink);
                showmenuPage();
            }
        });

        accountLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
            	setActiveLink(accountLink);
                showaccountPage();
            }
        });

        logoutLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
            	setActiveLink(logoutLink);
                showLogoutPage();
            }
        });
        rightpanel.add(ordersLink);
        rightpanel.add(menuLink);
        rightpanel.add(accountLink);
        rightpanel.add(logoutLink);
        
        navBar.add(leftpanel);
        navBar.add(rightpanel);
        
        navBar.setCellHorizontalAlignment(rightpanel, HasHorizontalAlignment.ALIGN_RIGHT);
        navBar.setCellVerticalAlignment(rightpanel, HasVerticalAlignment.ALIGN_MIDDLE);
        
        mainPanel.add(navBar);
        mainPanel.add(contentPanel);
        initWidget(mainPanel);
        showordersPage();
    }
	private void setActiveLink(HTML link) {
        if (activeLink != null) {
            activeLink.removeStyleName("active");
        }
        activeLink = link;
        activeLink.addStyleName("active");
    }
	private void showordersPage() {
        contentPanel.clear();
        OrdersPage ordersPage = new OrdersPage();
        contentPanel.add(ordersPage);
    }

    private void showmenuPage() {
        contentPanel.clear();
        MenuPage menu = new MenuPage();
        contentPanel.add(menu);
    }

    private void showaccountPage() {
        contentPanel.clear();
        AccountPage accountPage = new AccountPage();
        contentPanel.add(accountPage);
    }

    private void showLogoutPage() {
    	RootPanel.get().clear();
        contentPanel.clear();
        foodUI logoutpage = new foodUI();
        contentPanel.add(logoutpage);
    }
}
