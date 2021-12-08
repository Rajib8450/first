package com.cerner.patientcharting.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecordAlreadyExsists extends RuntimeException{
	private static final Logger logger = LoggerFactory.getLogger(RecordAlreadyExsists.class.getSimpleName());
	/**
	 * Exception thrown when a user tries to add a record which is already exist
	 */
	public RecordAlreadyExsists(String message) {
		super(message);
		logger.warn("Record already exists");
	}
}

