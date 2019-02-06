package com.bridgelabz.spring.dao;

import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.spring.model.User;

public interface UserDao {

	 int register(User user);

	 User loginUser(String emailId,HttpServletResponse resp);

	 User getUserById(int id);

	 void updateUser(int id, User user);

	 void deleteUser(int id);
	

}