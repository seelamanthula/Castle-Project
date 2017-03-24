package com.chemistry.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chemistry.dao.StudentStatusDAO;
import com.chemistry.lab.AssignmentStatus;

@Service
@Scope("session")
public class StudentStatusService extends StudentService{

	private StudentStatusDAO studentStatusDao = null;
	private List<AssignmentStatus> assignmentStatusList = null;
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

	public List<AssignmentStatus> getAssignmentStatusList() {
		return assignmentStatusList;
	}

	public int updateAssessmentInExperiment(String netId, int experimentId, String column) {
		int k = studentStatusDao.updateAssessmentInExperiment(netId, experimentId, column);
		System.out.println("Assesment Update : "+k);
		return k;
	}
	
	public void updatePrelabInExperiment(String netId, int experimentId, String column) {
		int k = studentStatusDao.updatePrelabInExperiment(netId, experimentId, column);
		System.out.println("Prelab Update : "+k);	
	}

	public void updateVolumetricLabInExperiment(String netId, int experimentId) {
		System.out.println("Coming to update");
		int k = studentStatusDao.updateVolumetricLabInExperiment(netId, experimentId);
		System.out.println("Volumetric Lab : "+k);
	}
	
	public void updateAcidBaseLabInExperiment(String netId, int experimentId) {
		int k =studentStatusDao.updateAcidBaseLabInExperiment(netId, experimentId);
		System.out.println("AcidBase Lab : "+k);
	}
	
	public AssignmentStatus getStudentAssignmentStatus(String netId, int experimentId) {
		return studentStatusDao.getStudentAssignmentStatus(netId, experimentId);
	}
	
/*	public void updatePDFofPrelab(FileInputStream fis, InputStream stream, String netId,int  experimentId) {
		studentStatusDao.updatePDFFIleinDB(stream, netId, experimentId);
	}*/
	
	public void updatePDFofPrelabVolumetric(byte[] stream, String netId,int  experimentId) {
		studentStatusDao.updatePDFFIleinDB(stream, "pdffile",netId, experimentId);
		studentStatusDao.updatePrelabInExperiment( netId,  experimentId,  "prelab");
	}

	public void updatePDFofPrelabAcid(byte[] stream, String netId,int  experimentId) {
		studentStatusDao.updatePDFFIleinDB(stream, "pdffileacid",netId, experimentId);
		studentStatusDao.updatePrelabInExperiment( netId,  experimentId,  "prelabacid");
	}

	public byte[] getPrelabVolumetric(String netId,int  experimentId) {
		return studentStatusDao.getPrelabPDF(netId,"pdffile", experimentId);
	}

	public byte[] getPrelabAcid( String netId,int  experimentId) {
		return studentStatusDao.getPrelabPDF(netId,"pdffileacid", experimentId);
	}

	public byte[] getLabVolumetricPDF(String netId,int  experimentId) {
		return studentStatusDao.getPrelabPDF(netId,"labpdf", experimentId);
	}

	public byte[] getLabAcidBasePDF(String netId,int  experimentId) {
		return studentStatusDao.getPrelabPDF(netId,"acidlabpdf", experimentId);
	}

	public void updateVolumetricLabPDFInDB(byte[] stream, String netId, int experimentId) {
		studentStatusDao.updatePDFFIleinDB(stream, "labpdf", netId, experimentId);
	}
	
	public void updateAcidBaseLabPDFInDB(byte[] stream, String netId, int experimentId) {
		studentStatusDao.updatePDFFIleinDB(stream, "acidlabpdf", netId, experimentId);
	}
	
	public void updatePostLabPDFInDB(byte[] stream, String netId, int experimentId) {
		studentStatusDao.updatePDFFIleinDB(stream, "postlabpdf", netId, experimentId);
	}

	public void updatePostLabInDB(String netId, int experimentId) {
		int k = studentStatusDao.updatePrelabInExperiment(netId, experimentId, "postlab");
		System.out.println("POStlab update : "+k);
	}
	
	public byte[] getPostLabPDF(String netId,int  experimentId) {
		return studentStatusDao.getPrelabPDF(netId,"postlabpdf", experimentId);
	}
}
