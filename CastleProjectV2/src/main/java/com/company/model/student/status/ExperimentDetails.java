package com.company.model.student.status;

import org.springframework.context.annotation.Scope;

import com.google.gson.JsonElement;

@Scope("session")
public class ExperimentDetails {

	private String netid = "";
	private String expName = "";
	private String expId = "";
	private String pdf = "";
	private String duedate = "";
	
	
	public String getDuedate() {
		return duedate;
	}
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}
	public String getPdf2() {
		return pdf;
	}
	public void setPdf2(String pdf2) {
		this.pdf = pdf2;
	}
/*	public void setPdf(JsonElement pdf) {
		this.pdf = pdf;
	}*/
	public String getNetId() {
		return netid;
	}
	public void setNetId(String netId) {
		this.netid = netId;
	}
	public String getExperimentName() {
		return expName;
	}
	public void setExperimentName(String experimentName) {
		this.expName = experimentName;
	}
	public String getExperimentId() {
		return expId;
	}
	public void setExperimentId(String experimentId) {
		this.expId = experimentId;
	}
}
