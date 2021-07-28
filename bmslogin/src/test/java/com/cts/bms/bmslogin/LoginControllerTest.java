package com.cts.bms.bmslogin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cts.bms.bmslogin.controller.LoginController;
import com.cts.bms.bmslogin.mdel.UserLoginDetails;
import com.cts.bms.bmslogin.repository.UserLoginDetailsRepository;
import com.cts.bms.bmslogin.service.JwtUtil;
import com.cts.bms.bmslogin.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class LoginControllerTest {
	@Autowired
	private LoginController loginController;
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	LoginService loginService;

	@Autowired
	UserLoginDetailsRepository userLoginDetailsRepository;

	@Autowired
	JwtUtil jwtUtil;

	private String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyNjYyNzg2NSwiaWF0IjoxNjI2NjI2MDY1fQ.93fcPeGaZZC6meozhXmt0vRttMV-GFjv4j1g-3Iqn4k";

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();

	}

	@Test
	public void testLoginSuccess() throws Exception {
		userLoginDetailsRepository.save(new UserLoginDetails("test", "test", null));
		ResultActions actions = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new UserLoginDetails("test", "test", null))));
		actions.andExpect(status().isOk());

	}

	@Test
	public void testLoginFailure() throws Exception {
		ResultActions actions = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new UserLoginDetails("", "", null))));
		actions.andExpect(status().isForbidden());

	}

	@Test
	public void testLoginPasswordFailure() throws Exception {
		userLoginDetailsRepository.save(new UserLoginDetails("test", "test", null));
		ResultActions actions = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new UserLoginDetails("test", "admin", null))));
		actions.andExpect(status().isForbidden());

	}

	@Test
	public void testLoginExceptionFailure() throws Exception {
		ResultActions actions = mockMvc.perform(
				post("/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(new UserLoginDetails())));

		actions.andExpect(status().isInternalServerError());

	}

	@Test
	public void testValidateSuccess() throws Exception {
		ResultActions actions = mockMvc.perform(get("/validate").header("Authorization", TOKEN));
		actions.andExpect(status().isForbidden());

	}

	@Test
	public void testValidateFailure() throws Exception {
		ResultActions actions = mockMvc.perform(get("/validate").header("Authorization", "Bearer testing"));
		actions.andExpect(status().isForbidden());

	}

	@Test
	public void testValidateEmptyFailure() throws Exception {
		ResultActions actions = mockMvc.perform(get("/validate").header("Authorization", ""));
		actions.andExpect(status().isForbidden());

	}

	public static String asJsonString(UserLoginDetails userLoginDetails) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(userLoginDetails);

	}
}

