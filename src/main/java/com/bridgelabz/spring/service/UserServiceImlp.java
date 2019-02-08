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
	public User updateUser(String token, User user, HttpServletRequest request) {
		int id=tokenGenerator.authenticateToken(token);
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
	public boolean register(User user, HttpServletRequest request,HttpServletResponse resp) {
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		int id = userDao.register(user);
		if (id > 0) {
			String activateStatusUrl=tokenGenerator.generateUrl("/verify/", user, request, resp);
//			String token = tokenGenerator.generateToken(String.valueOf(id));
//			StringBuffer url=request.getRequestURL();
//			String url2=url.substring(0, url.lastIndexOf("/"));
//			url2=url2+"/verify/"+token;
			//System.out.println(token);
			email.sendEmail("", "Verification Mail", activateStatusUrl);
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
	public User deleteUser(String token, HttpServletRequest request) {
		int id=tokenGenerator.authenticateToken(token);
		User aliveUser = userDao.getUserById(id);
		if (aliveUser != null) {
			userDao.deleteUser(id);
		}
		return aliveUser;
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
	@Transactional
	public User getUserByEmail( String userToken,HttpServletRequest request,User newPassword) {
		//int id=tokenGenerator.authenticateToken(userToken);
		User exsistingUser=userDao.getUserByEmailId(newPassword.getEmailId());
		if(exsistingUser!=null)
		{
			String token = tokenGenerator.generateToken(String.valueOf(exsistingUser.getId()));
			StringBuffer url=request.getRequestURL();
			String url2=url.substring(0, url.lastIndexOf("/"));
			url2=url2+"/resetpassword/"+token;
			System.out.println(token);
			email.sendEmail("dhanushsh1995@gmail.com", "Password Reset Link Mail", "please click on this link to reset password "+url2);
		}
		exsistingUser.setEmailId("");
		return exsistingUser;
	}

	@Transactional
	public User resetPassword(String emailID, HttpServletRequest request,User newPassword) {
		User exsistingUser=userDao.getUserByEmailId(emailID);
		if(exsistingUser!=null)
		{
			exsistingUser.setPassword(newPassword.getPassword());
			userDao.updateUser(exsistingUser.getId(), exsistingUser);
		}
		return exsistingUser;
	}

}