package com.company.model.gradecenter;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class UpdateAPListsTAComments {

	private String taComments = "";
	private String netId = "";
	private String experimentId = "";
	private ArrayList<UpdateScore> assessmentList = null;
	private ArrayList<UpdateScore> prelabList = null;
	
	
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

	public ArrayList<UpdateScore> getAssessmentList() {
		return assessmentList;
	}

	public void setAssessmentList(ArrayList<UpdateScore> assessmentList) {
		this.assessmentList = assessmentList;
	}

	public ArrayList<UpdateScore> getPrelabList() {
		return prelabList;
	}

	public void setPrelabList(ArrayList<UpdateScore> prelabList) {
		this.prelabList = prelabList;
	}

	public String getTaComments() {
		return taComments;
	}

	public String get_SQL_TaComments() {
		return taComments.replace("\"", "\\\"");
	}
	
	public void setTaComments(String taComments) {
		this.taComments = taComments;
	}
}
