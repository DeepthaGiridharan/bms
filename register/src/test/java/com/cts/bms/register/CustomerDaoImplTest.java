package com.cts.bms.register;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.bms.register.config.CustomerProducer;
import com.cts.bms.register.config.UserLoginProducer;
import com.cts.bms.register.model.Customer;
import com.cts.bms.register.repository.CustomerRepository;
import com.cts.bms.register.restclient.LoginClient;
import com.cts.bms.register.service.CustomerDaoImpl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CustomerDaoImplTest {
	@InjectMocks
	private CustomerDaoImpl customerDaoImpl;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	CustomerProducer customerProducer;

	@Mock
	UserLoginProducer userLogindProducer;

	@Mock
	LoginClient loginClient;
	@Test
	public void testRegisterCustomerSuccess() {
	Customer customer = new Customer();
	Mockito.when(customerRepository.save(customer)).thenReturn(customer);
	Assert.assertTrue(customerDaoImpl.registerCustomer(customer));
	}
	@Test
	public void testRegisterCustomerFailure() {
	Customer customer = new Customer();
	Mockito.when(customerRepository.save(customer)).thenThrow(new RuntimeException());
	Assert.assertFalse(customerDaoImpl.registerCustomer(customer));
	}
	@Test
	public void testUpdateCustomerSuccess() {
	Customer customer = new Customer();
	Mockito.when(customerRepository.save(customer)).thenReturn(customer);
	Assert.assertTrue(customerDaoImpl.updateCustomer(customer));
	}
	@Test
	public void testUpdateCustomerFailure() {
	Customer customer = new Customer();
	Mockito.when(customerRepository.save(customer)).thenThrow(new RuntimeException());
	Assert.assertFalse(customerDaoImpl.updateCustomer(customer));
	}
	@Test
	public void testGetCustomerByAccountIdSuccess() {

		Customer customer = new Customer();
		Mockito.when(customerRepository.getByAccountId((Mockito.anyInt()))).thenReturn(customer);
		Assert.assertEquals(customer, customerDaoImpl.getCustomerByAccountId((Mockito.anyInt())));

	}

	@Test
	public void testGetCustomerByAccountIdFailure() {

		Mockito.when(customerRepository.getByAccountId(Mockito.anyInt())).thenThrow(new RuntimeException());
		Assert.assertNull(customerDaoImpl.getCustomerByAccountId(Mockito.anyInt()));

	}

	@Test
	public void testValidateTokenSuccess() {

		Mockito.when(loginClient.validateToken(Mockito.anyString()))
				.thenReturn(new ResponseEntity<>(HttpStatus.OK));
		assertTrue(customerDaoImpl.validateToken(Mockito.anyString()));
	}

	@Test
	public void testValidateTokenFailure() {

		Mockito.when(loginClient.validateToken(Mockito.anyString()))
				.thenReturn(new ResponseEntity<>(HttpStatus.FORBIDDEN));
		assertFalse(customerDaoImpl.validateToken(Mockito.anyString()));
	}

	@Test
	public void testValidateTokenFailureNull() {

		assertFalse(customerDaoImpl.validateToken(null));
	}

	@Test
	public void testValidateTokenFailureException() {

		Mockito.when(loginClient.validateToken(Mockito.anyString())).thenThrow(new RuntimeException());
		assertFalse(customerDaoImpl.validateToken(Mockito.anyString()));
	}
}
