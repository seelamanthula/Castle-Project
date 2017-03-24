package com.chemistry.model;

public class PreLab {

	private Concept concept = new Concept();
	private String objective  = "";
	private String hypothesis = "";
	private String variables = "";
	private String experimental = "";
	private String chemical = "";
	

	public Concept getConcept() {
		return concept;
	}
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}
	public String getHypothesis() {
		return hypothesis;
	}
	public void setHypothesis(String hypothesis) {
		this.hypothesis = hypothesis;
	}
	public String getVariables() {
		return variables;
	}
	public void setVariables(String variables) {
		this.variables = variables;
	}
	
	public String getExperimental() {
		return experimental;
	}
	public void setExperimental(String experimental) {
		this.experimental = experimental;
	}
	public String getChemical() {
		return chemical;
	}
	public void setChemical(String chemical) {
		this.chemical = chemical;
	}
	
	public String toString() {
		return "[ PreLab { { concept : { id="+concept.getId()+", name="+concept.getName()+" }}, objective="+objective+", "
				+ "hypothesis= "+hypothesis+", variables="+variables+",experimental outline="+experimental+", "
			    + "chemical hazards="+chemical+"} ]" ;
	}
}
