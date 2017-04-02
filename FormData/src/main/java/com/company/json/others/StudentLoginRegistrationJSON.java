package com.company.json.others;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.model.account.registration.AccountLogin;
import com.company.model.account.registration.StudentRegistration;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Scope("session")
public class StudentLoginRegistrationJSON {

	public StudentRegistration parseStudentRegistrationInformation(String json) {
		
		JsonObject mainObject = new JsonParser().parse(json).getAsJsonObject();

		String netId = mainObject.get("netId").getAsString();
		String email = mainObject.get("email").getAsString();
		String pswd = mainObject.get("pswd").getAsString();
		String type = mainObject.get("type").getAsString();
		String firstName = mainObject.get("firstname").getAsString();
		String lastName = mainObject.get("lastname").getAsString();		
		
		StudentRegistration student = new StudentRegistration();
		student.setEmail(email);
		student.setFirstName(firstName);
		student.setLastName(lastName);
	
		AccountLogin login = new AccountLogin();
		login.setNetId(netId);
		login.setPassword(pswd);
		login.setAccountType(type);
		
		student.setLogin(login);
		return student;
	}
	
	public AccountLogin parseUserLoginInformation(String json) {
		
		JsonObject mainObject = new JsonParser().parse(json).getAsJsonObject();

		String netId = mainObject.get("netId").getAsString();
		String pswd = mainObject.get("pswd").getAsString();
		String type = mainObject.get("type").getAsString();
	
		AccountLogin login = new AccountLogin();
		login.setNetId(netId);
		login.setPassword(pswd);
		login.setAccountType(type);
		
		return login;
	}
}
