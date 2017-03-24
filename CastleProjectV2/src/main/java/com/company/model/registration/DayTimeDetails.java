package com.company.model.registration;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class DayTimeDetails {

	private String time = "";
	private ArrayList<TADetails> details = new ArrayList<TADetails>();
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public ArrayList<TADetails> getDetails() {
		return details;
	}
	public void setDetails(ArrayList<TADetails> details) {
		this.details = details;
	}
}
