package com.bridgelabz.spring.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.spring.model.User;

public interface UserServiceInf {
	 boolean register(User user, HttpServletRequest request);

	 User loginUser(User user, HttpServletRequest req,HttpServletResponse resp);
	
	User updateUser(int id, User user, HttpServletRequest request);

	 User deleteUser(int id, HttpServletRequest request);
	 
	 User activateUser(String token, HttpServletRequest request);
}