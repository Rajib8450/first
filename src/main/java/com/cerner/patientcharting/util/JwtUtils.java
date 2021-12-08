package com.cerner.patientcharting.util;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	private String secret = "demotoken";
	/**
	 * Generates JWT token by taking in userName
	 * 
	 * @param userName
	 * @return token of type String
	 */
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}
	/**
	 * Creates JWT token by taking in userName,claims
	 * 
	 * @param userName
	 * @param claims
	 * @return token of type String
	 */
	private String createToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}
	/**
	 * Function to extract User Name from created JWT token
	 * 
	 * @param token
	 * @return userName of type String
	 */
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}
	/**
	 * Function to extract the JWT token from the HTTP request header
	 * 
	 * @param request
	 * @return JWT token from HTTP Request
	 */
	public String getToken( HttpServletRequest request ) {

		String authHeader = getAuthHeaderFromHeader(request);
		if ( authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}

		return null;
	}
	/**
	 * Function returns the Authorization header from the Http Request 
	 * 
	 * @param request
	 * @return Authorization header from HTTP Request
	 */
	public String getAuthHeaderFromHeader(HttpServletRequest request) {
		return request.getHeader("Authorization");
	}
	/**
	 * Function returns true i.e. token valid if userName is not null, and generated token has not expired, else returns false
	 * 
	 * @param token
	 * @return True or False depending on whether Token is valid or not
	 */
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

}
