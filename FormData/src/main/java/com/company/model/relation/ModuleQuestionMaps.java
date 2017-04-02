package com.company.model.relation;

import java.util.Map;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class ModuleQuestionMaps {

	private Map<Long, String> moduleNames = null;
	private Map<Long, String> questionsList = null;
	
	public Map<Long, String> getModuleNames() {
		return moduleNames;
	}
	public void setModuleNames(Map<Long, String> moduleNames) {
		this.moduleNames = moduleNames;
	}
	public Map<Long, String> getQuestionsList() {
		return questionsList;
	}
	public void setQuestionsList(Map<Long, String> questionsList) {
		this.questionsList = questionsList;
	}
	
	
}
