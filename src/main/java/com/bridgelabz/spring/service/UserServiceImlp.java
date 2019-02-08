package com.bridgelabz.spring.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.spring.dao.UserDaoInf;
import com.bridgelabz.spring.model.User;
import com.bridgelabz.spring.utility.EmailUtil;
import com.bridgelabz.spring.utility.TokenGeneratorInf;

@Service
public class UserServiceImlp implements UserServiceInf {

	@Autowired
	private UserDaoInf userDao;

	@Autowired
	private EmailUtil email;

	@Autowired
	private TokenGeneratorInf tokenGenerator;


	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Transactional
	public User updateUser(int id, User user, HttpServletRequest request) {
		User existingUser = userDao.getUserById(id);
		if (existingUser != null) {
			existingUser.setMobileNumber(user.getMobileNumber());
			existingUser.setName(user.getName());
			existingUser.setPassword(user.getPassword());
			userDao.updateUser(id, existingUser);
		}
		return existingUser;
	}

	@Transactional
	public boolean register(User user, HttpServletRequest request) {
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		int id = userDao.register(user);
		if (id > 0) {
			String token = tokenGenerator.generateToken(String.valueOf(id));
			StringBuffer url=request.getRequestURL();
			String url2=url.substring(0, url.lastIndexOf("/"));
			url2=url2+"/verify/"+token;
			System.out.println(token);
			email.sendEmail("", "Verification Mail", url2);
			return true;
		}
		return false;
	}

	@Transactional
	public User loginUser(User user,HttpServletRequest request,HttpServletResponse resp) {

		User existingUser = userDao.loginUser(user,request,resp);
		if (existingUser != null) {
			boolean match=bcryptEncoder.matches(user.getPassword(), existingUser.getPassword());
			if(match)
				return existingUser;
		}
		return null;
	}

	@Transactional
	public User deleteUser(int id, HttpServletRequest request) {
		User existingUser = userDao.getUserById(id);
		if (existingUser != null) {
			userDao.deleteUser(id);
		}
		return existingUser;
	}

	@Transactional
	public User activateUser(String token, HttpServletRequest request) {
		int id=tokenGenerator.authenticateToken(token);
		User exsistingUser=userDao.getUserById(id);
		if(exsistingUser!=null)
		{
			exsistingUser.setActivationStatus(true);
			userDao.updateUser(id, exsistingUser);
		}
		return exsistingUser;
	}

}