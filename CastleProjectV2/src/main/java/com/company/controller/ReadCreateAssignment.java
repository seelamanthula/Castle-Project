package com.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.company.persistence.QuestionStorageService;
import com.company.service.NavigateExperiment;
import com.company.service.NavigateQuestions;

@Controller
@Scope("session")
public class ReadCreateAssignment {
	
	protected NavigateQuestions navigate;
	
	@Autowired
	private void setNavigateQuestions(NavigateQuestions navigate) {
		this.navigate = navigate;
	}
	

	protected NavigateExperiment navigateExperiment;

	@Autowired
	private void setNavigateExperimentService(NavigateExperiment navigateExperiment) {
		this.navigateExperiment = navigateExperiment;
	}
}
