package com.company.model;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class FileContent {

	private String questionId;
	private String type;
	private String extension;
	private String name;
	private String fileX;
	
	
	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFileX() {
		return fileX;
	}
	
	public void setFileX(String fileX) {
		this.fileX = fileX;
	}
}
