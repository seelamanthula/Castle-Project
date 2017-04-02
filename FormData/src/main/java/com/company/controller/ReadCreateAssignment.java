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
	/**
	 * It is an bean instance for NavigateQuestions
	 * @param NavigateQuestions - bean creation
	 */
	@Autowired
	private void setNavigateQuestions(NavigateQuestions navigate) {
		this.navigate = navigate;
	}
	
	protected NavigateExperiment navigateExperiment;

	/**
	 * It is an bean instance for NavigateExperiment
	 * @param NavigateExperiment - bean creation
	 */
	
	@Autowired
	private void setNavigateExperimentService(NavigateExperiment navigateExperiment) {
		this.navigateExperiment = navigateExperiment;
	}
}
