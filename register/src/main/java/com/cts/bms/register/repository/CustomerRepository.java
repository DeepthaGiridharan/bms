package com.cts.bms.register.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.bms.register.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

	Customer getByAccountId(Integer accountId);
}
