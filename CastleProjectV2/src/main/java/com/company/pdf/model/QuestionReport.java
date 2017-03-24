package com.company.pdf.model;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class QuestionReport {

	private String pointsAwarded = "";
	private String duration = "";	
	private String Start = "";
	private String end = "";	
	private String ans = "";	 
	private String type = "";
	private String[] cAnswers;
	private String question = "";
	private String attempts = "";
	
	
	public String getAttempts() {
		return attempts;
	}
	public void setAttempts(String attempts) {
		this.attempts = attempts;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String[] getcAnswers() {
		return cAnswers;
	}
	public void setcAnswers(String[] cAnswers) {
		this.cAnswers = cAnswers;
	}
	public String getPointsAwarded() {
		return pointsAwarded;
	}
	public void setPointsAwarded(String pointsAwarded) {
		this.pointsAwarded = pointsAwarded;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getStart() {
		return Start;
	}
	public void setStart(String start) {
		Start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getAns() {
		return ans;
	}
	public void setAns(String ans) {
		this.ans = ans;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
