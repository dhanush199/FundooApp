package com.bridgelabz.spring.utility;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import com.bridgelabz.spring.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenGeneratorImpl implements TokenGeneratorInf {

	public String generateToken(String id) {
		return Jwts.builder().setId(id).claim("roles", "existingUser").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretKey").compact();
	}

	public int authenticateToken(String token)
	{
		Claims claims = Jwts.parser()        
				.setSigningKey(DatatypeConverter.parseBase64Binary("secretKey"))
				.parseClaimsJws(token).getBody();
		System.out.println("ID: " + claims.getId());
		return Integer.parseInt(claims.getId());
	}
	
	public String generateUrl(String joinUrl,User user,HttpServletRequest req,HttpServletResponse resp) {
		StringBuffer url=req.getRequestURL();
		String verificationUrl=url.substring(0, url.lastIndexOf("/"));
		String token = generateToken(String.valueOf(user.getId()));
		verificationUrl=verificationUrl+joinUrl+token;
		resp.setHeader("userId", token);
		return verificationUrl;
	}

}


