package com.company.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.json.questions.MultipleChoiceJson;
import com.company.model.FileContent;
import com.company.model.QuestionContent;
import com.company.persistence.FileStorageService;
import com.company.persistence.QuestionStorageService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
@Scope("session")
public class NavigateQuestions extends MainNavigation {


	private void storeQuestionContent(QuestionContent content) {
		questionStorage.storeQuestionContent(content);
	}

	private void storeQuestionFiles(List<FileContent> fContent, String questionId) {
		questionStorage.storeQuestionFiles(fContent, questionId);
	}
	
	public void removeQuestion(String json) {
		questionStorage.removeQuestion(json);
	}
	
	public void parseAnyQuestion(String json) {

        JsonObject object2 = new JsonParser().parse(json).getAsJsonObject();
        JsonObject object = object2;
        
        String qId = object.get("questionId").getAsString();
		String modId = object.get("moduleId").getAsString();
		String type = object.get("type").getAsString();
		String questionDetails = json;
	//	String questionDetails = getOnlyQuestionier(object);

		List<FileContent> fContent = readFilesFromJsonArraytoString(object.getAsJsonArray("fMaps"), qId);
		if(fContent != null) {
			System.out.println("FContent Size : "+fContent.size());
		}
		
		QuestionContent qContent = new QuestionContent();
		qContent.setQuestionId(qId);
		qContent.setModuleId(modId);
		qContent.setType(type);
		qContent.setQuestionDetails(questionDetails);
		storeQuestionContent(qContent);
		
		storeQuestionFiles(fContent,  qId);
	}

	public void getAllQuestionsForInstructor(String experimentId) {
		
		
		
	}

	private String getOnlyQuestionier(JsonObject object) {
		JsonObject obj = object;
		obj.remove("fMaps");
		return new Gson().toJson(obj);
	}
	
	private ArrayList<FileContent> readFilesFromJsonArraytoString(JsonArray array, String questionId) {
		
		ArrayList<FileContent> content = new ArrayList<FileContent>();
//		System.out.println("Files Array List : "+array);
		
		if(array != null) {
			System.out.println("F Array : "+array.size());
		}
		
		if(array == null || array.size() == 0)
			return null;
		
		Iterator<JsonElement> iterate = array.iterator();
		
		while(iterate.hasNext()) {
			FileContent fContent = new FileContent();
			fContent.setQuestionId(questionId);
			
			JsonObject obj = iterate.next().getAsJsonObject();
			fContent.setType(obj.get("type").getAsString());
			fContent.setExtension(obj.get("extension").getAsString());
			fContent.setName(obj.get("name").getAsString());
			fContent.setFileX(obj.get("fileX").getAsString());
			
			content.add(fContent);
		}
		return content;
	}
	
}
