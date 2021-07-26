package com.cts.bms.bmssystem.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	
	private Integer accountId;
	
	private String name;
	
	private String userName;
	
	private String password;
	
	private String address;
	
	private String citizenship;
	
	private String state;
	
	private String country;
	
	private String email;
	
	private String gender;
	
	private String maritalStatus;
	
	private String contactNo;
	
	private LocalDate dob;
	
	private LocalDate regDate;
	
	private String accountType;

	private double initialDeposit;
	
	private String panCardNo;

}
