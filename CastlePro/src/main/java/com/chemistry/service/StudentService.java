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
import com.chemistry.lab.LabExperiment;
import com.chemistry.lab.PostLab;
import com.chemistry.lab.PreLabDisclaimers;
import com.chemistry.model.Concept;
import com.chemistry.model.Experiment;
import com.chemistry.model.Module;
import com.chemistry.model.PreLab;
import com.chemistry.questions.CheckBoxQuestion;
import com.chemistry.questions.Question;

@Service
@Scope("session")
public class StudentService extends AccountService{
	
	public Experiment[] getExperimentArray() {		
		Experiment[] experiment = getExperimentsList().toArray(new Experiment[getExperimentsList().size()]);
		return experiment;
	}

	private Map<Module, List<Question>> getAssessment() {
		return getQuestionsDao().getAssessmentQuestions();
	}
	
	public Map<Module,List<Question>> getLab() {
		return getQuestionsDao().getLabQuestions();
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
	
//	public PreLabDisclaimers[] getPrelabDisclaimerArray() {
	
	public PreLab[] getPreLabs() {
		return getQuestionsDao().getPrelabs();
	}
	
	public PreLab[] getPrelabDisclaimerArray() {
		Map<Module, PreLab> prelabDetails = getPreLabDisclaimers();
		
	//	PreLabDisclaimers[] prelabDisclaimers = new PreLabDisclaimers[prelabDetails.size()];
		
		System.out.println("Details Size : "+prelabDetails.size());
		PreLab[] prelabDisclaimers = new PreLab[prelabDetails.size()];
		int i =0;
		for(Map.Entry<Module, PreLab> modules : prelabDetails.entrySet()) {
			System.out.println("I : "+i);
			
			prelabDisclaimers[i++] = modules.getValue();
			System.out.println(prelabDisclaimers[i-1].getConcept().getName());
			System.out.println(prelabDisclaimers[i-1].getConcept().getId());
			System.out.println(prelabDisclaimers[i-1].getObjective());
			System.out.println(prelabDisclaimers[i-1].getHypothesis());
			System.out.println(prelabDisclaimers[i-1].getVariables());
			System.out.println(prelabDisclaimers[i-1].getExperimental());
			System.out.println(prelabDisclaimers[i-1].getChemical());
			
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
	
	//Map<Module,List<Question>> labQuestions = new HashMap<Module,List<Question>>();


	private Map<Module, List<Question>> getPostLabQuestions() {
		return getQuestionsDao().getPostLabQuestions();
	}
	
	public PostLab[] getPostLabArray() {
		Map<Module, List<Question>> postlabMQ = getPostLabQuestions();
		PostLab[] postlabArray = new PostLab[postlabMQ.size()];
		int i  = 0;
		for(Map.Entry<Module, List<Question>> map :  postlabMQ.entrySet()) {
			PostLab postlab = new PostLab();
			
			Concept concept = new Concept();
			
			Module module = map.getKey();
			concept.setId(module.getId());
			concept.setName(module.getName());
			
			Question[] questions = map.getValue().toArray(new Question[map.getValue().size()]);
			postlab.setConcept(concept);
			postlab.setQuestion(questions);
			postlabArray[i++] = postlab;
		}
		return postlabArray;
	}
	
	public LabExperiment[] getLabArray() {
		Map<Module, List<Question>> assessmentMQ = getLab();
		LabExperiment[] assessment = new LabExperiment[assessmentMQ.size()];
		int i = 0;
		for(Map.Entry<Module, List<Question>> map :  assessmentMQ.entrySet()) {
			LabExperiment assess = new LabExperiment();
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
}
