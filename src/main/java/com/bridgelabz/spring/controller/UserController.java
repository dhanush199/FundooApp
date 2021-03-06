package com.bridgelabz.spring.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.annotation.Validated;

import com.bridgelabz.spring.model.User;
import com.bridgelabz.spring.service.UserServiceInf;

@RestController
public class UserController {

	@Autowired
	private UserServiceInf userService;
	//////////////////////////
	User users = new User();

	@Autowired
	@Qualifier("userValidator")
	private org.springframework.validation.Validator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator( validator);
	}

	@GetMapping("index")
	public ModelAndView register(User user) {
		return new ModelAndView("registeruser");
	}
	//////////////////////////

	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@Validated @RequestBody User user, HttpServletRequest request,HttpServletResponse resp) {
		if (userService.register(user, request,resp))
			return new ResponseEntity<String>("Successfully Updated",HttpStatus.OK);
		else
			return new ResponseEntity<String>("Please enter the valid details",HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/loginuser", method = RequestMethod.POST)
	public ResponseEntity<?> loginUser(@RequestBody User user, HttpServletRequest request,HttpServletResponse resp) {

		User exsistingUser = userService.loginUser(user,request,resp);
		if (exsistingUser != null) {

			return new ResponseEntity<User>(exsistingUser, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("Incorrect emailId or password or Your Account is not Activated(Please activate from your registered mai)", HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/updateuser", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestHeader("token") String token, @RequestBody User user,
			HttpServletRequest request) {

		User user2 = userService.updateUser(token, user, request);
		if (user2 != null) {
			return new ResponseEntity<User>(user2, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("Email incorrect. Please enter valid email address present in database",
					HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/deleteuser", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@RequestHeader("token") String token, HttpServletRequest request,HttpServletResponse resp) {

		User user = userService.deleteUser(token, request,resp);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("Email incorrect. Please enter valid email address present in database",
					HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/verify/{token:.+}", method = RequestMethod.GET)
	public ResponseEntity<?> activateUser(@PathVariable("token") String token, HttpServletRequest request) {

		User registeredUser = userService.activateUser(token, request);
		if (registeredUser != null) {
			return new ResponseEntity<User>(registeredUser, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("Email incorrect. Please enter valid email address present in database",
					HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public ResponseEntity<?> forgotPassword(@RequestHeader("token") String token,@RequestBody User newPassword, HttpServletRequest request,HttpServletResponse resp) {

		User user = userService.getUserByEmail(token, request,newPassword,resp);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("Email incorrect. Please enter valid email address present in database",
					HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/resetpassword/{token:.+}", method = RequestMethod.POST)
	public ResponseEntity<?> resetPassword(@PathVariable("token") String token,@RequestBody User newPassword, HttpServletRequest request,HttpServletResponse resp) {

		User user = userService.getUserByEmail(token,request,newPassword,resp);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("Email incorrect. Please enter valid email address present in database",
					HttpStatus.NOT_FOUND);
		}
	}
}
