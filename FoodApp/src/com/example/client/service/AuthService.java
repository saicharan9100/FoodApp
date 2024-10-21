package com.example.client.service;

import com.example.domain.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("auth")
public interface AuthService extends RemoteService {
	User getUserById(Long id);
	User authenticateUser(String username,String password);
	User getCurrentUser();
	void signupUser(String username,String password,String email);
	void updateUser(User user);
}
