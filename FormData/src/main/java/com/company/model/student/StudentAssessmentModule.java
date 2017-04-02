package com.company.model.student;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class StudentAssessmentModule {

	private String data = "";
	private String qData = "";
	private String mData = "";
	
	private int score = 0;
	private StudentExperimentModule studentExperimentModule = null;
	
	
	public StudentExperimentModule getStudentExperimentModule() {
		return studentExperimentModule;
	}
	public void setStudentExperimentModule(
			StudentExperimentModule studentExperimentModule) {
		this.studentExperimentModule = studentExperimentModule;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getSQLData() {
		return data.replace("\"", "\\\"").replace("\n", "\\\n");
	}
	public String getJSONData() {
		return data.replace( "\\\"", "\"").replace("\\\n","\n");
	}
	public String getqData() {
		return qData;
	}
	public void setqData(String qData) {
		this.qData = qData;
	}
	
	public String getmData() {
		return mData;
	}
	public void setmData(String mData) {
		this.mData = mData;
	}
	public String getSQLQuesData() {
		return data.replace("\"", "\\\"");
	}
	public String getJSONQuesData() {
		return qData.replace( "\\\"", "\"");
	}
	public String getQuesAndAns() {
		return "{"+qData+","+qData+"}";
	}
	public String getSQLMixData() {
		return mData.replace("\"", "\\\"");
	}
	public String getJSONMixData() {
		return mData.replace( "\\\"", "\"");
	}
}
