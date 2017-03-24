package com.chemistry.questions;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DataEntry extends Question {

	private int cols;
	private int rows;
	private String units;
	private String columnNames;
	private String difference;
	private String hint;
	private double[] diff = new double[2];
	private List<String> columns;
	
	
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public String getDifference() {
		return difference;
	}
	public void setDifference(String difference) {
		this.difference = difference;
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}
	public double[] getDiff() {
		return diff;
	}
	public void setDiff(double[] diff) {
		this.diff = diff;
	}
	
	
}
