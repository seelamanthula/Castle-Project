package com.chemistry.lab;

import com.chemistry.model.Concept;
import com.chemistry.questions.Question;

public class PostLab {
	
	private Concept concept;
	private Question[] question;
	
	public Concept getConcept() {
		return concept;
	}
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	public Question[] getQuestion() {
		return question;
	}
	public void setQuestion(Question[] question) {
		this.question = question;
	}
}
