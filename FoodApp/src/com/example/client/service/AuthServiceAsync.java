package com.example.client.service;

import com.example.domain.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AuthServiceAsync {
	void getUserById(Long id,AsyncCallback<User> callback);
	void authenticateUser(String username,String password,AsyncCallback<User> callback);
	void getCurrentUser(AsyncCallback<User> callback);
	void signupUser(String username,String password,String email,AsyncCallback<Void> callback);
	void updateUser(User user,AsyncCallback<Void> callback);
}
