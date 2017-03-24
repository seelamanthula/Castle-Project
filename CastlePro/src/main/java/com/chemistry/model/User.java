package com.chemistry.model;

import org.springframework.stereotype.Service;

@Service
public class User {

	private String netId;
	private String firstName;
	private String lastName;
	public String getNetId() {
		return netId.toUpperCase();
	}
	public void setNetId(String netId) {
		this.netId = netId;
	}
	public String getFirstName() {
		return firstName.toLowerCase();
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName.toLowerCase();
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCapitalFirstName() {
		return " "+firstName.substring(0, 1).toUpperCase()+firstName.substring(1);
	}
}
