package com.company.model;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class QuestionContent {

	private String questionId;
	private String moduleId;
	private String type;
	private String questionDetails;
	
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getQuestionDetails() {
		return questionDetails;
	}
	public void setQuestionDetails(String questionDetails) {
		this.questionDetails = questionDetails;
	}
	public String getSQLQuestionDetails() {
		return questionDetails.replace("\"", "\\\"");
	}
	
	public String getRemovedSQLQuestionDetails() {
		
		return questionDetails.replace( "\\\"", "\"");
	}
	
}
