package com.company.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.json.others.StudentLoginRegistrationJSON;
import com.company.model.account.registration.AccountLogin;
import com.company.model.account.registration.StudentRegistration;
import com.company.model.registration.StudentProfile;
import com.company.persistance.account.StudentServiceH1;
import com.company.persistance.account.StudentServiceH2;

@Service
@Scope("session")
public class StudentNavigation {

	protected StudentServiceH1 studentService;
	protected StudentRegistration register = null;
	protected AccountLogin login = null;
	protected StudentProfile profile = null;

	@Autowired
	public void setStudentService(StudentServiceH1 studentService) {
		this.studentService = studentService;
	}
	

	protected StudentServiceH2 studentServiceH2;

	@Autowired
	public void setStudentServiceH2(StudentServiceH2 studentServiceH2) {
		this.studentServiceH2 = studentServiceH2;
	}
	

}
