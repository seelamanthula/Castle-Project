package com.chemistry.model;

public class Assignment {

	private int assignmentId;
	private int moduleId;
	private int checkboxId;
	private int blankId;
	private int textId;
	private int prelabId;
	private int dataEntryId;
	private int comparisionId;
	
	
	public int getComparisionId() {
		return comparisionId;
	}
	public void setComparisionId(int comparisionId) {
		this.comparisionId = comparisionId;
	}
	public int getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public int getCheckboxId() {
		return checkboxId;
	}
	public void setCheckboxId(int checkboxId) {
		this.checkboxId = checkboxId;
	}
	public int getBlankId() {
		return blankId;
	}
	public void setBlankId(int blankId) {
		this.blankId = blankId;
	}
	public int getTextId() {
		return textId;
	}
	public void setTextId(int textId) {
		this.textId = textId;
	}
	public int getPrelabId() {
		return prelabId;
	}
	public void setPrelabId(int prelabId) {
		this.prelabId = prelabId;
	}
	
	public int getDataEntryId() {
		return dataEntryId;
	}
	public void setDataEntryId(int dataEntryId) {
		this.dataEntryId = dataEntryId;
	}
	@Override
	public String toString() {
		return "Assignment [assignmentId="+ assignmentId +", moduleId="+ moduleId +", checkboxId="+ checkboxId +", "
				+ "blankId="+ blankId +", textId="+ textId +",prelabId="+ prelabId +"]";
	}
}
