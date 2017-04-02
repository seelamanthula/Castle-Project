package com.company.model.student.status;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class StudentStatus {

	private String netid = "";
	private String firstname = "";
	private ArrayList<ExperimentStatus> publishedExperiments = null;
	public String getNetid() {
		return netid;
	}
	public void setNetid(String netid) {
		this.netid = netid;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public ArrayList<ExperimentStatus> getPublishedExperiments() {
		return publishedExperiments;
	}
	public void setPublishedExperiments(
			ArrayList<ExperimentStatus> publishedExperiments) {
		this.publishedExperiments = publishedExperiments;
	}
	
	
}
