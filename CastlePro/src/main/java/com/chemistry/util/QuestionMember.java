package com.chemistry.util;

public class QuestionMember {

	public static String type(QuestionType type) {
		switch(type) {
		case CHECKBOX : {
			return "CHECKBOX QUESTION";
		}
		case BLANK : {
			return "BLANK QUESTION";
		}
		case TEXT : {
			return "TEXT QUESTION";
		}
		case DATAENTRY : {
			return "DATA ENTRY";
		}
		case COMPARISION : {
			return "COMPARISION";
		}

		}
		return null;
	}
	
	public static String section(SectionType section) {
		switch(section) {
		case ASSESSMENT: {
			return "AS_";
		}
		case LAB:{
			return "LB_";
		}
		case POSTLAB:{
			return "PO_";
		}
		}
		return null;
	}
}
