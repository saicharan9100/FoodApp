package com.example.client.gui;

import com.example.client.service.AuthService;
import com.example.client.service.AuthServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SignUpPage extends Composite{
	private final AuthServiceAsync authService = GWT.create(AuthService.class);
	public SignUpPage() {
        FlowPanel container = new FlowPanel();
        container.setStyleName("signup-panel-container");

        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.setSpacing(10);
        mainPanel.setStyleName("signup-panel");

        Label titleLabel = new Label("Create Your Account");
        titleLabel.setStyleName("signup-title");
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyleName("input-label");
        final TextBox usernameTextbox = new TextBox();
        usernameTextbox.setStyleName("input-field");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyleName("input-label");
        final PasswordTextBox passwordTextBox = new PasswordTextBox();
        passwordTextBox.setStyleName("input-field");

        Label emailLabel = new Label("Email:");
        emailLabel.setStyleName("input-label");
        final TextBox emailTextbox = new TextBox();
        emailTextbox.setStyleName("input-field");

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyleName("signup-button");
        signUpButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String username = usernameTextbox.getText();
                String password = passwordTextBox.getText();
                String email = emailTextbox.getText();
                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    Window.alert("All fields are required.");
                    return;
                }
                authService.signupUser(username, password, email, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Signup failed: " + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(Void result) {
                        Window.alert("Signup successful!");
                        loadLoginPage();
                    }
                });
            }
        });

        mainPanel.add(titleLabel);
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameTextbox);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordTextBox);
        mainPanel.add(emailLabel);
        mainPanel.add(emailTextbox);
        mainPanel.add(signUpButton);

        container.add(mainPanel);
        initWidget(container);  
    }
	private void loadLoginPage() {
        RootPanel.get().clear();
        foodUI loginUI = new foodUI();
        loginUI.loadConsumerHomePage();
    }
}
