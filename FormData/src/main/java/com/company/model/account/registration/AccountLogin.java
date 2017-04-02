package com.company.model.account.registration;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class AccountLogin {

	private String netId = "";
	private String password = "";
	private String accountType = "";

	public String getNetId() {
		return netId;
	}
	public void setNetId(String netId) {
		this.netId = netId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	
}
