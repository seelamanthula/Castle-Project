package com.company.model.gradecenter;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;

import com.company.model.student.status.ExperimentDetails;

@Scope("session")
public class GradeTable3 {

	private String netId = "";
	private String taName = "";
	private ArrayList<GradeTable2> experiments = null;
	private int studentAssessmentPoints = 0;
	private int studentPrelabPoints = 0;
	
	
	public String getTaName() {
		return taName;
	}
	public void setTaName(String taName) {
		this.taName = taName;
	}
	public int getStudentAssessmentPoints() {
		return studentAssessmentPoints;
	}
	public void setStudentAssessmentPoints(int studentAssessmentPoints) {
		this.studentAssessmentPoints = studentAssessmentPoints;
	}
	public int getStudentPrelabPoints() {
		return studentPrelabPoints;
	}
	public void setStudentPrelabPoints(int studentPrelabPoints) {
		this.studentPrelabPoints = studentPrelabPoints;
	}
	public String getNetId() {
		return netId;
	}
	public void setNetId(String netId) {
		this.netId = netId;
	}

	public ArrayList<GradeTable2> getStudents() {
		return experiments;
	}
	public void setStudents(ArrayList<GradeTable2> experiments) {
		this.experiments = experiments;
	}
	
	
	
	
}
