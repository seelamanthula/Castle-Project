package com.company.model.gradecenter;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.context.annotation.Scope;

import com.company.model.relation.ModuleDetails;
import com.company.model.student.status.ExperimentDetails;
@Scope("session")
public class GradeTable {

	private String netId = "";
	private Map<ExperimentDetails, ArrayList<ModuleDetails>> assessmentDetails = null;
	private Map<ExperimentDetails, ArrayList<ModuleDetails>> prelabDetails = null;
	
	public String getNetId() {
		return netId;
	}
	public void setNetId(String netId) {
		this.netId = netId;
	}
	public Map<ExperimentDetails, ArrayList<ModuleDetails>> getAssessmentDetails() {
		return assessmentDetails;
	}
	public void setAssessmentDetails(
			Map<ExperimentDetails, ArrayList<ModuleDetails>> assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}
	public Map<ExperimentDetails, ArrayList<ModuleDetails>> getPrelabDetails() {
		return prelabDetails;
	}
	public void setPrelabDetails(
			Map<ExperimentDetails, ArrayList<ModuleDetails>> prelabDetails) {
		this.prelabDetails = prelabDetails;
	}
}
