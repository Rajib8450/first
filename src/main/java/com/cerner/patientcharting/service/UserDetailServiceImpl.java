package com.cerner.patientcharting.service;

import java.util.ArrayList;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cerner.patientcharting.exception.RecordAlreadyExsists;
import com.cerner.patientcharting.model.User;
import com.cerner.patientcharting.payload.request.LoginRequest;
import com.cerner.patientcharting.repository.UserRepository;
import com.cerner.patientcharting.util.JwtUtils;



@Service
public class UserDetailServiceImpl implements UserDetailsService,UserDetailService{
	private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtUtils jwtUtil;
	@Override
	/**
	 * Takes in userName as input and returns the user details corresponding to the input user name
	 * 
	 * @return UserDetails
	 * @param userName
	 * @throws UsernameNotFoundException
	 */
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user=userRepository.findByUserName(userName);
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());
	}
	/**
	 * Function to create User in repository
	 * 
	 * @param user
	 */
	@Override
	public void createUser(User user) {
		if(userRepository.exsistByUserName(user.getUserName())) {
			logger.trace("Name already exist");
			throw new RecordAlreadyExsists("Name Already Exist");
		}
		user.setPassword(encoder.encode(user.getPassword()));
		logger.info("Password is encoded");
		userRepository.save(user);

	}
	/**
	 * Function to calling generateToken method to get the token
	 * 
	 * @param loginRequest
	 * @return String jwt
	 */
	@Override
	public String login(@Valid LoginRequest loginRequest) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		return jwtUtil.generateToken(loginRequest.getUserName());
	}
}
