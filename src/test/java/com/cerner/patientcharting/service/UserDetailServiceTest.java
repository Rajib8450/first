package com.cerner.patientcharting.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cerner.patientcharting.exception.RecordAlreadyExsists;
import com.cerner.patientcharting.model.User;
import com.cerner.patientcharting.payload.request.LoginRequest;
import com.cerner.patientcharting.repository.UserRepository;
import com.cerner.patientcharting.util.JwtUtils;
@SpringBootTest
public class UserDetailServiceTest {
	@InjectMocks
	private UserDetailServiceImpl userDetailServiceImpl;
	@Mock
	private JwtUtils jwtUtils;
	@Mock
	AuthenticationManager authenticationManager;
	@Mock
	PasswordEncoder encoder;
	@Mock
	private UserDetailService userDetailService;
	@Mock
	private UserRepository userRepository;
	private User user;
	private LoginRequest loginRequest;
	@BeforeEach
	public void setUp() {
		user=new User();
		loginRequest=new LoginRequest();
		user.setUserName("test");
		user.setPassword("test");
		loginRequest.setUserName("test");
		loginRequest.setPassword("test");
	}
	@Test
	public void createUserTest() {
		doReturn(null).when(userRepository).save(user);
		doReturn(false).when(userRepository).exsistByUserName("test");
		userDetailServiceImpl.createUser(user);
	}
	@Test
	public void createUserFailureTest() {
		doReturn(true).when(userRepository).exsistByUserName("test");
		RecordAlreadyExsists ex=assertThrows(RecordAlreadyExsists.class,()->userDetailServiceImpl.createUser(user));
		Assertions.assertEquals("Name Already Exist", ex.getMessage());
	}
	@Test
	public void loginTest() {
		doReturn("abc").when(jwtUtils).generateToken("test");
		String jwt=userDetailServiceImpl.login(loginRequest);
		Assertions.assertEquals("abc", jwt);
	}
	@Test
	public void loadUserByUsernameTest() {
		doReturn(user).when(userRepository).findByUserName("test");
		String userName=userDetailServiceImpl.loadUserByUsername("test").getUsername();
		String password=userDetailServiceImpl.loadUserByUsername("test").getPassword();
		Assertions.assertEquals("test", userName);
		Assertions.assertEquals("test", password);
		}
}
