package com.company.model.registration;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class TADetails {

	private String classNo = "";
	private String taName = "";
	private String taNetId = "";
	private String room = "";
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public String getTaName() {
		return taName;
	}
	public void setTaName(String taName) {
		this.taName = taName;
	}
	public String getTaNetId() {
		return taNetId;
	}
	public void setTaNetId(String taNetId) {
		this.taNetId = taNetId;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	
	
}
