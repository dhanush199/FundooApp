package com.bridgelabz.spring.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.spring.model.User;

public interface UserDaoInf {

	 int register(User user);

	 User loginUser(User user,HttpServletRequest req,HttpServletResponse resp);

	 User getUserById(int id);

	 void updateUser(int id, User user);

	 void deleteUser(int id);
	 
	 User getUserByEmailId(String emailId);
	 
}