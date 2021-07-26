package com.cts.bms.bmslogin.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cts.bms.bmslogin.controller.LoginController;
import com.cts.bms.bmslogin.mdel.UserLoginDetails;
import com.cts.bms.bmslogin.repository.UserLoginDetailsRepository;

@Service
public class LoginService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserLoginDetailsRepository userLoginDetailsRepository;

	@Override
	public UserDetails loadUserByUsername(String uname) {
		LOGGER.info("Start - loadUserByUsername");
		try {
			UserLoginDetails userLoginDetails = userLoginDetailsRepository.findById(uname).orElse(null);
			if (userLoginDetails != null) {
				return new User(userLoginDetails.getUsername(), userLoginDetails.getPassword(), new ArrayList());
			} else {
				LOGGER.info("End - loadUserByUsername - Username Not Found");

				return null;
			}
		} catch (Exception e) {
			LOGGER.info("Exception - loadUserByUsername - InternalServerError");

			throw e;
		}

	}

	public boolean saveCredentials(UserLoginDetails userLoginDetails) {
		try {
			userLoginDetailsRepository.save(userLoginDetails);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
