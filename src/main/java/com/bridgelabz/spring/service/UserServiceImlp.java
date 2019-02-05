package com.bridgelabz.spring.service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.spring.dao.UserDao;
import com.bridgelabz.spring.model.User;
import com.bridgelabz.spring.utility.EmailUtil;
import com.bridgelabz.spring.utility.TokenGeneratorInf;

@Service
public class UserServiceImlp implements UserServiceInf {

	@Autowired
	private UserDao userDao;

	@Autowired
	private EmailUtil email;

	@Autowired
	private TokenGeneratorInf tokenGenerator;
	@Autowired
    private PasswordEncoder bcryptEncoder;


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
			email.sendEmail("", "click here Verification Mail", url2);
			return true;
		}
		return false;
	}

	@Transactional
	public User loginUser(String emailId, String password,HttpServletRequest request) {
		

		 User details = userDao.loginUser(emailId);
		       if (details != null) {
		           boolean match=bcryptEncoder.matches(password, details.getPassword());
		           if(match)
		               return details;
		       }
		       return null;

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
	
	@Transactional
	  public User activateUser(String token, HttpServletRequest request) {
	        int id=tokenGenerator.authenticateToken(token);
	        User user=userDao.getUserById(id);
	        if(user!=null)
	        {
	            user.setActivationStatus(true);
	            userDao.updateUser(id, user);
	        }
	        return user;
	    }
	
}