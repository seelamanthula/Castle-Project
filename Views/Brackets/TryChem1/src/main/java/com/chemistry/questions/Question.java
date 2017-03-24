package com.chemistry.questions;

import java.util.Set;

public class Question {
	private String question;
	private String answer;
	private String section;
	private String type;
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}

	public void setQuestionType(String type) {
		this.type = type;
	}
	public String getQuestionType() {
		return type;
	}
	
	public Set getInvalidValues() {
		return null;
	}
	
}
