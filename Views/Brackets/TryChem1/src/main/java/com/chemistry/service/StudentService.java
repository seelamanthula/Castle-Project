package com.chemistry.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chemistry.dao.QuestionsDAO;
import com.chemistry.dao.StudentQuestionsDAO;
import com.chemistry.lab.Assessment;
import com.chemistry.lab.PreLabDisclaimers;
import com.chemistry.model.Concept;
import com.chemistry.model.Experiment;
import com.chemistry.model.Module;
import com.chemistry.model.PreLab;
import com.chemistry.questions.CheckBoxQuestion;
import com.chemistry.questions.Question;

@Service
//@Scope("prototype")
public class StudentService extends AccountService{
	
	public Experiment[] getExperimentArray() {		
		Experiment[] experiment = getexperimentList().toArray(new Experiment[getexperimentList().size()]);
		return experiment;
	}

	private Map<Module, List<Question>> getAssessment() {
		return getQuestionsDao().getAssessmentQuestions();
	}
	
	public Map<String, Question[]> getArrayAssessment() {
	
		Map<Module, List<Question>> access = getAssessment();
		Map<String, Question[]> assessment = new HashMap<String, Question[]>();
		
		for(Map.Entry<Module, List<Question>> map : access.entrySet()) {
			Module module = map.getKey();
			Question[] question = map.getValue().toArray(new Question[map.getValue().size()]);
			assessment.put(module.getName(), question);
		}
		return assessment;
	}
	
	public Assessment[] getAssessmentArray() {
		Map<Module, List<Question>> assessmentMQ = getAssessment();
		Assessment[] assessment = new Assessment[assessmentMQ.size()];
		int i = 0;
		for(Map.Entry<Module, List<Question>> map :  assessmentMQ.entrySet()) {
			Assessment assess = new Assessment();
			Concept concept = new Concept();
			
			Module module = map.getKey();
			concept.setId(module.getId());
			concept.setName(module.getName());
			
			Question[] questions = map.getValue().toArray(new Question[map.getValue().size()]);
			assess.setConcept(concept);
			assess.setQuestion(questions);
			assessment[i++] = assess;
		}
		
		return assessment;
	}
	
	public Map<Module, PreLab> getPreLabDisclaimers() {
		return getQuestionsDao().getPrelabDisclaimers();
	}
	
	public PreLabDisclaimers[] getPrelabDisclaimerArray() {
		Map<Module, PreLab> prelabDetails = getPreLabDisclaimers();
		
		PreLabDisclaimers[] prelabDisclaimers = new PreLabDisclaimers[prelabDetails.size()];
		int i =0;
		for(Map.Entry<Module, PreLab> modules : prelabDetails.entrySet()) {
			PreLabDisclaimers prelab = new PreLabDisclaimers();
			Concept concept = new Concept();
			
			Module module = modules.getKey();
			concept.setId(module.getId());
			concept.setName(module.getName());
			
			prelab.setConcept(concept);
			prelab.setPrelab(modules.getValue());
			prelabDisclaimers[i++] = prelab;
		}
		return prelabDisclaimers;
	}
	
	public Concept[] getPrelabModuleConcepts() {
		Iterator<Module> iterate = getQuestionsDao().getExperimentModules().iterator();
		Concept[] concepts = new Concept[getQuestionsDao().getExperimentModules().size()];
		int i = 0;
		while(iterate.hasNext()) {
			Module module = iterate.next();
			Concept concept = new Concept();
			concept.setId(module.getId());
			concept.setName(module.getName());
			concepts[i++] = concept;
		}
		return concepts;
	}
}
