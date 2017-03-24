package com.chemistry.questions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class Comparision extends Question {

	private String from;
	private String with;	
	private List<Double> fromValues = new ArrayList<Double>();
	private List<Double> withValues = new ArrayList<Double>();
	private String hint;
	
	
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getWith() {
		return with;
	}
	public void setWith(String with) {
		this.with = with;
	}
	public List<Double> getFromValues() {
		return fromValues;
	}
	public void setFromValues(List<Double> fromValues) {
		this.fromValues = fromValues;
	}
	public List<Double> getWithValues() {
		return withValues;
	}
	public void setWithValues(List<Double> withValues) {
		this.withValues = withValues;
	}
}
