package com.company.json.questions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;

import com.company.model.FileContent;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Scope("session")
public class JsonFiles {

	public ArrayList<FileContent> readFilesFromJsonArraytoString(JsonArray array) {
		
		ArrayList<FileContent> content = new ArrayList<FileContent>();
		
		Iterator<JsonElement> iterate = array.iterator();
		while(iterate.hasNext()) {
			
			FileContent fContent = new FileContent();
			
			JsonObject obj = iterate.next().getAsJsonObject();
		//	System.out.println("Type : "+obj.get("type").getAsString());
			fContent.setType(obj.get("type").getAsString());
	//		System.out.println("Extension : "+obj.get("extension").getAsString());
			fContent.setExtension(obj.get("extension").getAsString());
//			System.out.println("Name : "+obj.get("name").getAsString());
			fContent.setName(obj.get("name").getAsString());
			fContent.setFileX(obj.get("fileX").getAsString());
			
			content.add(fContent);
			return content;
		
		}
		return null;
	}
	
	
}
