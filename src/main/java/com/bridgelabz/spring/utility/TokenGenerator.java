package com.bridgelabz.spring.utility;


public interface TokenGenerator {
	public String generateToken(String id);

	public int verifyToken(String token);
}