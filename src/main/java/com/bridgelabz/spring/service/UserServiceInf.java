package com.bridgelabz.spring.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.spring.model.User;

public interface UserServiceInf {
	
	boolean register(User user, HttpServletRequest request,HttpServletResponse resp);

	User loginUser(User user, HttpServletRequest req,HttpServletResponse resp);

	User updateUser(String token, User user, HttpServletRequest request);

	User deleteUser(String token, HttpServletRequest request,HttpServletResponse resp);

	User activateUser(String token, HttpServletRequest request);

	User getUserByEmail(String token, HttpServletRequest request,User newPassword,HttpServletResponse response);
}