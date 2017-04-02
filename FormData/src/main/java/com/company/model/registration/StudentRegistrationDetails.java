package com.company.model.registration;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class StudentRegistrationDetails {

	private String day = "";
	private ArrayList<DayTimeDetails> dayDetails = new ArrayList<DayTimeDetails>();
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public ArrayList<DayTimeDetails> getDayDetails() {
		return dayDetails;
	}
	public void setDayDetails(ArrayList<DayTimeDetails> dayDetails) {
		this.dayDetails = dayDetails;
	}
}
