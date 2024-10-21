package com.example.client.gui;

import com.example.client.service.AuthService;
import com.example.client.service.AuthServiceAsync;
import com.example.client.service.ExampleService;
import com.example.client.service.ExampleServiceAsync;
import com.example.domain.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class foodUI extends Composite{
	private final AuthServiceAsync service = GWT.create(AuthService.class);
    public foodUI() {
        FlowPanel centerPanelContainer = new FlowPanel();
        centerPanelContainer.setStyleName("center-panel-container");

        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.setStyleName("loginPanel");
        mainPanel.setSpacing(10);

        Label titleLabel = new Label("Online Food Ordering System");
        titleLabel.setStyleName("titleLabel");

        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyleName("input-label");
        final TextBox usernameTextbox = new TextBox();
        usernameTextbox.setStyleName("input-field");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyleName("input-label");
        final PasswordTextBox passwordTextBox = new PasswordTextBox();
        passwordTextBox.setStyleName("input-field");
        
        Button loginButton = new Button("Login");
        loginButton.setStyleName("loginButton");
        loginButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String username = usernameTextbox.getText();
                String password = passwordTextBox.getText();

                service.authenticateUser(username,password,new AsyncCallback<User>() {
        			@Override
        			public void onFailure(Throwable caught) {
        				Window.alert("Validation error :" +caught.getMessage());
        			}

        			@Override
        			public void onSuccess(User result) {
        				if(result!=null){
        					if(result.getRoleId()==1){
        						loadAdminHomePage();
        					}
        					else{
        						loadConsumerHomePage();
        					}
        				}
        				else{
        					Window.alert("Invalid Username");
        				}
        			}
                });
            }
        });

        FlowPanel linkPanel = new FlowPanel();
        linkPanel.setStyleName("linkPanel");

        HTML forgotPasswordLink = new HTML("<a href='#'>Forgot Password</a>");
        HTML signUpLink = new HTML("<a href='#'>Sign Up</a>");
        signUpLink.setStyleName("signUpLink");
        signUpLink.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				loadSignUpPage();
			}
        	
        });
        linkPanel.add(forgotPasswordLink);
        linkPanel.add(signUpLink);

        mainPanel.add(titleLabel);
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameTextbox);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordTextBox);
        mainPanel.add(loginButton);
        mainPanel.add(linkPanel);
        centerPanelContainer.add(mainPanel);
        RootPanel.get().add(centerPanelContainer);
    }

    public void loadConsumerHomePage() {
        RootPanel.get().clear();
        NavBar navbar=new NavBar();
        RootPanel.get().add(navbar);
    }
    public void loadAdminHomePage() {
        RootPanel.get().clear();
        AdminNavBar adminnavbar=new AdminNavBar();
        RootPanel.get().add(adminnavbar);
    }
    public void loadSignUpPage(){
    	RootPanel.get().clear();
    	SignUpPage signup=new SignUpPage();
    	RootPanel.get().add(signup);
    }
}
