package com.chemistry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chemistry.dao.StudentStatusDAO;
import com.chemistry.lab.AssignmentStatus;

@Service
public class StudentStatusService extends StudentService{

	private StudentStatusDAO studentStatusDao = null;
	private List<AssignmentStatus> assignmentStatusList = null;
	private com.chemistry.lab.AssignmentStatus[] assignmentStatusArray;
	private String netId;
	
	
	@Autowired
	public void setStudentStatus(StudentStatusDAO studentStatusDao) {
		this.studentStatusDao = studentStatusDao;
	}
	
	public String getNetId() {
		return netId;
	}

	public void setNetId(String netId) {
		this.netId = netId;
	}

	private List<AssignmentStatus> getAssignmnetStatusList(String netId) {
		setAssignmentStatusList(studentStatusDao.getStudentAssignmentStatusList(netId, getexperimentList()));
		return getAssignmentStatusList();
	}

	public List<AssignmentStatus> getAssignmentStatusList() {
		return assignmentStatusList;
	}

	public void setAssignmentStatusList(List<AssignmentStatus> assignmnetStatusList) {
		this.assignmentStatusList = assignmnetStatusList;
	}

	public com.chemistry.lab.AssignmentStatus[] getAssignmentStatusArray() {
		return assignmentStatusArray;
	}

	public void setAssignmentStatusArray(
			com.chemistry.lab.AssignmentStatus[] assignmentStatusArray) {
		this.assignmentStatusArray = assignmentStatusArray;
	}

	private com.chemistry.lab.AssignmentStatus[] getAssignmentStatusArray(String netId) {
		com.chemistry.lab.AssignmentStatus[] assignmentStatusArray = getAssignmentStatusList().toArray(new com.chemistry.lab.AssignmentStatus[getAssignmentStatusList().size()]);
		setAssignmentStatusArray(assignmentStatusArray);
		return getAssignmentStatusArray();
	}
	
	public void setStudentStatus(String netId) {
		setNetId(netId);
		getAssignmentStatusArray(netId);
		getAssignmnetStatusList(netId);
	}
	
	public void updateAssessmentInExperiment(int experimentId) {
		studentStatusDao.updateAssessmentInExperiment(getNetId(), experimentId);
	}
	
	public void updatePrelabInExperiment(int experimentId) {
		studentStatusDao.updatePrelabInExperiment(getNetId(), experimentId);
	}
}
