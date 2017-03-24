package com.chemistry.lab;

import com.chemistry.model.Experiment;

public class AssignmentStatus {

	private Experiment experiment;
	private int assessmentVolumetric;
	private int assessmentAcid;

	private int prelabVolumetric;
	private int prelabAcid;

	private int experimentLabVolumetric;
	private int experimentLabAcid;
	private int postlab;

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
	
	public int getExperimentLabVolumetric() {
		return experimentLabVolumetric;
	}
	public void setExperimentLabVolumetric(int experimentLabVolumetric) {
		this.experimentLabVolumetric = experimentLabVolumetric;
	}
	public int getExperimentLabAcid() {
		return experimentLabAcid;
	}
	public void setExperimentLabAcid(int experimentLabAcid) {
		this.experimentLabAcid = experimentLabAcid;
	}
	public int getAssessmentVolumetric() {
		return assessmentVolumetric;
	}
	public void setAssessmentVolumetric(int assessmentVolumetric) {
		this.assessmentVolumetric = assessmentVolumetric;
	}
	public int getAssessmentAcid() {
		return assessmentAcid;
	}
	public void setAssessmentAcid(int assessmentAcid) {
		this.assessmentAcid = assessmentAcid;
	}
	public int getPrelabVolumetric() {
		return prelabVolumetric;
	}
	public void setPrelabVolumetric(int prelabVolumetric) {
		this.prelabVolumetric = prelabVolumetric;
	}
	public int getPrelabAcid() {
		return prelabAcid;
	}
	public void setPrelabAcid(int prelabAcid) {
		this.prelabAcid = prelabAcid;
	}
	public int getPostlab() {
		return postlab;
	}
	public void setPostlab(int postlab) {
		this.postlab = postlab;
	}
	
	
}
