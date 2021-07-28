package com.cts.bms.register.service;

import java.util.List;

import com.cts.bms.register.RegisterApplication;
import com.cts.bms.register.config.CustomerProducer;
import com.cts.bms.register.config.UserLoginProducer;
import com.cts.bms.register.model.Customer;
import com.cts.bms.register.model.UserLoginDetails;
import com.cts.bms.register.repository.CustomerRepository;
import com.cts.bms.register.restclient.LoginClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomerDaoImpl implements CustomerDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterApplication.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	LoginClient loginClient;
	
	@Autowired
	private CustomerProducer customerProducer;
	@Autowired
	private UserLoginProducer userLoginProducer;
	@Value("${spring.kafka.topic.registerCustomer}")
	private String REGISTER_CUSTOMER_TOPIC;
	@Value("${spring.kafka.topic.registerCustomerMessage}")
	private String REGISTER_CUSTOMER_MESSAGE_TOPIC;
	@Value("${spring.kafka.topic.updateCustomer}")
	private String UPDATE_CUSTOMER_TOPIC;
	@Value("${spring.kafka.topic.updateCustomerMessage}")
	private String UPDATE_CUSTOMER_MESSAGE_TOPIC;
	@Value("${spring.kafka.topic.failedCustomerMessage}")
	private String FAILED_CUSTOMER_MESSAGE_TOPIC;
	@Value("${spring.kafka.topic.userLogin}")
	private String USER_LOGIN_TOPIC;
	

	@Override
	public List<Customer> getAllRegisteredCustomers() {
        LOGGER.info("Displaying all registered customers");
		return customerRepository.findAll();
	}

	@Override
	public Customer getCustomerByAccountId(Integer accountId) {
		try {
		  LOGGER.info("Displaying customer by account Id");
		return customerRepository.getByAccountId(accountId);
		}
		catch (Exception e) {
			LOGGER.error("Exception");
			return null;
		}
		
	}

	@Override
	public void registerCustomer(Customer customer) {
		 String message;
		try {
		 LOGGER.info("Saving customer details");
		
		customerRepository.save(customer);
		UserLoginDetails userLoginDetials = new UserLoginDetails (customer.getUserName(), customer.getPassword(), null);
		userLoginProducer.send(USER_LOGIN_TOPIC, userLoginDetials);
		message="Registered Successfully";
		customerProducer.send(REGISTER_CUSTOMER_MESSAGE_TOPIC, message);
		}
		catch (Exception e) {
			message = "Process Failed. Please try again.";
			customerProducer.send(FAILED_CUSTOMER_MESSAGE_TOPIC, message);
			LOGGER.error("Exception");
		}
		
	}

	@Override
	public void updateCustomer(Customer updatedValue) {
		 String message;
				
		
				try {
					LOGGER.info("Updating customer details");
					customerRepository.save(updatedValue);
					message = "Updated Successfully";
					customerProducer.send(UPDATE_CUSTOMER_MESSAGE_TOPIC, message);
				}
				catch (Exception e) {
					message = "Process Failed. Please try again.";
					customerProducer.send(FAILED_CUSTOMER_MESSAGE_TOPIC, message);
					LOGGER.error("Exception");
				}
		
		
		
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
