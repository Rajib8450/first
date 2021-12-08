package com.cerner.patientcharting.util;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import com.cerner.patientcharting.service.UserDetailServiceImpl;
@SpringBootTest
public class JwtUtilsTest {
	@Autowired
	private JwtUtils jwtUtils;
	@MockBean
	private UserDetails userDetails;
	@MockBean
	private HttpServletRequest request;
	@MockBean
	private UserDetailServiceImpl userDetailServiceImpl;
	@Test
	public void getUserNameFromJwtTokenTest() {
		String jwt=jwtUtils.generateToken("test");
		Assertions.assertEquals("test", jwtUtils.getUserNameFromJwtToken(jwt));
	}
	@Test
	void validateJwtTokenMalformedJwtExceptionTest() {
		String token = "eyJhbGciOiJIUzI1NiJ9..rupDgnpI-15n3dBXK7N4zG_F-qLj1hAMZWUu_GakWr4";
		Assertions.assertEquals(false, jwtUtils.validateJwtToken(token));
	}
	@Test
	void validateJwtTokenExpiredJwtExceptionTest() {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzNjc3ODM5MSwiaWF0IjoxNjM2NzQyMzkxfQ.6FXSgLgKl2WxP8P4ZpFsCD6zSSOxeQJMYSi2-ly5riI";
		Assertions.assertEquals(false, jwtUtils.validateJwtToken(token));
	}
	@Test
	void validateJwtTokenSignatureExceptionTest() {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzNjc3ODM5MSwiaWF0IjoxNjM2NzQyMzkxfQ.tOIXLiz-2CMzahDt1dNWnmlasasa5nCD-RhJ27XSIVVRK0HA";
		Assertions.assertEquals(false, jwtUtils.validateJwtToken(token));
	}
	@Test
	void validateJwtTokenTokenIllegalArgumentExceptionTest() {
		String token = "";
		Assertions.assertEquals(false, jwtUtils.validateJwtToken(token));
	}
	@Test
	void validateJwtTokenUnsupportedJwtExceptionTest() {
		String token = jwtUtils.generateToken("test");
		String[] parts = token.split(Pattern.quote("."));
		token = parts[0] + "." + parts[1] + ".";
		Assertions.assertEquals(false, jwtUtils.validateJwtToken(token));
	}
	@Test
	void validateJwtTokenTest() {
		String token = jwtUtils.generateToken("test");
		Assertions.assertEquals(true, jwtUtils.validateJwtToken(token));
	}
	@Test
	void getTokenTest() {
		Mockito.when(jwtUtils.getAuthHeaderFromHeader(request)).thenReturn("Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJQcmVzY3JpcHRpb24tYW5kLUFsbGVyZ2llcy1NYW5hZ2VtZW50LUFwcCIsInN1YiI6IlJhbSIsImlhdCI6MTYzNTU3MTMwNiwiZXhwIjoxNjM1NjA3MzA2fQ.rupDgnpI-15n3dBXK7N4zG_F-qLj1hAMZWUu_GakWr4");
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJQcmVzY3JpcHRpb24tYW5kLUFsbGVyZ2llcy1NYW5hZ2VtZW50LUFwcCIsInN1YiI6IlJhbSIsImlhdCI6MTYzNTU3MTMwNiwiZXhwIjoxNjM1NjA3MzA2fQ.rupDgnpI-15n3dBXK7N4zG_F-qLj1hAMZWUu_GakWr4";
		Assertions.assertEquals(token, jwtUtils.getToken(request));
	}
	@Test
	void getTokenNoBearerFailTest() {
		Mockito.when(jwtUtils.getAuthHeaderFromHeader(request)).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJQcmVzY3JpcHRpb24tYW5kLUFsbGVyZ2llcy1NYW5hZ2VtZW50LUFwcCIsInN1YiI6IlJhbSIsImlhdCI6MTYzNTU3MTMwNiwiZXhwIjoxNjM1NjA3MzA2fQ.rupDgnpI-15n3dBXK7N4zG_F-qLj1hAMZWUu_GakWr4");
		Assertions.assertEquals(null, jwtUtils.getToken(request));
	}
	@Test
	void getTokenFailTest() {
		Mockito.when(jwtUtils.getAuthHeaderFromHeader(request)).thenReturn(null);
		Assertions.assertEquals(null, jwtUtils.getToken(request));
	}
}
