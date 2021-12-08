package com.cerner.patientcharting.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import com.cerner.patientcharting.model.User;
import com.cerner.patientcharting.service.UserDetailServiceImpl;
@SpringBootTest
public class JwtFilterTest {
	@Autowired
	private JwtUtils jwtUtil;
	@Autowired
	private JwtFilter jwtFilter;
	@MockBean
	private HttpServletRequest request;
	@Mock
	private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	@MockBean
	private UserDetails userDetails;
	@MockBean
	private HttpServletResponse response;
	@MockBean
	private FilterChain filterChain;
	@Test
	public void doFilterInternalTest() throws ServletException, IOException {
		String token=jwtUtil.generateToken("admin");
		String final_token="Bearer "+token;
		Mockito.when(jwtUtil.getToken(request)).thenReturn(final_token);
		jwtFilter.doFilterInternal(request, response, filterChain);
	}
}
