package com.company.model.account.registration;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class StudentRegistration {

	private String email = "";
	private String firstName = "";
	private String lastName = "";
	private AccountLogin login;
	
	
	public AccountLogin getLogin() {
		return login;
	}
	public void setLogin(AccountLogin login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}
