package com.cerner.patientcharting.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cerner.patientcharting.exception.RecordAlreadyExsists;
import com.cerner.patientcharting.model.Patient;
import com.cerner.patientcharting.model.PatientDetails;
import com.cerner.patientcharting.repository.PatientDetailsRepository;
import com.cerner.patientcharting.repository.PatientRepository;

@Service
public class PatientDetailServiceImpl implements PatientDetailService {
	private static final Logger logger = LoggerFactory.getLogger(PatientDetailServiceImpl.class);
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	PatientDetailsRepository patientDetailsRepository;
	/**
	 * Function to create patient details in repository
	 * 
	 * @param patientDetails
	 */
	public void createPatient(PatientDetails patientDetails){
		int mrnNoIndex=patientDetails.getId().indexOf('-');
		String mrnNo=patientDetails.getId().substring(mrnNoIndex+1);
		if(patientRepository.exsistByPatient(mrnNo))
		{
			logger.trace("MrnNo already exist");
			throw new RecordAlreadyExsists("MrnNo exist");
		}
		else {
			logger.info("MrnNo is unique");
			patientDetailsRepository.save(patientDetails);
			Patient patient=new Patient();
			patient.setMrnNo(mrnNo);
			patient.setPatientId(patientDetails.getId());
			patientRepository.save(patient);
		}
	}
	/**
	 * Function to fetch all patient details from repository
	 * 
	 * @return List<PatientDetails>
	 */
	@Override
	public List<PatientDetails> findAll() {
		return patientDetailsRepository.findAll();
	}
	/**
	 * Function to fetch patient details from repository on the basis of input i.e name
	 * 
	 * @param name
	 * @return List<PatientDetails>
	 */
	@Override
	public List<PatientDetails> findByName(String name) {
		return patientDetailsRepository.findByName(name);
	}
}
