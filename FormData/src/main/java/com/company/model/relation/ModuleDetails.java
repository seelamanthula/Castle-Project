package com.company.model.relation;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class ModuleDetails {

	private long id;
	private String name = "";
	private int score = 0;
	private ArrayList<String> questionIds = null;
	
	
	public ArrayList<String> getQuestionIds() {
		return questionIds;
	}
	public void setQuestionIds(ArrayList<String> questionIds) {
		this.questionIds = questionIds;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	
}
