package com.cerner.patientcharting.service;

import java.util.List;

import com.cerner.patientcharting.model.PatientDetails;

public interface PatientDetailService {
	/**
	 * Stores Patient details into the repository (Database)
	 * 
	 * @param patientDetails
	 */
	public void createPatient(PatientDetails patientDetails);

	public List<PatientDetails> findByName(String name);

	public List<PatientDetails> findAll();
}
