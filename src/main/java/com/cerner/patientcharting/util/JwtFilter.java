package com.cerner.patientcharting.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cerner.patientcharting.service.UserDetailServiceImpl;
@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtils jwtUtil;
	@Autowired
	private UserDetailServiceImpl userDetailService;
	/**
	 * Implementation of the doFilterInternal function which gets the JWT token from the HTTP request and validates it to
	 * make sure it is not expired or incorrect and once validated, it will either approve or block the HTTP Request
	 * 
	 * @throws ServletException
	 * @throws IOException
	 * @param request
	 * @param response
	 * @param filterchain
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token=jwtUtil.getToken(request);
		if(token!=null)
		{
			String userName=jwtUtil.getUserNameFromJwtToken(token);

			if(userName!=null)
			{
				UserDetails userDetails=userDetailService.loadUserByUsername(userName);

				if(jwtUtil.validateJwtToken(token))
				{
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
					.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
