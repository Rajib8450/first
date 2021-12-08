package com.cerner.patientcharting.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cerner.patientcharting.model.PatientDetails;
import com.cerner.patientcharting.service.PatientDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
@SpringBootTest
public class PatientDetailsControllerTest {
	@Autowired
	ObjectMapper objectMapper;
	@Mock
	private PatientDetailService patientDetailService;
	@Autowired
	private PatientDetailsController patientDetailsController;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private MockMvc mockMvc;
	private PatientDetails patientDetails;
	@BeforeEach
	public void setup() {
		mockMvc=MockMvcBuilders.standaloneSetup(patientDetailsController).build();
		patientDetails=new PatientDetails();
		patientDetails.setId("test-100");
		patientDetails.setAddress("test");
		patientDetails.setName("test");
		patientDetails.setMobile("test");
	}
	@AfterEach
	void tearDown() {
		 jdbcTemplate.execute("Delete from patient where patient_id='test-100'");
		 jdbcTemplate.execute("Delete from patient_details where id='test-100'");
	}
	@Test
	public void createPatientSuccessTest() throws Exception {
		String jsonRequest=objectMapper.writeValueAsString(patientDetails);
		MvcResult result=mockMvc.perform(post("/create").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();
		Assertions.assertEquals(201,result.getResponse().getStatus());
		Assertions.assertEquals("Successfully created",result.getResponse().getContentAsString());
	}
	@Test
	public void createPatientFailureTest() throws Exception {
		String jsonRequest=objectMapper.writeValueAsString(patientDetails);
		mockMvc.perform(post("/create").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE));
		MvcResult result=mockMvc.perform(post("/create").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isConflict()).andReturn();
		Assertions.assertEquals(409,result.getResponse().getStatus());
		Assertions.assertEquals("MrnNo already exist",result.getResponse().getContentAsString());
	}
}
