package com.cerner.patientcharting.service;

import com.cerner.patientcharting.model.PatientDetails;

public interface PatientDetailService {
	/**
	 * Stores Patient details into the repository (Database)
	 * 
	 * @param patientDetails
	 */
	public void createPatient(PatientDetails patientDetails);
}
