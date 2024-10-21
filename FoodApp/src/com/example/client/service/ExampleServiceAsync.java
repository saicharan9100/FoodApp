package com.example.client.service;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ExampleServiceAsync{
	void getUserDetails(String username,String password, AsyncCallback<String> callback);
}

