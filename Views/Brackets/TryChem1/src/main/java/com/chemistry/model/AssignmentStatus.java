package com.chemistry.model;

import org.springframework.stereotype.Component;

public class AssignmentStatus {

	private String netId;
	private com.chemistry.lab.AssignmentStatus[] assignmentStatus;
	
	public String getNetId() {
		return netId;
	}
	public void setNetId(String netId) {
		this.netId = netId;
	}
	public com.chemistry.lab.AssignmentStatus[] getAssignmentStatus() {
		return assignmentStatus;
	}
	public void setAssignmentStatus(
			com.chemistry.lab.AssignmentStatus[] assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}
	
}
