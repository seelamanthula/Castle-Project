package com.company.json.questions;

import java.util.Iterator;

import org.springframework.context.annotation.Scope;

import com.company.model.account.registration.AccountLogin;
import com.company.model.account.registration.StudentRegistration;
import com.company.model.student.StudentAssessmentModule;
import com.company.model.student.StudentExperimentModule;
import com.company.model.student.StudentPrelab;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Scope("session")
public class UpdateAssessmentPrelabModuleJson {


	public StudentAssessmentModule parseStudentQuestionAnswer(String json) {
		
		System.out.println("JSON : "+json);
		JsonObject mainObject = new JsonParser().parse(json).getAsJsonObject();

		JsonObject basics = mainObject.get("basics").getAsJsonObject();		
		StudentExperimentModule moduleData = updateModuleData(basics);
		
		JsonArray array = mainObject.get("mAnswers").getAsJsonArray();	
		JsonArray qArray = mainObject.get("sQuestions").getAsJsonArray();
		
//		String data = mainObject.get("mAnswers").getAsString();
		
		String data = new Gson().toJson(array);
		String qData = new Gson().toJson(qArray);
		
		int score =  mainObject.get("score").getAsInt();
		
		StudentAssessmentModule assessment = new StudentAssessmentModule();
		assessment.setStudentExperimentModule(moduleData);
		assessment.setData(data);
		assessment.setqData(qData);
		assessment.setScore(score);
		assessment.setmData(json);
		
		return assessment;
	}
	
	private StudentExperimentModule updateModuleData(JsonObject obj) {
	
		String netId = obj.get("unetid").getAsString();
		String expId = obj.get("uexpid").getAsString();
		String modId = obj.get("umodid").getAsString();

		StudentExperimentModule update = new StudentExperimentModule();
		update.setNetId(netId);
		update.setExperimentId(expId);
		update.setModuleId(modId);
		
		return update;
	}
	
	public StudentPrelab updatePrelabAnswersJSON(String json) {
		System.out.println("JSON : "+json);
		
		JsonObject mainObject = new JsonParser().parse(json).getAsJsonObject();

		JsonObject basics = mainObject.get("basics").getAsJsonObject();		
		StudentExperimentModule moduleData = updateModuleData(basics);
		
		StudentPrelab prelab = new StudentPrelab();
		prelab.setStudentExperimentModule(moduleData);
		prelab.setWrittenText(json);
		
		return prelab;
	}
}
