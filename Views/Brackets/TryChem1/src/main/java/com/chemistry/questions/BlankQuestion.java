package com.chemistry.questions;

import com.chemistry.util.QuestionMember;
import com.chemistry.util.QuestionType;

public class BlankQuestion extends Question {

	private String[] hints = new String[4];
	private String range;
	private String formula;
	
	
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
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
