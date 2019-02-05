package com.bridgelabz.spring.service;


import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.spring.model.User;

public interface UserService {
	 boolean register(User user, HttpServletRequest request);

	 User loginUser(String emailId/*, String password*/, HttpServletRequest request);

	 User updateUser(int id, User user, HttpServletRequest request);

	 User deleteUser(int id, HttpServletRequest request);
}