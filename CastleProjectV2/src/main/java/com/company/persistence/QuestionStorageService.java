package com.company.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.dao.StoreQuestions;
import com.company.demo.Store1;
import com.company.model.FileContent;
import com.company.model.QuestionContent;
import com.company.model.relation.ModuleQuestionMaps;

@Service
@Scope("session")
public class QuestionStorageService extends MainService {

	public void removeQuestion(String questionId) {
		storeQuestions.removeQuestion(questionId);
	}
	
	public void storeQuestionContent(QuestionContent content) {
		
		int up = storeQuestions.findQuestionExists(content.getQuestionId());
		System.out.println("Is Question Exists : "+up);
		
		int k = -1;
		if(up == 1)
			k = storeQuestions.updateQuestionContent(content);
		else 
			k = storeQuestions.storeQuestionContent(content);
		
		System.out.println("K status : "+k);
	}
	
	public void storeQuestionFiles(List<FileContent> files, String questionId) {
		if(files != null) {
			HashMap<String, ArrayList<FileContent>> cFiles = categorizeFiles(files);
			
			int k = storeQuestions.storeQuestionFiles(cFiles, questionId);
			System.out.println("Files status : "+k);
		}
	}
	
	
	public ModuleQuestionMaps getAllQuestionsForInstructor(String experimentId) {
		return storeQuestions.retrieveAllQuestionListForInstructor(experimentId);
	}
	
	
	private HashMap<String, ArrayList<FileContent>> categorizeFiles(List<FileContent> files) {
		
		Iterator<FileContent> iterate = files.iterator();
		HashMap<String, ArrayList<FileContent>> map = new HashMap<String, ArrayList<FileContent>>();
		
		while(iterate.hasNext()) {
			FileContent content = iterate.next();
			if(map.containsKey(content.getType())) {
				ArrayList list = map.get(content.getType());
				list.add(content);
				map.put(content.getType(), list);
			} else {
				ArrayList li = new ArrayList<FileContent>();
				li.add(content);
				map.put(content.getType(), li);
			}
		}
		
		return map;
	}
}
