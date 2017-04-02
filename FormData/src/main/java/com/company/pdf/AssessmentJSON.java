package com.company.pdf;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.context.annotation.Scope;

import com.company.pdf.model.AssessmentHeader;
import com.company.pdf.model.AssessmentModelPDF;
import com.company.pdf.model.QuestionReport;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Scope("session")
public class AssessmentJSON {

	public AssessmentModelPDF parseResults(String json) {

		System.out.println("Given Json : "+json);
		JsonObject mainObject = new JsonParser().parse(json).getAsJsonObject();
		JsonObject basics = mainObject.get("basics").getAsJsonObject();	
	
		String netId = basics.get("unetid").getAsString();
		String expId = basics.get("uexpid").getAsString();
		String modId = basics.get("umodid").getAsString();
		String expName = basics.get("uexpname").getAsString();
		String modName = basics.get("umodname").getAsString();

		
		/*	
		System.out.println("N ID : "+netId);
		System.out.println("E ID : "+expId);
		System.out.println("M ID : "+modId);		
			
		System.out.println("E Name : "+eName);
		System.out.println("M Name : "+mName);		*/
		
		AssessmentHeader header = new AssessmentHeader();
		header.setExpId(expId);
		header.setModId(modId);
		header.setNetId(netId);
		header.setModName(modName);
		header.setExpName(expName);
		
		JsonArray answers = mainObject.get("mAnswers").getAsJsonArray();	
		ArrayList<QuestionReport> qReport = answers(answers);		
		String score = mainObject.get("score").getAsString();
		
		AssessmentModelPDF pdfModel = new AssessmentModelPDF();
		pdfModel.setHeader(header);
		pdfModel.setqReport(qReport);
		pdfModel.setScore(score);
		
		return pdfModel;
	}
	
	private ArrayList<QuestionReport> answers(JsonArray array) {
		
		Iterator<JsonElement> iterate = array.iterator();
		ArrayList<QuestionReport> list = new ArrayList<QuestionReport>();
	      
		while(iterate.hasNext()) {
			
   		   JsonObject object = iterate.next().getAsJsonObject();
		   String type = object.get("type").getAsString();
			   
//			   System.out.println("Given Type : "+type+"\n\n");
			if(type.equalsIgnoreCase("mcq")) {
				list.add(typeMCQ(object));
			} else if(type.equalsIgnoreCase("num")) {
				list.add(typeNUM(object));
			}else {
				list.add(typeSHORT(object));
			}		   
		}	
		return list;
	}
	
	private QuestionReport typeNUMSHORT(JsonObject object) {
		   String pointsAwarded = object.get("points").getAsString();
		   String duration = object.get("dTime").getAsString();	
		   String start = object.get("startT").getAsString();
		   String end = object.get("endT").getAsString();	
		   String ans = object.get("studentAns").getAsString();	 
		   String type = object.get("type").getAsString();
		   String attempts = object.get("attempts").getAsString();
		   String question = object.get("question").getAsString();
		   
		   QuestionReport report = new QuestionReport();
		   report.setDuration(duration);
		   report.setEnd(end);
		   report.setPointsAwarded(pointsAwarded);
		   report.setStart(start);
		   report.setType(type);
		   report.setAns(ans);
		   report.setAttempts(attempts);
		   report.setQuestion(question);
		   
			System.out.println("points : "+pointsAwarded);
			System.out.println("Duration : "+duration);
			System.out.println("Start : "+start);		
			System.out.println("End : "+end);	
			System.out.println("Ans : "+ans);
		
			return report;
	}

	private QuestionReport typeNUM(JsonObject object) {
		return typeNUMSHORT(object);
	}
	
	private QuestionReport typeSHORT(JsonObject object) {
		
		return typeNUMSHORT(object);
		 /*  String pointsAwarded = object.get("points").getAsString();
		   String duration = object.get("dTime").getAsString();	
		   String Start = object.get("startT").getAsString();
		   String end = object.get("endT").getAsString();	
		   String ans = object.get("studentAns").getAsString();	 

			System.out.println("points : "+pointsAwarded);
			System.out.println("Duration : "+duration);
			System.out.println("Start : "+Start);		
			System.out.println("End : "+end);	
			System.out.println("Ans : "+ans);*/
	}
	
	private QuestionReport typeMCQ(JsonObject object) {
		
		   String pointsAwarded = object.get("points").getAsString();
		   String duration = object.get("dTime").getAsString();	
		   String start = object.get("startT").getAsString();
		   String end = object.get("endT").getAsString();		   
		   String type = object.get("type").getAsString();
		   String attempts = object.get("attempts").getAsString();
		   String question = object.get("question").getAsString();
		   
		   QuestionReport report = new QuestionReport();
		   report.setDuration(duration);
		   report.setEnd(end);
		   report.setPointsAwarded(pointsAwarded);
		   report.setStart(start);
		   report.setType(type);
		   report.setAttempts(attempts);
		   report.setQuestion(question);
		   
		 /*  System.out.println("points : "+pointsAwarded);
			System.out.println("Duration : "+duration);
			System.out.println("Start : "+start);		
			System.out.println("End : "+end);*/		
			
		  JsonArray ar =  object.get("studentAns").getAsJsonArray();

		  String[] ans = new String[ar.size()];
		  for(int i = 0; i < ar.size(); i++) {
			  String s = ar.get(i).getAsString();
			  System.out.println("Ans : "+s);
			  ans[i] = s;
		  }
		  
		   report.setcAnswers(ans);
		  
		   return report;
	}
	
	private void setUpHeader(AssessmentHeader header) {
		
	}
}
