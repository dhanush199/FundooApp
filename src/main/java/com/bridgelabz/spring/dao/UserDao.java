package com.bridgelabz.spring.dao;

import com.bridgelabz.spring.model.User;

public interface UserDao {

	 int register(User user);

	 User loginUser(String emailId);

	 User getUserById(int id);

	 void updateUser(int id, User user);

	 void deleteUser(int id);
	

}