package com.chemistry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chemistry.model.Experiment;
import com.chemistry.model.User;
import com.chemistry.service.AuthenticationService;
import com.chemistry.service.JsonToClassConverter;
import com.chemistry.service.JsonToExcelConverter;
import com.chemistry.service.StudentStatusService;
import com.chemistry.util.ExcelGeneration;
import com.chemistry.util.PDFPostLabGenerate;
import com.chemistry.util.PdfGeneration;

@Controller
@Scope("session")
public class Arrange {

	private Experiment experiment = null;
	private StudentStatusService student = null;
	private JsonToClassConverter jsonToClassConverter = null;
	private PdfGeneration generation = null;
	private byte[] prelabPdfs1;
	private byte[] prelabPdfs2;
	
	private JsonToExcelConverter jsonToExcelConverter = null;
	private ExcelGeneration excelGeneration = null;
	private User user = null;
	private AuthenticationService authentication = null;
	private PDFPostLabGenerate postlabGenerate = null;
	
	@Autowired
	public void setAuthentication(AuthenticationService authentication) {
		this.authentication = authentication;
	}

	public AuthenticationService getAuthentication() {
		return authentication;
	}

	public ExcelGeneration getExcelGeneration() {
		return excelGeneration;
	}
	
	public PDFPostLabGenerate getPostlabGenerate() {
		return postlabGenerate;
	}
	@Autowired
	public void setPostlabGenerate(PDFPostLabGenerate postlabGenerate) {
		this.postlabGenerate = postlabGenerate;
	}

	@Autowired
	public void setExcelGeneration(ExcelGeneration excelGeneration) {
		this.excelGeneration = excelGeneration;
	}
	public JsonToExcelConverter getJsonToExcelConverter() {
		return jsonToExcelConverter;
	}
	@Autowired
	public void setJsonToExcelConverter(JsonToExcelConverter jsonToExcelConverter) {
		this.jsonToExcelConverter = jsonToExcelConverter;
	}
	public JsonToClassConverter getJsonToClassConverter() {
		return jsonToClassConverter;
	}
	public PdfGeneration getGeneration() {
		return generation;
	}
	public StudentStatusService getStudent() {
		return student;
	}
	public byte[] getPrelabPdfs1() {
		return prelabPdfs1;
	}
	public void setPrelabPdfs1(byte[] prelabPdfs1) {
		this.prelabPdfs1 = prelabPdfs1;
	}
	public byte[] getPrelabPdfs2() {
		return prelabPdfs2;
	}
	public void setPrelabPdfs(byte[] prelabPdfs2) {
		this.prelabPdfs2 = prelabPdfs2;
	}

	@Autowired
	public void setStudent(StudentStatusService student) {
		this.student = student;
	}

	@Autowired
	public void setJsonToClassConverter(JsonToClassConverter jsonToClassConverter) {
		this.jsonToClassConverter = jsonToClassConverter;
	}

	@Autowired
	public void setGeneration(PdfGeneration generation) {
		this.generation = generation;
	}

	public Experiment getExperiment() {
		return experiment;
	}

	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
