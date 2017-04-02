package com.company.model.student.module;

import org.springframework.context.annotation.Scope;

import com.company.model.relation.ModuleDetails;
import com.company.model.student.status.ExperimentDetails;

@Scope("session")
public class EachModule {

	private String netId = "";
	private String firstName = "";
	private String type = "";
	private ExperimentDetails experiment = null;
	private ModuleDetails module = null;
	private String prelab = "";
	
	public String getNetId() {
		return netId;
	}
	public void setNetId(String netId) {
		this.netId = netId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ExperimentDetails getExperiment() {
		return experiment;
	}
	public void setExperiment(ExperimentDetails experiment) {
		this.experiment = experiment;
	}
	public ModuleDetails getModule() {
		return module;
	}
	public void setModule(ModuleDetails module) {
		this.module = module;
	}
	
	
}
