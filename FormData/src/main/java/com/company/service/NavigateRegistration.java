package com.company.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.json.others.TARegistration;
import com.company.model.registration.TAMainDetails;
import com.company.persistence.TARegistrationService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
/*
 * This class is only for Student Registration Slots and  
 */
@Service
@Scope("session")
public class NavigateRegistration {

	private TARegistrationService taRegistrationService;
	
	@Autowired
	public void setTaRegistrationService(TARegistrationService taRegistrationService) {
		this.taRegistrationService = taRegistrationService;
	}

	private ArrayList<TAMainDetails> taMainDetails;
	
	public String parseTARegistration(String json) {
        TARegistration ta = new TARegistration();
        taMainDetails = ta.getTAMainDetails();
        return ta.parseTARegistration(json);
	}

	private ArrayList<TAMainDetails> taMainDetails() {
		return taMainDetails;	
	}
	
	// Use this to store data in database
	public void storeRegistration() {
		System.out.println("TA MAIN DET LEN : "+taMainDetails().size());
		taRegistrationService.storeTARegistration(taMainDetails());	
	}
	
	public void modifiedAndNotModifiedJSONStore(String modifiedJson, String notModifiedJson) {
		System.out.println("Modified Json : "+modifiedJson);
		System.out.println("Not Modified Json : "+notModifiedJson);
		try {			
			JsonObject mainObject = new JsonParser().parse(notModifiedJson).getAsJsonObject();
			String semester = mainObject.get("sem").getAsString();
			String year = mainObject.get("year").getAsString();
			
			taRegistrationService.storeModifiedAndNotModifiedJSON(semester, year, modifiedJson, notModifiedJson);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

		
	}
