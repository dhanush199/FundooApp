package com.bridgelabz.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.spring.model.User;
import com.bridgelabz.spring.service.UserServiceInf;

@RestController
public class UserController {

	@Autowired
	private UserServiceInf userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user, HttpServletRequest request) {
	//	try {
			if (userService.register(user, request))
				return new ResponseEntity<String>("Successfully Updated",HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<String>("User not found",HttpStatus.CONFLICT);
//		}
		return new ResponseEntity<String>("Please enter the valid details",HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<?> loginUser(@RequestParam("emailId") String emailId,
			@RequestParam("password") String password, HttpServletRequest request,HttpServletResponse resp) {

		User user = userService.loginUser(emailId,password,request,resp);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("Incorrect emailId or password or Your Account is not Activated(Please activate from your registered mai)", HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> updateUser(@RequestParam("id") int id, @RequestBody User user,
			HttpServletRequest request) {

		User user2 = userService.updateUser(id, user, request);
		if (user2 != null) {
			return new ResponseEntity<User>(user2, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("Email incorrect. Please enter valid email address present in database",
					HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@RequestParam("id") int id, HttpServletRequest request) {

		User user = userService.deleteUser(id, request);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("Email incorrect. Please enter valid email address present in database",
					HttpStatus.NOT_FOUND);
		}
	}
	//////////////////

	@RequestMapping(value = "/verify/{token:.+}", method = RequestMethod.GET)
	public ResponseEntity<?> deleteUser(@PathVariable("token") String token, HttpServletRequest request) {

		User user = userService.activateUser(token, request);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("Email incorrect. Please enter valid email address present in database",
					HttpStatus.NOT_FOUND);
		}
	}
}
