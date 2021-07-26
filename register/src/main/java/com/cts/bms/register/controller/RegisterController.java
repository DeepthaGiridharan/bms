package com.cts.bms.register.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.bms.register.RegisterApplication;

import com.cts.bms.register.model.Customer;
import com.cts.bms.register.service.CustomerDao;


@RestController
@RequestMapping("/register_customer")

public class RegisterController {
	
@Autowired
private CustomerDao registerService;

private static final Logger LOGGER = LoggerFactory.getLogger(RegisterApplication.class);

/*@GetMapping("/all_customers")
public List<Customer> getAllRegisteredCustomers(){
	LOGGER.info("Contoller to get all registered customers");
	return  registerService.getAllRegisteredCustomers();
	
}*/

@GetMapping("/get_accountid_customer")
public ResponseEntity<Customer> getCustomerByAccountId(@RequestHeader("Authorization") String token,@RequestParam Integer accountId) {
	if (!registerService.validateToken(token)) {
		LOGGER.info("Unauthorized");
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}
	
	LOGGER.info("Contoller to get all customer by account id");	
	return new ResponseEntity<Customer>( registerService.getCustomerByAccountId(accountId),HttpStatus.OK);
}

@PostMapping("/save_customer_details")
public ResponseEntity<Void> registerCustomer(@RequestBody Customer customer) {
	//LOGGER.info(customer.getAccountType());
	 registerService.registerCustomer(customer);
	LOGGER.info("Saved customer details");
	return new ResponseEntity<Void>(HttpStatus.OK);	
	
}
@PostMapping("/update_customer_details")
public ResponseEntity<Void> updateCustomer(@RequestHeader("Authorization") String token,@RequestBody  Customer customer,@RequestParam Integer accountId) {
	//LOGGER.info(customer.getAccountType());
	if (! registerService.validateToken(token)) {
		LOGGER.info("Unauthorized");
		return new ResponseEntity<>( HttpStatus.FORBIDDEN);
	}
	 registerService.updateCustomer(accountId,customer);
	LOGGER.info("Updated customer details");	
	return new ResponseEntity<Void>(HttpStatus.OK);	
	
}



}

