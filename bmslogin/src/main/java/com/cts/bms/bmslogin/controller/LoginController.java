package com.cts.bms.bmslogin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cts.bms.bmslogin.mdel.UserLoginDetails;
import com.cts.bms.bmslogin.service.JwtUtil;
import com.cts.bms.bmslogin.service.LoginService;

@RestController
public class LoginController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLoginDetails userLoginDetails) {
		try {System.out.println(userLoginDetails.getUsername());
			final UserDetails userDetails = loginService.loadUserByUsername(userLoginDetails.getUsername());
			if (userDetails == null)
				return new ResponseEntity<>("Not Accessible", HttpStatus.FORBIDDEN);

			if (userDetails.getPassword().equals(userLoginDetails.getPassword())) {
				String generatedToken = jwtUtil.generateToken(userDetails);

				return new ResponseEntity<>(generatedToken, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Not Accessible", HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {

			return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/validate")
	public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
		try {
			if (token == null) {

				return new ResponseEntity<>("Not Accessible", HttpStatus.FORBIDDEN);
			} else {
				String token1 = token.substring(7);
				if (jwtUtil.validateToken(token1)) {

					return new ResponseEntity<>("Accessible", HttpStatus.OK);
				} else {
					return new ResponseEntity<>("Not Accessible", HttpStatus.FORBIDDEN);

				}
			}
		} catch (Exception e) {

			return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
}
