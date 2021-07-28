package com.cts.bms.applyloan.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

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
@Entity
@Builder
@Table(name = "loan_details")
public class Loan {
	@NotNull
	@NumberFormat(style = Style.NUMBER)
	@Id
	@GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
      name = "sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @Parameter(name = "sequence_name", value = "user_sequence"),
        @Parameter(name = "initial_value", value = "100"),
        @Parameter(name = "increment_size", value = "1")
      })
	@Column(name="LOAN_ID")
	private Integer loanId;
	@Column(name="ACCOUNT_ID")
	private Integer accountId;
	@Column(name="LOAN_TYPE")
	private String loanType;
	@Column(name="LOAN_AMOUNT")
	private double loanAmount;
	@Column(name="INTEREST_RATE")
	private double interestRate;
	@Column(name="LOAN_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate loanDate;
	@Column(name="DURATION")
	private Integer duration;
	@Column(name="ANN_INCOME")
	private double annualIncome;
	@Column(name="DESIGNATION")
	private String designation;
	@Column(name="TOTAL_EXP")
	private Integer totalExp;
	@Column(name="CURR_EXP")
	private Integer currExp;
	@Column(name="COMPANY_NAME")
	private String companyName;
	@Column(name="FATHER_NAME")
	private String fatherName;
	@Column(name="FATHER_OCC")
	private String fatherOcc;
	@Column(name="COURSE_NAME")
	private String courseName;
	@Column(name="RATION_CARD")
	private String rationCardNo;
	@Column(name="FATHER_TOTAL_EXP")
	private Integer fatherTotalExp;
	@Column(name="FATHER_CURR_EXP")
	private Integer fatherCurrExp;
	@Column(name="COURSE_FEE")
	private double courseFee;
	@Column(name="FATHER_ANN_INCOME")
	private double fatherAnnIncome;
	
	
	
	
	

}
