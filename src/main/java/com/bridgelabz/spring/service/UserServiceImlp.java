package com.bridgelabz.spring.service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.spring.dao.UserDao;
import com.bridgelabz.spring.model.User;
import com.bridgelabz.spring.utility.TokenGenerator;

@Service
public class UserServiceImlp implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TokenGenerator tokenGenerator;

	@Transactional
	public boolean register(User user, HttpServletRequest request) {
		int id = userDao.register(user);
		if (id > 0) {
			String token = tokenGenerator.generateToken(String.valueOf(id));
			System.out.println(token);
			return true;
		}
		return false;
	}

	@Transactional
	public User loginUser(String emailId, String password, HttpServletRequest request) {
		return userDao.loginUser(emailId, password);
	}

	@Transactional
	public User updateUser(int id, User user, HttpServletRequest request) {
		User user2 = userDao.getUserById(id);
		if (user2 != null) {
			user2.setMobileNumber(user.getMobileNumber());
			user2.setName(user.getName());
			user2.setPassword(user.getPassword());
			userDao.updateUser(id, user2);
		}
		return user2;
	}

	@Transactional
	public User deleteUser(int id, HttpServletRequest request) {
		User user2 = userDao.getUserById(id);
		if (user2 != null) {
			userDao.deleteUser(id);
		}
		return user2;
	}

}