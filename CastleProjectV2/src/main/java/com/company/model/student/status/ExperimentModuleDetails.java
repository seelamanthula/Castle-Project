package com.company.model.student.status;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;

import com.company.model.relation.ModuleDetails;

@Scope("session")
public class ExperimentModuleDetails extends ExperimentDetails {

	private String type = "";
	private ArrayList<ModuleDetails> modules = null;
	

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList<ModuleDetails> getModules() {
		return modules;
	}
	public void setModules(ArrayList<ModuleDetails> modules) {
		this.modules = modules;
	}
}
