package com.company.model;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.context.annotation.Scope;

import com.company.model.relation.ModuleDetails;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.company.model.student.status.ExperimentDetails;;
/**
 * @author Harish
 *
 */
@Scope("session")
public class ExperimentContent {

	private String name = "";
//	private String experimentId = "";
	private long experimentId;	
	private String dueDate = "";
	private String status = "";

	private String content = "";
	private String moduleOrder = "";
	private String orderList = "";
	private String moduleNamesList = "";

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getExperimentId() {
		return experimentId;
	}
	public void setExperimentId(long experimentId) {
		this.experimentId = experimentId;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getModuleNamesList() {
		return moduleNamesList;
	}
	public void setModuleNamesList(String moduleNamesList) {
		this.moduleNamesList = moduleNamesList;
	}
	public String getOrderList() {
		return orderList;
	}
	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getModuleOrder() {
		return moduleOrder;
	}
	public void setModuleOrder(String moduleOrder) {
		this.moduleOrder = moduleOrder;
	}
	
	public String getRemovedSQLFileContent() {
		
		return content.replace( "\\\"", "\"");
	}
	
}
