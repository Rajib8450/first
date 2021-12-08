package com.cerner.patientcharting.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.cerner.patientcharting.exception.RecordAlreadyExsists;
import com.cerner.patientcharting.model.PatientDetails;
import com.cerner.patientcharting.repository.PatientDetailsRepository;
import com.cerner.patientcharting.repository.PatientRepository;
@SpringBootTest
public class PatientDetailServiceTest {
	@InjectMocks
	private PatientDetailServiceImpl patientDetailServiceImpl;
	@Mock
	private PatientDetailService patientDetailService;
	@Mock
	private PatientDetailsRepository patientDetailRepository;
	@Mock
	private PatientRepository patientRepository;
	private PatientDetails patientDetails;
	@BeforeEach
	public void setUp() {
		patientDetails=new PatientDetails();
		patientDetails.setId("test-100");
		patientDetails.setAddress("test");
		patientDetails.setMobile("test");
		patientDetails.setName("test");
	}
	@Test
	public void createPatientTest() {
		doReturn(null).when(patientDetailRepository).save(patientDetails);
		doReturn(false).when(patientRepository).exsistByPatient("100");
		patientDetailServiceImpl.createPatient(patientDetails);
	}
	@Test
	public void createPatientFailedTest() {
		Mockito.when(patientRepository.exsistByPatient("100")).thenReturn(true);
		RecordAlreadyExsists ex=assertThrows(RecordAlreadyExsists.class,()->patientDetailServiceImpl.createPatient(patientDetails));
		Assertions.assertEquals("MrnNo exist", ex.getMessage());

	}
}
