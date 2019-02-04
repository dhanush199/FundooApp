package com.bridgelabz.spring.service;


import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.spring.model.User;

public interface UserService {
	public boolean register(User user, HttpServletRequest request);

	public User loginUser(String emailId, String password, HttpServletRequest request);

	public User updateUser(int id, User user, HttpServletRequest request);

	public User deleteUser(int id, HttpServletRequest request);

}