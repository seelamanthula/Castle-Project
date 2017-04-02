package com.company.model.student;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class StudentExperimentModule {

	private String netId = "";
	private String experimentId = "";
	private String moduleId = "";
	public String getNetId() {
		return netId;
	}
	public void setNetId(String netId) {
		this.netId = netId;
	}
	public String getExperimentId() {
		return experimentId;
	}
	public void setExperimentId(String experimentId) {
		this.experimentId = experimentId;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	
}
