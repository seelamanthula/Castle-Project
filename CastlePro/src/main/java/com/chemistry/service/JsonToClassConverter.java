package com.chemistry.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chemistry.model.Concept;
import com.chemistry.model.PreLab;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
@Scope("session")
public class JsonToClassConverter {

	public List<PreLab> preLabConverter(JsonArray json) {
	
		List<PreLab> prelab = new ArrayList<PreLab>();
		System.out.println("convertion JSON SIZE : "+json.size());
		for(int i = 0; i < json.size(); i++) {
			if (json.get(i) != null) {
				JsonObject object = (JsonObject) json.get(i);
				System.out.println("I : "+i);
				System.out.println(object);
				prelab.add(preLabElement(object));
			}
		}
		
		return prelab;
	}
	
	private PreLab preLabElement(JsonObject object) {
		JsonObject jsonConcept = (JsonObject) object.get("concept");
		PreLab prelab = new PreLab();

		JsonElement id_Ele = jsonConcept.get("id");
		JsonElement name_Ele = jsonConcept.get("name");

		Concept concept = new Concept();
		concept.setId(id_Ele.getAsInt());
		concept.setName(name_Ele.getAsString());
	
		System.out.println("** Concept **");
		System.out.println("Id : "+concept.getId());
		System.out.println("Name : "+concept.getName());
		
		JsonElement objective_Ele = object.get("objective");
		JsonElement hypothesis_Ele = object.get("hypothesis");
		JsonElement variables_Ele = object.get("variables");
		JsonElement experimental_Ele = object.get("experimentalOutline");
		JsonElement chemical_Ele = object.get("chemicalHazards");
		
		prelab.setConcept(concept);
		prelab.setObjective(objective_Ele.getAsString());
		prelab.setHypothesis(hypothesis_Ele.getAsString());
		prelab.setVariables(variables_Ele.getAsString());
		prelab.setExperimental(experimental_Ele.getAsString());
		prelab.setChemical(chemical_Ele.getAsString());

		System.out.println("Objective : "+prelab.getObjective());
		System.out.println("Hypothesis : "+prelab.getHypothesis());
		System.out.println("Variables ; "+prelab.getVariables());
		System.out.println("Experimental : "+prelab.getExperimental());
		System.out.println("Chemical : "+prelab.getChemical());
		return prelab;
	}
	
	
}
