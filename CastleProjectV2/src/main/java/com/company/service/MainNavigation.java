package com.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.persistence.ExperimentModuleStorageService;
import com.company.persistence.QuestionStorageService;

@Service
@Scope("session")
public class MainNavigation {

	QuestionStorageService questionStorage;
	
	@Autowired
	private void setQuestionStorageService(QuestionStorageService questionStorage) {
		this.questionStorage = questionStorage;
	}
	

	ExperimentModuleStorageService experimentModuleService;
	
	@Autowired
	private void setExperimentModuleStorageService(ExperimentModuleStorageService experimentModuleService) {
		this.experimentModuleService = experimentModuleService;
	}
	
	
}
