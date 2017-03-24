package com.company.model.gradecenter;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;

import com.company.model.relation.ModuleDetails;
import com.company.model.student.status.ExperimentDetails;

@Scope("session")
public class GradeTable2 {

	private ExperimentDetails experiment = null;
	private ArrayList<ModuleDetails> assessmentList = null;
	private ArrayList<ModuleDetails> prelabList = null;
	private int assessmentPoints = 0;
	private int prelabPoints = 0;
	
	
	public int getAssessmentPoints() {
		return assessmentPoints;
	}
	public void setAssessmentPoints(int assessmentPoints) {
		this.assessmentPoints = assessmentPoints;
	}
	public int getPrelabPoints() {
		return prelabPoints;
	}
	public void setPrelabPoints(int prelabPoints) {
		this.prelabPoints = prelabPoints;
	}
	public ExperimentDetails getExperiment() {
		return experiment;
	}
	public void setExperiment(ExperimentDetails experiment) {
		this.experiment = experiment;
	}
	public ArrayList<ModuleDetails> getAssessmentList() {
		return assessmentList;
	}
	public void setAssessmentList(ArrayList<ModuleDetails> assessmentList) {
		this.assessmentList = assessmentList;
	}
	public ArrayList<ModuleDetails> getPrelabList() {
		return prelabList;
	}
	public void setPrelabList(ArrayList<ModuleDetails> prelabList) {
		this.prelabList = prelabList;
	}
	
	
}
