package com.cerner.patientcharting.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cerner.patientcharting.exception.RecordAlreadyExsists;
import com.cerner.patientcharting.model.User;
import com.cerner.patientcharting.payload.request.LoginRequest;
import com.cerner.patientcharting.service.UserDetailService;

@CrossOrigin("*")
@RestController
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserDetailService userDetailService;
	/**
	 * POST REST API to register new user
	 * 
	 * @param User
	 * @return ResponseEntity
	 */
	@PostMapping("/signup")
	public ResponseEntity<String> createUser(@RequestBody User user)
	{
		try {
			userDetailService.createUser(user);
		}
		catch (RecordAlreadyExsists e) {
			logger.trace("User name is already present");
			return new ResponseEntity<>("Name Already Exist",HttpStatus.CONFLICT);
		}
		logger.info("User is successfully created");
		return new ResponseEntity<>("User Successfully Created",HttpStatus.CREATED);


	}
	/**
	 * POST REST API to validate user login credentials, generate and return JWT token if the user credentials are valid i.e.
	 * matches credentials stored in User Details Repository i.e. Database
	 * 
	 * @param LoginRequest
	 * @return ResponseEntity
	 */
	@PostMapping("/login")
	public ResponseEntity<String> generateToken(@Valid @RequestBody LoginRequest loginRequest)
	{
		String jwt=userDetailService.login(loginRequest);
		logger.info("Jwt token is successfully generated");
		return new ResponseEntity<>(jwt,HttpStatus.ACCEPTED);

	}
}
