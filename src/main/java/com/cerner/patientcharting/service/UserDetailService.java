package com.cerner.patientcharting.service;

import javax.validation.Valid;

import com.cerner.patientcharting.model.User;
import com.cerner.patientcharting.payload.request.LoginRequest;

public interface UserDetailService {
	/**
	 * Create User into the repository (Database)
	 * 
	 * @param user
	 */
	void createUser(User user);
	/**
	 * Generates String jwt
	 * 
	 * @param loginRequest
	 * @return String jwt
	 */
	String login(@Valid LoginRequest loginRequest);

}
