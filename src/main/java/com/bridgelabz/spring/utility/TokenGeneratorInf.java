package com.bridgelabz.spring.utility;


public interface TokenGeneratorInf {
	String generateToken(String id);
	int authenticateToken(String token);
}