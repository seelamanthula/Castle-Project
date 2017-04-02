package com.company.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.dao.StoreExperimentDetails;
import com.company.model.ExperimentContent;
import com.company.model.ExperimentQuestionContent;
import com.company.model.relation.ModuleDetails;
import com.company.model.relation.ModuleQuestionMaps;
import com.company.model.student.status.ExperimentStatus;
import com.company.persistence.ExperimentModuleStorageService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
@Scope("session")
public class NavigateExperiment extends MainNavigation {

	private void updateModuleAndOrderList(String id, String list, String oList, String mNamesList) {
		experimentModuleService.updateExperimentModuleOrderList(id, list, oList, mNamesList);
	}

	public void updateExperimentModuleList(String json) {
		
		JsonObject object = new JsonParser().parse(json).getAsJsonObject();	
		
		System.out.println("Actual : "+json);
		
		 String id = object.get("expId").getAsString();
		 String list = new Gson().toJson(object.get("mList").getAsJsonArray());
		 String oList = new Gson().toJson(object.get("oList").getAsJsonArray());
		 String mNList = new Gson().toJson(object.get("mNamesList").getAsJsonArray());
		 
		 System.out.println("Question Order List : "+oList);
		 
		 updateModuleAndOrderList(id, list, oList, mNList);
	}
	
	public void storeNewExperimentID(String id) {
		experimentModuleService.storeNewExperimentID(id);
	}

	public void removeModule(String json) {
		experimentModuleService.removeModule(json);
	}
	
	
	public void updateNewExperimentName(String json) {
		 JsonObject object = new JsonParser().parse(json).getAsJsonObject();	
		 
		 String id = object.get("id").getAsString();
		 String name = object.get("name").getAsString();
		 
		 updateNewExperimentName(id, name);
	}
	
	private ExperimentContent getAllSetsFromExperimentTable(String experimentId) {		
		return experimentModuleService.getAllSetsFromExperimentTable(experimentId);		
	}

	private ModuleQuestionMaps getAllQuestionsForInstructor(String experimentId) {
		return questionStorage.getAllQuestionsForInstructor(experimentId);
	}
	
	private ArrayList<Long> convertStringToList(String orderList) {
		
		
		int len = orderList.length();
		System.out.println("Order length : "+len);
		
		if(len == 0) return new ArrayList<Long>();
		
		int start = 1, end = 0, i = 1;
		ArrayList<Long> list = new ArrayList<Long>();
		
		for(i = 1; i < len-1; i++) {
			
			int c = (int) orderList.charAt(i);
			if(c == 44) {
				end = i;
				String s = orderList.substring(start, end);
				list.add(Long.parseLong(s));
				start = i+1;
			}
			
		}
		
		end = i;
		if(end > start)
		list.add(Long.parseLong(orderList.substring(start, end)));
		
		System.out.println("Order list size : "+list.size());
		return list;
	}
	
	private ArrayList<ModuleDetails> arrangeModuleNamesList(Map<Long, String> map) {
		
		if(map.size() == 0) return new ArrayList<ModuleDetails>();
		
		ArrayList<ModuleDetails> list = new ArrayList<ModuleDetails>();
		
		Map<Long, String> hMap = map;
		for (Map.Entry<Long, String> entry : hMap.entrySet())		{
		    ModuleDetails details = new ModuleDetails();
		    details.setId(entry.getKey());
		    details.setName(entry.getValue());
		    list.add(details);
		}	
		
		return list;
	}
	
