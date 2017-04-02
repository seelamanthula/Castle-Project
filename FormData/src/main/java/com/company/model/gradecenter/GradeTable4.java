package com.company.model.gradecenter;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;

import com.company.model.student.status.ExperimentDetails;

@Scope("session")
public class GradeTable4 {

	private ArrayList<ExperimentDetails> publishedExps = null;	
	private ArrayList<GradeTable3> studentList = null;
	
	public ArrayList<ExperimentDetails> getPublishedExps() {
		return publishedExps;
	}
	public void setPublishedExps(ArrayList<ExperimentDetails> publishedExps) {
		this.publishedExps = publishedExps;
	}
	public ArrayList<GradeTable3> getStudentList() {
		return studentList;
	}
	public void setStudentList(ArrayList<GradeTable3> studentList) {
		this.studentList = studentList;
	}
	
	
}
