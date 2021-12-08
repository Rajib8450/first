package com.cerner.patientcharting.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cerner.patientcharting.exception.RecordAlreadyExsists;
import com.cerner.patientcharting.model.PatientDetails;
import com.cerner.patientcharting.repository.PatientDetailsRepository;
import com.cerner.patientcharting.repository.PatientRepository;
import com.cerner.patientcharting.service.PatientDetailService;
@CrossOrigin("*")
@RestController
public class PatientDetailsController {
	private static final Logger logger = LoggerFactory.getLogger(PatientDetailsController.class);
	@Autowired
	PatientDetailsRepository patientDetailsRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	PatientDetailService patientDetailService;
	@PostMapping("/create")
	/**
	 * POST REST API to create new patient
	 * 
	 * @param PatientDetails
	 */
	public ResponseEntity<String> createPatient(@RequestBody PatientDetails patientDetails){
		try {
			patientDetailService.createPatient(patientDetails);
		} catch (RecordAlreadyExsists e) {
			logger.trace("MrnNo already exist");
			return new ResponseEntity<>("MrnNo already exist",HttpStatus.CONFLICT);
		}
		logger.info("Patient Details created");
		return new ResponseEntity<>("Successfully created",HttpStatus.CREATED);
	}
	/**
	 * GET REST API to fetch details of patient on the basis Search Input param i.e. name
	 * 
	 * @param name
	 * @return List<PatientDetails>
	 */
	@GetMapping("/details")
	public ResponseEntity<List<PatientDetails>> findPatientDetails(@RequestParam String name)
	{
		List<PatientDetails> pd=patientDetailService.findByName(name);
		if(pd.isEmpty())
		{
			logger.info("Patient not found with given name");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			logger.info("Patient found with given name");
			return new ResponseEntity<>(pd,HttpStatus.ACCEPTED);
	
		}
	}
	/**
	 * GET REST API to fetch details of all patient
	 * 
	 * @return List<PatientDetails>
	 */
	@GetMapping("/alldetails")
	public ResponseEntity<List<PatientDetails>> findAllPatientDetails()
	{
		List<PatientDetails> pd=patientDetailService.findAll();
		logger.info("List of all patient details obtained");
		return new ResponseEntity<>(pd,HttpStatus.ACCEPTED);
	}
}
