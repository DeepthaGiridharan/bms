package com.cts.bms.bmslogin.mdel;

public class AuthorizationResponse {
	private String name;
	private boolean isValid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
