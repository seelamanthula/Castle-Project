package com.company.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.dao.StoreExperimentDetails;
import com.company.model.ExperimentContent;
import com.company.model.student.status.ExperimentStatus;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
@Scope("session")
public class ExperimentModuleStorageService extends MainService{

	public void updateExperimentModuleOrderList(String id, String moduleList, String orderList, String moduleNamesList) {
		int k  = storeExperimentDetails.updateExperimentModuleList(id, moduleList, orderList, moduleNamesList);
		System.out.println("Update Module List : "+k);
	}
	
	public void storeNewExperimentID(String id) {
		int k = 0;
		if(storeExperimentDetails.findExperimentID(id) == 0)
			k = storeExperimentDetails.storeNewExperimentID(id);

		System.out.println("New Experiment ID : "+k);
	}

	public ExperimentContent getAllSetsFromExperimentTable(String experimentId){
		return storeExperimentDetails.getAllSetsFromExperimentTable(experimentId);
	}
	
	public void updateNewExperimentName(String id, String name) {
		int k  = storeExperimentDetails.updateExperimentName(id, name);
		System.out.println("New Experiment Name : "+k);
	}

	public void removeModule(String moduleId) {
		
		if(storeExperimentDetails.removeModuleFromModuleTable(moduleId) == 1)
			storeExperimentDetails.removeQuestionsInModule(moduleId);
	}
	
	private void storeNewModuleID(String moduleId, String experimentId) {
		int k  = storeExperimentDetails.storeNewModuleID(moduleId, experimentId);
		System.out.println("New Module ID : "+k);
	}

	public void updateModuleName(String moduleId, String experimentId, String name) {
		if(storeExperimentDetails.findExperimentModule(moduleId) == 0)
			storeNewModuleID(moduleId, experimentId);
		
		int k = storeExperimentDetails.updateModuleName(moduleId, experimentId, name);
		System.out.println("Update Module Name : "+k);
	}
	
	public void updateExperimentFile(String id, String content) {
		int k = storeExperimentDetails.updateExperimentFile(id, content);
		System.out.println("Update Experiemnt File : "+k);
	}
	
	public int checkExperimentFile(String experimentId) {
		int k  = storeExperimentDetails.findExperimentFileExists(experimentId);
		System.out.println("Check Experiemnt File : "+k);
		return k;
	}
	
	public int actionButton(String experimentId, String status) {
		return storeExperimentDetails.actionButton(experimentId, status);
	}
	
	public List<ExperimentStatus> getAllExperimentStatusForInstructor() {
		return storeExperimentDetails.getAllExperimentDetailsForInstructor();
	}
	
	public List<ExperimentContent> getAllExperimentContentForStudent() {
		return storeExperimentDetails.getAllExperimentDetailsForStudent();
	}
	
	public int updateExperimentDueDate(String experimentId, String dueDate) {		
		return storeExperimentDetails.updateExperimentDueDate(experimentId, dueDate);
	}
	
	public String[] getAllModuleIDsInExperiment(String experimentId) {
		String json = storeExperimentDetails.getAllModules(experimentId);
		JsonArray arr = new JsonParser().parse(json).getAsJsonArray();	

		String[] mods = new String[arr.size()];
		for(int i = 0; i < mods.length; i++) {
			mods[i] = arr.get(i).getAsString();
		}
		
		return mods;
	}
	
	
	// Discard Button
	public int removeExperimentFromExperimentTable(String experimentId) {
		if(storeExperimentDetails.removeExperimentFromExperimentTable(experimentId) == 1) {
				if(removeModulesQuestionsInTables(experimentId) == 1)
					return 1;
		}
		
		return 0;
	}
	
	// Discard Button	
	private int removeAllModulesInExperiment(String experimentId) {
		return storeExperimentDetails.removeAllModulesInExperiment(experimentId);
	}
	
	// Discard Button	
	private int removeModulesQuestionsInTables(String experimentId) {
		return discardButton(experimentId);
	}

	// Discard Button
	public int discardButton(String experimentId) {
				
		String[] mods = getAllModuleIDsInExperiment(experimentId);
		
		int k = 0;
		for(int i = 0; i < mods.length; i++) {
			k = storeExperimentDetails.removeQuestionsInModule(mods[i]);
			if(k == 0) return 0;
		}
		
		k = removeAllModulesInExperiment(experimentId);
		if(k == 0) return 0;
		
		return 1;
	}
	
}
