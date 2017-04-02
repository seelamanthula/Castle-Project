package com.company.pdf.model;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class PrelabModel {

	private AssessmentHeader header = null;
	private String objective = "";
	private String hypothesis = "";
	private String variables = "";
	private String experimentOutline = "";
	private String chemicalhazards = "";
	private String lastUpdate = "";
	
	
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public AssessmentHeader getHeader() {
		return header;
	}
	public void setHeader(AssessmentHeader header) {
		this.header = header;
	}
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}
	public String getHypothesis() {
		return hypothesis;
	}
	public void setHypothesis(String hypothesis) {
		this.hypothesis = hypothesis;
	}
	public String getVariables() {
		return variables;
	}
	public void setVariables(String variables) {
		this.variables = variables;
	}
	public String getExperimentOutline() {
		return experimentOutline;
	}
	public void setExperimentOutline(String experimentOutline) {
		this.experimentOutline = experimentOutline;
	}
	public String getChemicalhazards() {
		return chemicalhazards;
	}
	public void setChemicalhazards(String chemicalhazards) {
		this.chemicalhazards = chemicalhazards;
	}
	
	
}
