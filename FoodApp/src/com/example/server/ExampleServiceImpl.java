package com.example.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.example.client.service.ExampleService;
import com.example.daoimpl.UserDaoImpl;
import com.example.domain.User;

public class ExampleServiceImpl extends RemoteServiceServlet implements ExampleService{
	
	@Override
	public String getUserDetails(String username, String password) {
		if(username.equals("sai")&&password.equals("charan")){
			return "admin";
		}
		if(username.equals("charan") && password.equals("sai")) {
	        return "consumer";
	    }
		return null;
	}
}
