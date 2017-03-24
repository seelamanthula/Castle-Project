package com.company.model.student.status;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class ExperimentStatus {
	
	// ExperimentId
	private String expid = "";
	// ExperimentName
	private String Name = "";
	private String status = "";
	private String dueDate = "";
	private int assessmentScore = 0;
	private int prelabScore = 0;
	private String taComments = "";
	
	
	
	public String getTaComments() {
		return taComments;
	}
	public void setTaComments(String taComments) {
		this.taComments = taComments;
	}
	public int getAssessmentScore() {
		return assessmentScore;
	}
	public void setAssessmentScore(int assessmentScore) {
		this.assessmentScore = assessmentScore;
	}
	public int getPrelabScore() {
		return prelabScore;
	}
	public void setPrelabScore(int prelabScore) {
		this.prelabScore = prelabScore;
	}
	public String getExperimentId() {
		return expid;
	}
	public void setExperimentId(String id) {
		this.expid = id;
	}
	public String getExperimentName() {
		return Name;
	}
	public void setExperimentName(String name) {
		this.Name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}	
}
