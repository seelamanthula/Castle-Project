package com.company.pdf.model;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class AssessmentModelPDF {

	private AssessmentHeader header;
	private ArrayList<QuestionReport> qReport;
	private String score;
	
	
	public AssessmentHeader getHeader() {
		return header;
	}
	public void setHeader(AssessmentHeader header) {
		this.header = header;
	}
	public ArrayList<QuestionReport> getqReport() {
		return qReport;
	}
	public void setqReport(ArrayList<QuestionReport> qReport) {
		this.qReport = qReport;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	
}
