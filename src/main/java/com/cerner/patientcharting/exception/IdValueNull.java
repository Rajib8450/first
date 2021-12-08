package com.cerner.patientcharting.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdValueNull extends RuntimeException{
	private static final Logger logger = LoggerFactory.getLogger(IdValueNull.class.getSimpleName());
	/**
	 * Exception thrown when a user tries to update a record with id null value
	 */
	public IdValueNull(String message) {
		super(message);
		logger.warn("Id value is null");
	}
}
