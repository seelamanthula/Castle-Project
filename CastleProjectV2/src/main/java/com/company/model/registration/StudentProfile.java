package com.company.model.registration;

import org.springframework.context.annotation.Scope;

import com.company.model.account.registration.StudentRegistration;

@Scope("session")
public class StudentProfile {

	private StudentRegistration studentDetails = null;
	private TAMainDetails classInformation = null;
	
	
	public StudentRegistration getStudentDetails() {
		return studentDetails;
	}
	public void setStudentDetails(StudentRegistration studentDetails) {
		this.studentDetails = studentDetails;
	}
	public TAMainDetails getClassInformation() {
		return classInformation;
	}
	public void setClassInformation(TAMainDetails classInformation) {
		this.classInformation = classInformation;
	}
	
	
}
