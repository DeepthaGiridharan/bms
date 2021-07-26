package com.cts.bms.bmssystem.model;



import org.springframework.stereotype.Component;

@Component
public class UserLoginDetails {

	
	private String password;
	
	private String username;
	
	private String token;

	public String getPassword() {
		return password;
	}

	public void setPassword(String upassword) {
		this.password = upassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String uname) {
		this.username = uname;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserLoginDetails(String username, String password, String token) {
		super();
		this.password = password;
		this.username = username;
		this.token = token;
	}

	public UserLoginDetails() {

	}

}
