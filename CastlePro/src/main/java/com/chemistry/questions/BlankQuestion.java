package com.chemistry.questions;

import com.chemistry.util.QuestionMember;
import com.chemistry.util.QuestionType;

public class BlankQuestion extends Question {

	private String[] hints = new String[4];
	private String ranged;
	private String formula;
	private double[] answerFromTo = new double[2];
	private String units;
	
	
	
	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public double[] getAnswerFromTo() {
		return answerFromTo;
	}

	public void setAnswerFromTo(double[] answerFromTo) {
		this.answerFromTo = answerFromTo;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}


	public String getRanged() {
		return ranged;
	}

	public void setRanged(String ranged) {
		this.ranged = ranged;
	}

	public String getHints(int i) {
		return hints[i];
	}

	public void setHints(String[] hints) {
		this.hints = hints;
	}
	
	public void setHintsSet(int index, String hint) {
		this.hints[index] = hint;
	}
	
}
