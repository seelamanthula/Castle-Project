package com.chemistry.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chemistry.dao.QuestionsDAO;
import com.chemistry.lab.Assessment;
import com.chemistry.lab.AssignmentStatus;
import com.chemistry.model.Experiment;
import com.chemistry.model.Module;
import com.chemistry.questions.Question;

@Service
//@Scope("prototype")

public abstract class AccountService {
	private QuestionsDAO questionsDao = null;

	@Autowired
	public void setQuestionsDao(QuestionsDAO questionsDao) {
		this.questionsDao = questionsDao;
	}
	
	protected QuestionsDAO getQuestionsDao(){
	//	System.out.println("Questions DAO : "+questionsDao);
		return questionsDao;
	}
	
	protected List<Experiment> getExperimentsList() {
		return questionsDao.getExperimentsList();
	}
	
	public Experiment getParticularExperiment(int experimentId) {
		return getQuestionsDao().getParticularExperiment(experimentId);
	}

	public List<Module> getExperimentModules() {
		return getQuestionsDao().getExperimentModules();
	}
	
	public List<Experiment> getexperimentList() {
		return questionsDao.getExperimentsList();
	}
	
	public Experiment[] getExperimentArray() {		
		Experiment[] experiment = getexperimentList().toArray(new Experiment[getexperimentList().size()]);
		return experiment;
	}
	
//	abstract Map<String, Question[]> getArrayAssessment();
//	abstract Assessment[] getAssessmentArray();
}
