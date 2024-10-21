package com.example.client.gui;

import com.example.client.service.AuthService;
import com.example.client.service.AuthServiceAsync;
import com.example.domain.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class AccountPage extends Composite {
    private AuthServiceAsync userService =GWT.create(AuthService.class);
    private User user;
    private VerticalPanel accountInfoPanel = new VerticalPanel();
    
    private TextBox usernameTextBox = new TextBox();
    private TextBox emailTextBox = new TextBox();
    private PasswordTextBox passwordTextBox = new PasswordTextBox();
    private Button saveButton = new Button("Save");

    public AccountPage() {
        fetchCurrentUser();
        initWidget(accountInfoPanel);
    }

    public void fetchCurrentUser() {
        userService.getCurrentUser(new AsyncCallback<User>() {

            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(User result) {
                user = result;
                accountDetails();
            }
        });
    }

    public void accountDetails() {
        accountInfoPanel.setSpacing(10);
        accountInfoPanel.addStyleName("accountInfo");

        Label accountTitle = new Label("Account Information");
        accountTitle.addStyleName("title");

        Anchor editAnchor = new Anchor("Edit");
        editAnchor.addStyleName("editLink");

        Grid formGrid = new Grid(3, 2);
        formGrid.setCellPadding(10);
        formGrid.setWidth("100%");

        Label usernameLabel = new Label("Username:");
        usernameTextBox.setText(user.getUsername());
        usernameTextBox.setEnabled(false);
        usernameTextBox.setWidth("100%");
        formGrid.setWidget(0, 0, usernameLabel);
        formGrid.setWidget(0, 1, usernameTextBox);

        Label emailLabel = new Label("Email:");
        emailTextBox.setText(user.getEmail());
        emailTextBox.setEnabled(false);
        emailTextBox.setWidth("100%");
        formGrid.setWidget(1, 0, emailLabel);
        formGrid.setWidget(1, 1, emailTextBox);

        Label passwordLabel = new Label("Password:");
        passwordTextBox.setText(user.getPassword());
        passwordTextBox.setEnabled(false);
        passwordTextBox.setWidth("100%");
        formGrid.setWidget(2, 0, passwordLabel);
        formGrid.setWidget(2, 1, passwordTextBox);

        accountInfoPanel.add(accountTitle);
        accountInfoPanel.add(editAnchor);
        accountInfoPanel.add(formGrid);

        FlowPanel buttonPanel = new FlowPanel();
        buttonPanel.addStyleName("accountButtonPanel");
        saveButton.addStyleName("accountSaveButton");
        saveButton.setEnabled(false); 
        buttonPanel.add(saveButton);
        accountInfoPanel.add(buttonPanel);

        editAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                usernameTextBox.setEnabled(true);
                emailTextBox.setEnabled(true);
                passwordTextBox.setEnabled(true);
                saveButton.setEnabled(true);
            }
        });

        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                user.setUsername(usernameTextBox.getText());
                user.setEmail(emailTextBox.getText());
                user.setPassword(passwordTextBox.getText());

                userService.updateUser(user, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {

                    }

                    @Override
                    public void onSuccess(Void result) {
                        usernameTextBox.setEnabled(false);
                        emailTextBox.setEnabled(false);
                        passwordTextBox.setEnabled(false);
                        saveButton.setEnabled(false);
                    }
                });
            }
        });
    }
}
