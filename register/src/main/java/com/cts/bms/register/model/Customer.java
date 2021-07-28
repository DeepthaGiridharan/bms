package com.cts.bms.register.model;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "user_registration")
public class Customer {
	
	@NotNull
	@NumberFormat(style = Style.NUMBER)
	@Id
	@GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
      name = "sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @Parameter(name = "sequence_name", value = "user_sequence"),
        @Parameter(name = "initial_value", value = "1000"),
        @Parameter(name = "increment_size", value = "1")
      })
	private Integer accountId;
	@NotNull
	@Column(name="NAME")
	private String name;
	@NotNull
	@Column(name="USER_NAME")
	private String userName;
	@NotNull
	@Column(name="PASWWORD")
	private String password;
	@NotNull
	@Column(name="ADDRESS")
	private String address;
	
	@NotNull
	@Column(name="CITIZENSHIP")
	private String citizenship;
	@NotNull
	@Column(name="STATE")
	private String state;
	@NotNull
	@Column(name="COUNTRY")
	private String country;
	@NotNull
	@Column(name="EMAIL")
	private String email;
	@NotNull
	@Column(name="GENDER")
	private String gender;
	@NotNull
	@Column(name="MARITAL_STATUS")
	private String maritalStatus;
	@NotNull
	@Column(name="CONTACT_NO")
	private String contactNo;
	@NotNull
	@Column(name="DOB")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dob;
	@NotNull
	@Column(name="REG_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate regDate;
	@NotNull
	@Column(name="ACCOUNT_TYPE")
	private String accountType;
	@NotNull
	@Column(name="INITIAL_DEPOSIT")
	private double initialDeposit;
	@NotNull
	@Column(name="PAN_CARD_NO")
	private String panCardNo;
	

}
