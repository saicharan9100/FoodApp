package com.example.dao;

import java.util.List;

import com.example.domain.User;

public interface UserDao {
	void saveUser(User user);
	void deleteUser(User user);
	void updateUser(User user);
	User getUserById(Long id);
	List<User> findAllUsers();
	User findByUsername(String username);
}
