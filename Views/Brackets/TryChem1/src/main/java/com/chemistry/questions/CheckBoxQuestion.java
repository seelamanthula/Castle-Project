package com.chemistry.questions;

import java.util.HashSet;
import java.util.Set;

import com.chemistry.util.QuestionMember;
import com.chemistry.util.QuestionType;

public class CheckBoxQuestion  extends Question {

	private String[] invalid = new String[4];
	private Set<String> invalidValues = new HashSet<String>();
	
	public String getInvalid(int i) {
		return invalid[i];
	}
	public void setInvalid(String[] invalid) {
		this.invalid = invalid;
	}
	public void setInvalidSets(int i, String answers) {
		invalid[i] = answers;
	}
	
	public Set getInvalidValues() {
		return invalidValues;
	}
	public void setInvalidValues(Set invalidValues) {
		this.invalidValues = invalidValues;
	}
	
}
