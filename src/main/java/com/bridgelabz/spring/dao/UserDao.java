package com.bridgelabz.spring.dao;

import com.bridgelabz.spring.model.User;

public interface UserDao {

	public int register(User user);

	public User loginUser(String emailId, String password);

	public User getUserById(int id);

	public void updateUser(int id, User user);

	public void deleteUser(int id);
	

}