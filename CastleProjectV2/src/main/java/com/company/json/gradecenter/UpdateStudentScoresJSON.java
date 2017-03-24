package com.company.json.gradecenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.model.gradecenter.UpdateAPListsTAComments;
import com.company.model.gradecenter.UpdateScore;
import com.company.model.registration.TADetails;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Scope("session")
public class UpdateStudentScoresJSON {

	
	public UpdateAPListsTAComments parseStudentExperimentScores(String json) {
		
		System.out.println("Given Json : "+json);
		JsonObject mainObject = new JsonParser().parse(json).getAsJsonObject();
		JsonObject currentExp = mainObject.get("currentExp").getAsJsonObject();		
		String taComments = mainObject.get("taComments").getAsString();
		
		JsonObject expDetails = currentExp.get("experiment").getAsJsonObject();
		
		String netId = expDetails.get("netid").getAsString();
		String expId = expDetails.get("expId").getAsString();

/*		UpdateScore update = new UpdateScore();
		update.setNetId(netId);
		update.setExperimentId(expId);*/
		
		JsonArray assessmentList = currentExp.get("assessmentList").getAsJsonArray();
		JsonArray prelabList = currentExp.get("prelabList").getAsJsonArray();
		
/*		 ArrayList<UpdateScore> aList = updateAssessmentPrelabTables(assessmentList, update);
		 ArrayList<UpdateScore> pList = updateAssessmentPrelabTables(prelabList, update);*/
		 
		 ArrayList<UpdateScore> aList = updateAssessmentPrelabTables(assessmentList);
		 ArrayList<UpdateScore> pList = updateAssessmentPrelabTables(prelabList);
		 
		 UpdateAPListsTAComments aplists = new UpdateAPListsTAComments();
		 aplists.setTaComments(taComments);
		 aplists.setAssessmentList(aList);
		 aplists.setPrelabList(pList);
		 aplists.setNetId(netId);
		 aplists.setExperimentId(expId);
		 
		 return aplists;
	}

	private ArrayList<UpdateScore> updateAssessmentPrelabTables(JsonArray array) {
		
		 Iterator<JsonElement> iterate = array.iterator();
		 
		 ArrayList<UpdateScore> updates = new ArrayList<UpdateScore>();
		 
		   while(iterate.hasNext()) {
			   UpdateScore update2 = new UpdateScore();
			   
			   JsonObject object = iterate.next().getAsJsonObject();
			   String id = object.get("id").getAsString();
			   String score = object.get("score").getAsString();
			   
			   update2.setModuleId(id);
			   update2.setScore(score);
			   
			   updates.add(update2);
		   }
		
		   return updates;
	}
	
	
}