	public String getInstructorExperimentQuestionsInformation(String experimentId) {
		
		ExperimentContent content = getAllSetsFromExperimentTable(experimentId);	
		ModuleQuestionMaps mQues =	getAllQuestionsForInstructor(experimentId);	
		
		Map<Long, String> quesMap = mQues.getQuestionsList();	
		
		System.out.println("Q Map : "+quesMap.size());
		if(quesMap.size() == 0) return "No Questions";

		ArrayList<ModuleDetails> mDetails = arrangeModuleNamesList(mQues.getModuleNames());
		ArrayList<Long> orderList = convertStringToList(content.getOrderList());
		ArrayList<Long> moduleList = convertStringToList(content.getModuleOrder());


		ArrayList<JsonObject> allQuestions = new ArrayList<JsonObject>();
		
		Iterator<Long> iterate = orderList.iterator();
		while(iterate.hasNext()) {
			
			long s = iterate.next();
			String data = quesMap.get(s);
			System.out.println("ID : "+s);
			
			System.out.println("DATA : "+data);
			if(data != null) {
	   		    JsonObject mainObject = new JsonParser().parse(data.replace("\\\"", "\"")).getAsJsonObject();
				allQuestions.add(mainObject);
			}
		}
		
		ExperimentQuestionContent questionContent = new ExperimentQuestionContent();
		questionContent.setContent(content);
		questionContent.setAllQuestions(allQuestions);
		questionContent.setModuleDetails(mDetails);
		questionContent.setOrderList(orderList);
		questionContent.setModuleList(moduleList);

		String json = new Gson().toJson(questionContent);
		return json;
	}
	
	
	private void updateNewExperimentName(String id, String name) {
		experimentModuleService.updateNewExperimentName(id, name);
	}

	public void updateModuleName(String json) {
		JsonObject object = new JsonParser().parse(json).getAsJsonObject();	
		
		String expId = object.get("expId").getAsString();
		JsonObject mod = object.get("module").getAsJsonObject();
		String moduleId = mod.get("id").getAsString();
		
		updateModuleName(moduleId, expId, mod.get("name").getAsString());
	}
	private void updateModuleName(String moduleId, String experimentId, String name) {
		experimentModuleService.updateModuleName(moduleId, experimentId, name);
	}
	
	public void updateExperimentFile(String json){
		 JsonObject object = new JsonParser().parse(json).getAsJsonObject();	
		 
		 String id = object.get("id").getAsString();
		 String fMaps = new Gson().toJson(object.get("fMaps").getAsJsonArray());
		 System.out.println(fMaps);
		 //.replace("\"", "\\\"")
		 updateExperimentFile(id, fMaps);
	}
	
	public void updateExperimentDueDate(String json) {
		JsonObject object = new JsonParser().parse(json).getAsJsonObject();	
		String id = object.get("eid").getAsString();
		String date = object.get("dDate").getAsString();
		
		updateExperimentDueDate(id, date);
	}
	
	public String getAllExperimentsForInstructor() {
		
		List<ExperimentStatus> list = experimentModuleService.getAllExperimentStatusForInstructor();
		String json = "";
		if(list.size() == 0) {
			json = new Gson().toJson("no");
		} else {
			json = new Gson().toJson(list);
		}
		return json;
	}
	
	private void updateExperimentDueDate(String experimentId, String dueDate) {
		experimentModuleService.updateExperimentDueDate(experimentId, dueDate);
	}
	
	public List<ExperimentContent> getAllExperimentContentForStudent() {
		return experimentModuleService.getAllExperimentContentForStudent();
	}
	
	private void updateExperimentFile(String id, String content) {
		experimentModuleService.updateExperimentFile(id, content);
	}
	
	public boolean checkExperimentFile(String experimentId) {
		return experimentModuleService.checkExperimentFile(experimentId) == 1;
	}
	
	public boolean discardExperiment(String experimentId) {
		return actionButton(experimentId, "DISCARD") == 1;
	}
	
	public boolean draftExperiment(String experimentId) {
		return actionButton(experimentId, "DRAFT") == 1;
	}
	
	public boolean publishExperiment(String experimentId) {
		return actionButton(experimentId, "PUBLISH") == 1;
	}
	
	private int actionButton(String experimentId, String status) {
		System.out.println("Action : "+status);
		return experimentModuleService.actionButton(experimentId, status);
	}
}
