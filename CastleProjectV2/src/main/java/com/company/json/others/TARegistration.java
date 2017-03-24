package com.company.json.others;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.context.annotation.Scope;

import com.company.model.registration.DayTimeDetails;
import com.company.model.registration.StudentRegistrationDetails;
import com.company.model.registration.TADetails;
import com.company.model.registration.TAMainDetails;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Scope("session")
public class TARegistration {
	
	private ArrayList<TAMainDetails> taMainDetails = new ArrayList<TAMainDetails>();

	public ArrayList<TAMainDetails> getTAMainDetails() {
		return taMainDetails;
	}
	public String parseTARegistration(String json) {
	
  		  JsonObject mainObject = new JsonParser().parse(json).getAsJsonObject();
		  
  		  String sem = mainObject.get("sem").getAsString();
  		  String year = mainObject.get("year").getAsString();
		  
		   //JsonArray array = new JsonParser().parse(json).getAsJsonArray();	  
  		JsonArray array = mainObject.get("info").getAsJsonArray();	  
		//   System.out.println("TA Size : "+array.size());

		   Iterator<JsonElement> iterate = array.iterator();
		   HashMap<String, HashMap<String, ArrayList<TADetails>>> mainMap = new HashMap<String, HashMap<String, ArrayList<TADetails>>>();

		   while(iterate.hasNext()) {
			  
			   JsonObject object = iterate.next().getAsJsonObject();
			   String day = object.get("Day").getAsString();
			   String time = object.get("Time").getAsString();
			   String room = object.get("Room").getAsString();
			   String taName = object.get("TA_Name").getAsString();
			   String taNetId = object.get("TA_NetID").getAsString();
			   String classNo = object.get("Class_Number").getAsString();
			   
			   TADetails details = new TADetails();
			   details.setClassNo(classNo);
			   details.setRoom(room);
			   details.setTaName(taName);
			   details.setTaNetId(taNetId);
			   
			   TAMainDetails mDetails = new TAMainDetails();
			   mDetails.setDay(day);
			   mDetails.setTime(time);
			   mDetails.setSemester(sem);
			   mDetails.setYear(year);
			   mDetails.setTaDetails(details);
			   
			   taMainDetails.add(mDetails);
			   
			   if(mainMap.containsKey(day)) {
				   HashMap<String, ArrayList<TADetails>> innerMap = mainMap.get(day);
				   ArrayList<TADetails> list = null;
				   if(innerMap.containsKey(time)) {
					   list = innerMap.get(time);					   
				   } else {
					   list = new ArrayList<TADetails>();
				   }
				   list.add(details);
				   innerMap.put(time, list);				   
			   } else {
				   HashMap<String, ArrayList<TADetails>> m1 = new HashMap<String, ArrayList<TADetails>>();
				   ArrayList<TADetails> list =  new ArrayList<TADetails>();
				   list.add(details);
				   m1.put(time, list);
				   mainMap.put(day, m1);				   
			   }			   
		   }
		   
		 return covertTARegistrationModelClass(mainMap);
	}
	
	private String covertTARegistrationModelClass(HashMap<String, HashMap<String, ArrayList<TADetails>>> mainMap) {
		
		ArrayList<StudentRegistrationDetails> list = new ArrayList<StudentRegistrationDetails>();
		
		for (Map.Entry<String, HashMap<String, ArrayList<TADetails>>> entry : mainMap.entrySet()) {
			
			String day = entry.getKey();			
			HashMap<String, ArrayList<TADetails>> innerMap = entry.getValue();
			StudentRegistrationDetails studentDetails = new StudentRegistrationDetails();
			ArrayList<DayTimeDetails> innerDetails = new ArrayList<DayTimeDetails>();
			
			for (Map.Entry<String, ArrayList<TADetails>> innerEntry : innerMap.entrySet()) {
				String time = innerEntry.getKey();
				ArrayList<TADetails> innerList = innerEntry.getValue();
				
				DayTimeDetails timeDetails = new DayTimeDetails();
				timeDetails.setTime(time);
				timeDetails.setDetails(innerList);
				
				innerDetails.add(timeDetails);
			}	
			
			studentDetails.setDay(day);
			studentDetails.setDayDetails(innerDetails);
			
			list.add(studentDetails);
		}	
		// System.out.println("Final Print : "+new Gson().toJson(list));	

		return new Gson().toJson(list);
	}
	
}
