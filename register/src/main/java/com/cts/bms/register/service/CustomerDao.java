package com.cts.bms.register.service;

import java.util.List;

import com.cts.bms.register.model.Customer;


public interface CustomerDao {

	List<Customer> getAllRegisteredCustomers();
	
	Customer getCustomerByAccountId(Integer accountId);
	
	void registerCustomer(Customer customer);

	void updateCustomer(Customer updateValue);
	
	boolean validateToken(String token);
	
	
}
