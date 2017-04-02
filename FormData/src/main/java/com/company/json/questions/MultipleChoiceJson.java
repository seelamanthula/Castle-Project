package com.company.json.questions;

import java.util.Iterator;

import org.springframework.context.annotation.Scope;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Scope("session")
public class MultipleChoiceJson {

	private String fileString = "";
	
	public void parseMCQ(JsonObject json) {
		
		JsonElement ele = json.get("questionId");
		System.out.println("Question Id : "+ele.getAsString());
		
		JsonElement ele2 = json.get("question");
		System.out.println("Question : "+ele2.getAsString());
		
		JsonArray arr1 = json.getAsJsonArray("iOptions");
		System.out.println("False Options");
		Iterator<JsonElement> iterate = arr1.iterator();
		while(iterate.hasNext()) {
			JsonElement je = iterate.next();
			System.out.println(je.getAsString());
		}

		JsonArray arr2 = json.getAsJsonArray("vOptions");
		System.out.println("True Options");
		Iterator<JsonElement> iterate2 = arr2.iterator();
		while(iterate.hasNext()) {
			JsonElement je = iterate.next();
			System.out.println(je.getAsString());
		}
		
		JsonArray arr3 = json.getAsJsonArray("hints");
		System.out.println("Hint Options");
		Iterator<JsonElement> iterate3 = arr3.iterator();
		while(iterate.hasNext()) {
			JsonElement je = iterate.next();
			System.out.println(je.getAsString());
		}
		
	//	json.remove("fMaps");
		
		JsonArray arr4 = json.getAsJsonArray("fMaps");
		checkFiles(arr4);
	}
	
	private byte[] filebytes;
	
	private void checkFiles(JsonArray object) {
		
		Iterator<JsonElement> iterate = object.iterator();
		
		System.out.println("In Files");
		while(iterate.hasNext()) {
			JsonObject obj = iterate.next().getAsJsonObject();
			System.out.println("Type : "+obj.get("type").getAsString());
			System.out.println("File to Bytes..");
			System.out.println("Extension : "+obj.get("extension").getAsString());
			System.out.println("Name : "+obj.get("name").getAsString());
			JsonElement sEle = obj.get("fileX");
			this.fileElement = sEle;
			byte[] b  = sEle.getAsString().getBytes();
			System.out.println("Bytes len : "+b.length);
			this.filebytes = b;
		}
	}
	
	private JsonElement fileElement = null;
	
	public JsonElement getFileElement() {
		return fileElement;
	}
	public byte[] getFileBytes() {
		return filebytes;
	}
}
