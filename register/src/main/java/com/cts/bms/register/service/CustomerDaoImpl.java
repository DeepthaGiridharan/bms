package com.cts.bms.register.service;

import java.util.List;

import com.cts.bms.register.RegisterApplication;
import com.cts.bms.register.model.Customer;
import com.cts.bms.register.repository.CustomerRepository;
import com.cts.bms.register.restclient.LoginClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomerDaoImpl implements CustomerDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterApplication.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	LoginClient loginClient;
	

	@Override
	public List<Customer> getAllRegisteredCustomers() {
        LOGGER.info("Displaying all registered customers");
		return customerRepository.findAll();
	}

	@Override
	public Customer getCustomerByAccountId(Integer accountId) {
		  LOGGER.info("Displaying customer by account Id");
		return customerRepository.getByAccountId(accountId);
	}

	@Override
	public void registerCustomer(Customer customer) {
		 LOGGER.info("Saving customer details");
		 //LOGGER.info(customer.getUserName());
		customerRepository.save(customer);
	}

	@Override
	public void updateCustomer(Integer accountId,Customer updatedValue) {
		Customer customer= getCustomerByAccountId(accountId);
		customer.setAccountType(updatedValue.getAccountType());
		customer.setAddress(updatedValue.getAddress());
		customer.setCitizenship(updatedValue.getCitizenship());
		customer.setContactNo(updatedValue.getContactNo());
		customer.setCountry(updatedValue.getCountry());
		customer.setDob(updatedValue.getDob());
		customer.setEmail(updatedValue.getEmail());
		customer.setGender(updatedValue.getGender());
		customer.setInitialDeposit(updatedValue.getInitialDeposit());
		customer.setMaritalStatus(updatedValue.getMaritalStatus());
		customer.setName(updatedValue.getName());
		customer.setPanCardNo(updatedValue.getPanCardNo());
		customer.setPassword(updatedValue.getPassword());
		customer.setRegDate(updatedValue.getRegDate());
		customer.setState(updatedValue.getState());
		customer.setUserName(updatedValue.getUserName());
		customerRepository.save(customer);
		
		
		
		
	}

	@Override
	public boolean validateToken(String token) {
		
		try {
			if (token == null) {
				LOGGER.info("Unauthorized");
				return false;
			}
			HttpStatus loginStatusCode = loginClient.validateToken(token).getStatusCode();
			if (loginStatusCode.equals(HttpStatus.OK)) {
				LOGGER.info("Success");
				return true;
			} else {
				LOGGER.info("Unauthorized");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Error");
			return false;
		}
	}

}
