package com.company.service.account;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.json.others.StudentLoginRegistrationJSON;
import com.company.model.account.registration.AccountLogin;
import com.company.model.account.registration.StudentRegistration;
import com.company.model.registration.StudentProfile;
import com.company.model.student.status.ExperimentModuleDetails;
import com.company.model.student.status.ExperimentStatus;
import com.company.model.student.status.StudentStatus;
import com.company.persistance.account.StudentServiceH1;
import com.google.gson.Gson;

@Service
@Scope("session")
public class StudentNavigationH1 extends StudentNavigation {

	StudentLoginRegistrationJSON student = new StudentLoginRegistrationJSON();		
	
	public String parseStudentRegistrationInformation(String json) {
	
		register = student.parseStudentRegistrationInformation(json);
		
		if(validateLogin(register.getLogin()) == 0) {
			String s = getStudentRegistrationInfo();
			if(s == null) return "slots_not_available";
			return s;
		}
		
		return null;
	}
	
	public int checkStudentGradeCenter(String netId) {
		int k = studentService.checkStudentGradeCenter(netId);
		return k;
	}
	
	public String getStudentExperimentAssessmentModulesScore(String netId, String experimentId) {
		ExperimentModuleDetails details = studentService.getStudentExperimentAssessmentModulesScore(netId, experimentId);
		return new Gson().toJson(details);
	}
	
	public String getStudentExperimentPrelabModulesScore(String netId, String experimentId) {
		ExperimentModuleDetails details = studentService.getStudentExperimentPrelabModulesScore(netId, experimentId);
		return new Gson().toJson(details);
	}
	
	public String getStudentStatus() {
		ArrayList<ExperimentStatus> list = studentService.getStudentExperimentStatus(login.getNetId());
		
		StudentStatus status = new StudentStatus();
		status.setFirstname(this.profile.getStudentDetails().getFirstName());
		status.setNetid(login.getNetId());
		status.setPublishedExperiments(list);
		
		return new Gson().toJson(status);
	}
	
	public String storeStudentInformation(String classno) {
		int k = studentService.storeStudentAccount(register, classno);
		if(k == 0) return "slot_is_no_more"; else if(k == 2) return "account-exists";
		
		return "navigate_student";
	}
	
	public AccountLogin parseAndValidateAccountLogin(String json) {
		this.login = student.parseUserLoginInformation(json);
	//	if(login.getAccountType().equalsIgnoreCase("instructor")) return login;
		
		if(login.getAccountType().equalsIgnoreCase("instructor") && checkInstructor(login) == 1) return login;
		
		int k = validateLogin(login);
		if(k == 0) return null;

		this.profile = extractUserProfile(login);
		return login;
	}
	
	
	private int checkInstructor(AccountLogin login) {
		
		if(login.getNetId().equalsIgnoreCase("IN12345") && 
				login.getPassword().equalsIgnoreCase("default"))
			return 1;
		
		if(login.getNetId().equalsIgnoreCase("MC12345") && 
				login.getPassword().equalsIgnoreCase("default"))
			return 1;
		
		return 0;
	}
	
	public AccountLogin getAccountLogin() {
		return this.login;
	}

	public StudentProfile getStudentProfile() {
		return this.profile;
	}
	
	private int validateLogin(AccountLogin login) {		
		return studentService.validateUser(login);
	}
	
	private StudentProfile extractUserProfile(AccountLogin login) {
		return studentService.extractUserProfile(login);
	}
	private String getStudentRegistrationInfo() {
		return studentService.getStudentRegistrationInfo();
	}
}
