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
	/**
	 * List Patient details info on the basis of name from the repository (Database)
	 * 
	 * @param name
	 */
	public List<PatientDetails> findByName(String name);
	/**
	 * List Patient details info from the repository (Database)
	 * 
	 */
	public List<PatientDetails> findAll();
	/**
	 * Update Patient details info into the repository (Database)
	 * 
	 * @param patientDetails
	 */
	public void update(PatientDetails patientDetails);
}
