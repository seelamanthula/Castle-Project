package com.chemistry.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Experiment {

	private int experimentId;
	private String title;
	private Timestamp dueDate;

	public int getExperimentId() {
		return experimentId;
	}
	public void setExperimentId(int experimentId) {
		this.experimentId = experimentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getDueDate() {
		
		return dueDate;
	}
	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	

}
