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
@Scope("session")
public abstract class AccountService {
	private QuestionsDAO questionsDao = null;

	@Autowired
	public void setQuestionsDao(QuestionsDAO questionsDao) {
		this.questionsDao = questionsDao;
	}
	
	protected QuestionsDAO getQuestionsDao(){
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
		
	public Experiment[] getExperimentArray() {		
		Experiment[] experiment = getExperimentsList().toArray(new Experiment[getExperimentsList().size()]);
		return experiment;
	}
	
	public AssignmentStatus getStudentAssignmentStatus(String netId, int experimentId) {
		return questionsDao.getStudentAssignmentStatus(netId, experimentId);
	}
}
