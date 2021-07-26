package com.cts.bms.bmslogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.bms.bmslogin.mdel.UserLoginDetails;

@Repository
public interface UserLoginDetailsRepository extends JpaRepository<UserLoginDetails, String>{
	

}
