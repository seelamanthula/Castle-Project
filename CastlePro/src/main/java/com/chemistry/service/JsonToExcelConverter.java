package com.chemistry.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chemistry.model.PreLab;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
@Scope("session")
public class JsonToExcelConverter {

	public Map<String, Object[]> excel1Converter(JsonArray json) {

		Map<String, Object[]> matrix = new TreeMap<String, Object[]>();
		
		for(int i = 0; i < json.size(); i++) {
			JsonObject object = (JsonObject) json.get(i);
			System.out.println("I : "+i);
			if(i == 0)
				matrix.put(i+"", findingObject(object));
			else 
				matrix.put(i+"", findingObject2(object));
						
		}
		return matrix;

	}
	
	private Object[] findingObject(JsonObject object) {
		Object[] obj = new Object[3];
	
		JsonElement one = object.get("one");
		JsonElement two = object.get("two");
		JsonElement three = object.get("three");
		
		obj[0] = one.getAsString();
		obj[1] = two.getAsString();
		obj[2] = three.getAsString();
		
		System.out.println(" "+obj[0]+" "+obj[1]+" "+obj[2]);
		return obj;
	}
	
	private Object[] findingObject2(JsonObject object) {
		Object[] obj = new Object[3];
	
		JsonElement one = object.get("one");
		JsonElement two = object.get("two");
		JsonElement three = object.get("three");
		
		obj[0] = one.getAsDouble();
		obj[1] = two.getAsDouble();
		obj[2] = three.getAsDouble();

		System.out.println(" "+obj[0]+" "+obj[1]+" "+obj[2]);

		return obj;
	}

}
