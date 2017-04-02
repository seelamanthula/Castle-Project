package com.company.model.registration;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class TAMainDetails {

	private TADetails taDetails;
	private String day = "";
	private String time = "";
	private String semester = "";
	private String year = "";
	
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public TADetails getTaDetails() {
		return taDetails;
	}
	public void setTaDetails(TADetails taDetails) {
		this.taDetails = taDetails;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
