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

import com.cerner.patientcharting.model.User;
import com.cerner.patientcharting.payload.request.LoginRequest;
import com.cerner.patientcharting.service.UserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
@SpringBootTest
public class UserControllerTest {
	@Autowired
	ObjectMapper objectMapper;
	@Mock
	private UserDetailService userDetailService;
	@Autowired
	private UserController userController;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private MockMvc mockMvc;
	private User user;
	private LoginRequest loginRequest;
	@BeforeEach
	public void setup() {
		mockMvc=MockMvcBuilders.standaloneSetup(userController).build();
	    user=new User();
	    loginRequest=new LoginRequest();
	    loginRequest.setUserName("test");
	    loginRequest.setPassword("test");
		user.setUserName("test");
		user.setPassword("test");
	}
	@AfterEach
	void tearDown() {
		jdbcTemplate.execute("Delete from user where user_name='test'");
	}
	@Test
	public void createUserTest() throws Exception {
		String jsonRequest=objectMapper.writeValueAsString(user);
		MvcResult result=mockMvc.perform(post("/signup").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();
		Assertions.assertEquals(201,result.getResponse().getStatus());
		Assertions.assertEquals("User Successfully Created",result.getResponse().getContentAsString());
	}
	@Test
	public void createUserRecordAlreadyExsistTest() throws Exception {
		String jsonRequest=objectMapper.writeValueAsString(user);
		mockMvc.perform(post("/signup").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE));
		MvcResult result=mockMvc.perform(post("/signup").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isConflict()).andReturn();
		Assertions.assertEquals(409,result.getResponse().getStatus());
		Assertions.assertEquals("Name Already Exist",result.getResponse().getContentAsString());
	}
	@Test
	public void generateTokenTest() throws Exception {
		String jsonRequest=objectMapper.writeValueAsString(user);
		mockMvc.perform(post("/signup").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE));
		String login_Request=objectMapper.writeValueAsString(loginRequest);
		MvcResult result=mockMvc.perform(post("/login").content(login_Request).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isAccepted()).andReturn();
		Assertions.assertEquals(202,result.getResponse().getStatus());
	}
}
