package com.company.model;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;

import com.company.model.relation.ModuleDetails;
import com.google.gson.JsonObject;

@Scope("session")
public class ExperimentQuestionContent {

	private ExperimentContent content = null;
	private ArrayList<JsonObject> allQuestions = new ArrayList<JsonObject>();
	private ArrayList<ModuleDetails> moduleDetails = new ArrayList<ModuleDetails>();
	private ArrayList<Long> orderList = new ArrayList<Long>();
	private ArrayList<Long> moduleList = new ArrayList<Long>();
	
	
	
	public ArrayList<Long> getOrderList() {
		return orderList;
	}
	public void setOrderList(ArrayList<Long> orderList) {
		this.orderList = orderList;
	}
	public ArrayList<Long> getModuleList() {
		return moduleList;
	}
	public void setModuleList(ArrayList<Long> moduleList) {
		this.moduleList = moduleList;
	}
	public ArrayList<ModuleDetails> getModuleDetails() {
		return moduleDetails;
	}
	public void setModuleDetails(ArrayList<ModuleDetails> moduleDetails) {
		this.moduleDetails = moduleDetails;
	}

	public ExperimentContent getContent() {
		return content;
	}
	public void setContent(ExperimentContent content) {
		this.content = content;
	}
	public ArrayList<JsonObject> getAllQuestions() {
		return allQuestions;
	}
	public void setAllQuestions(ArrayList<JsonObject> allQuestions) {
		this.allQuestions = allQuestions;
	}
	
	
	
}
