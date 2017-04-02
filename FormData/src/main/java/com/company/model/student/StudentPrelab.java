package com.company.model.student;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class StudentPrelab {

	private String writtenText = "";
	private String pdfContent = "";
	private String ta_s_c_data = "";
	private StudentExperimentModule studentExperimentModule = null;
	
	public String getWrittenText() {
		return writtenText;
	}
	public void setWrittenText(String writtenText) {
		this.writtenText = writtenText;
	}
	public String getPdfContent() {
		return pdfContent;
	}
	public void setPdfContent(String pdfContent) {
		this.pdfContent = pdfContent;
	}
	public String getTa_s_c_data() {
		return ta_s_c_data;
	}
	public void setTa_s_c_data(String ta_s_c_data) {
		this.ta_s_c_data = ta_s_c_data;
	}
	public StudentExperimentModule getStudentExperimentModule() {
		return studentExperimentModule;
	}
	public void setStudentExperimentModule(
			StudentExperimentModule studentExperimentModule) {
		this.studentExperimentModule = studentExperimentModule;
	}
	
	public String getSQLTa_s_c_data() {
		return ta_s_c_data.replace("\"", "\\\"").replace("\n", "\\\n");
	}
	public String getJSONTa_s_c_data() {
		return ta_s_c_data.replace( "\\\"", "\"");
	}
	public String getSQLPdfContent() {
		return pdfContent.replace("\"", "\\\"");
	}
	public String getJSONPdfContent() {
		return pdfContent.replace( "\\\"", "\"");
	}
	public String getSQLWrittenText() {
		return writtenText.replace("\"", "\\\"").replace("\n", "\\\n");
	}
	public String getJSONWrittenText() {
		return writtenText.replace( "\\\"", "\"");
	}
}
