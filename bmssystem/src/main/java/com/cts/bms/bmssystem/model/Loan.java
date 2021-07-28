package com.cts.bms.bmssystem.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder

public class Loan {
	
	private Integer loanId;
	
	private Integer accountId;
	
	private String loanType;
	
	private double loanAmount;
	
	private double interestRate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate loanDate;
	
	private Integer duration;
	
	private double annualIncome;
	
	private String designation;
	
	private Integer totalExp;
	
	private Integer currExp;
	
	private String companyName;
	
	private String fatherName;
	
	private String fatherOcc;
	
	private String courseName;
	
	private String rationCardNo;
	
	private Integer fatherTotalExp;
	
	private Integer fatherCurrExp;
	
	private double courseFee;
	
	private double fatherAnnIncome;
	
	
	
	
	

}
