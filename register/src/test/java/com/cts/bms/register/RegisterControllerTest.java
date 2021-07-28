package com.cts.bms.register;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cts.bms.register.controller.RegisterController;
import com.cts.bms.register.model.Customer;
import com.cts.bms.register.service.CustomerDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.joda.time.LocalDate;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class RegisterControllerTest {
	@InjectMocks
	private RegisterController registerController;
	@Autowired
	private MockMvc mockMvc;
	@Mock
	CustomerDao customerService;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();

	}

	@Test
	public void testRegisterCustomer() throws Exception {
		Customer customer = new Customer();
		Mockito.when(customerService.registerCustomer(customer)).thenReturn(true);
		ResultActions actions = mockMvc
				.perform(post("/register_customer/save_customer_details").contentType(MediaType.APPLICATION_JSON).content(asJsonString(customer)));
		actions.andExpect(status().isOk());
	}
	
	@Test
	public void testUpdateCustomerSuccess() throws Exception {
		Customer customer = new Customer();
		Mockito.when(customerService.validateToken(Mockito.anyString())).thenReturn(true);
		Mockito.when(customerService.updateCustomer(customer)).thenReturn(true);
		ResultActions actions = mockMvc.perform(post("/register_customer/update_customer_details").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer)).header("Authorization", Mockito.anyString()));
		actions.andExpect(status().isOk());
	}

	@Test
	public void testUpdateCustomerFailure() throws Exception {
		Customer customer = new Customer();
		Mockito.when(customerService.validateToken(Mockito.anyString())).thenReturn(false);
		Mockito.when(customerService.updateCustomer(customer)).thenReturn(true);
		ResultActions actions = mockMvc.perform(post("/register_customer/update_customer_details").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer)).header("Authorization", Mockito.anyString()));
		actions.andExpect(status().isForbidden());
	}

	@Test
	public void testGetCustomerByAccountIdSuccess() throws Exception {
		Mockito.when(customerService. getCustomerByAccountId(Mockito.anyInt())).thenReturn(new Customer());
		Mockito.when(customerService.validateToken(Mockito.anyString())).thenReturn(true);
		ResultActions actions = mockMvc
				.perform(get("/register_customer/get_accountid_customer?accountId=1000").header("Authorization", Mockito.anyString()));
		actions.andExpect(status().isOk());

	}

	@Test
	public void testGetCustomerByAccountIdFailure() throws Exception {
		Mockito.when(customerService.getCustomerByAccountId(Mockito.anyInt())).thenReturn(new Customer());
		Mockito.when(customerService.validateToken(Mockito.anyString())).thenReturn(false);
		ResultActions actions = mockMvc
				.perform(get("/register_customer/get_accountid_customer?accountId=1").header("Authorization", Mockito.anyString()));
		actions.andExpect(status().isForbidden());

	}

	public static String asJsonString(Customer customer) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(customer);

	}
}
