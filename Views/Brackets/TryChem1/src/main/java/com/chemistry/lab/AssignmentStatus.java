package com.chemistry.lab;

import com.chemistry.model.Experiment;

public class AssignmentStatus {

	private Experiment experiment;
	private int assessment;
	private int prelab;
	private int experimentLab;
	private byte[] pdfFile;
	
	public Experiment getExperiment() {
		return experiment;
	}
	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}
	public byte[] getPdfFile() {
		return pdfFile;
	}
	public void setPdfFile(byte[] pdfFile) {
		this.pdfFile = pdfFile;
	}
	public int getAssessment() {
		return assessment;
	}
	public void setAssessment(int assessment) {
		this.assessment = assessment;
	}
	public int getPrelab() {
		return prelab;
	}
	public void setPrelab(int prelab) {
		this.prelab = prelab;
	}
	public int getExperimentLab() {
		return experimentLab;
	}
	public void setExperimentLab(int experimentLab) {
		this.experimentLab = experimentLab;
	}
	
}
