package com.company.model.gradecenter;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class UpdateScore {

	private String moduleId = "";
	private String score = "";

	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}

	
}
