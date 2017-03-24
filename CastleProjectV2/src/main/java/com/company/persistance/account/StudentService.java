package com.company.persistance.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.dao.account.AuthenticationDAO;
import com.company.dao.student.StoreStudentExperiment;
import com.company.dao.student.StoreStudentExperimentQuestions;

@Service
@Scope("session")
public class StudentService {

	protected AuthenticationDAO authenticate;
	
	@Autowired
	public void setAuthenticate(AuthenticationDAO authenticate) {
		this.authenticate = authenticate;
	}

	protected StoreStudentExperiment studentExperiment;
	
	@Autowired
	public void setStoreStudentExperiment(StoreStudentExperiment studentExperiment) {
		this.studentExperiment = studentExperiment;
	}

	protected StoreStudentExperimentQuestions studentExperimentQuestions;
	
	@Autowired
	public void setStoreStudentExperimentQuestions(StoreStudentExperimentQuestions studentExperimentQuestions) {
		this.studentExperimentQuestions = studentExperimentQuestions;
	}
	
}
