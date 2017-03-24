package com.chemistry.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.jstl.core.IteratedExpression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chemistry.lab.AssignmentStatus;
import com.chemistry.model.Experiment;
import com.chemistry.model.Module;
import com.chemistry.model.PreLab;
import com.chemistry.questions.Question;
import com.chemistry.util.QuestionMember;
import com.chemistry.util.QuestionType;
import com.chemistry.util.SectionType;

@Service
@Scope("session")
public class QuestionsDAO {

	private InstructorQuestionsDAO insQuestions = null;
	private StudentQuestionsDAO stuQuestions = null;
//	private AssignmentModuleDAO assignMod = null;

	private StudentStatusDAO studentStatus = null;
	
	private List<Experiment> experimentsList = null;
	private List<Module> experimentModules = null;
	private List<Question> allExperimentQuestions = null;
	
	private Map<Module,List<Question>> assessmentQuestions = null;
	private Map<Module,List<Question>> postLabQuestions = null;
	private Map<Module,List<Question>> labQuestions = null;
	private Map<Module,PreLab> prelabDisclaimers = new HashMap<Module, PreLab>();
	private PreLab[] prelabs = null;
	
/*	public QuestionsDAO(InstructorQuestionsDAO insQuestions, StudentQuestionsDAO stuQuestions, StudentStatusDAO assignMod) {
		this.insQuestions = insQuestions;
		this.stuQuestions = stuQuestions;
		this.studentStatus = assignMod;
		setExperimentsList();
	}*/
	
	@Autowired
	public void setInsQuestions(InstructorQuestionsDAO insQuestions) {
		this.insQuestions = insQuestions;
	}

	@Autowired
	public void setStuQuestions(StudentQuestionsDAO stuQuestions) {
		this.stuQuestions = stuQuestions;
	}
	
	@Autowired
	public void setAssignMod(StudentStatusDAO studentStatus) {
		this.studentStatus = studentStatus;
		setExperimentsList();
	}

	private void setExperimentsList() {
		this.experimentsList = studentStatus.getAllAssignmentDetails();
	}

	public List<Experiment> getExperimentsList() {
		return experimentsList;
	}
	
	public AssignmentStatus getStudentAssignmentStatus(String netId, int experimentId) {
		return studentStatus.getStudentAssignmentStatus(netId, experimentId);
	}
	
	public Map<Module, PreLab> getPrelabDisclaimers() {
		return prelabDisclaimers;
	}

	private void setPrelabDisclaimers(Map<Module, PreLab> prelabDisclaimers) {
		this.prelabDisclaimers = prelabDisclaimers;
		
	}

	public PreLab[] getPrelabs() {
		return prelabs;
	}

	public void setPrelabs(PreLab[] prelabs) {
		this.prelabs = prelabs;
	}


	public Map<Module, List<Question>> getAssessmentQuestions() {
		return assessmentQuestions;
	}

	public void setAssessmentQuestions(Map<Module, List<Question>> assessmentQuestions) {
		this.assessmentQuestions = assessmentQuestions;
	}

	public Map<Module, List<Question>> getPostLabQuestions() {
		return postLabQuestions;
	}

	public void setPostLabQuestions(Map<Module, List<Question>> postLabQuestions) {
		this.postLabQuestions = postLabQuestions;
	}

	public Map<Module, List<Question>> getLabQuestions() {
		return labQuestions;
	}

	public void setLabQuestions(Map<Module, List<Question>> labQuestions) {
		this.labQuestions = labQuestions;
	}

	public Experiment getParticularExperiment(int experimentId) {
		
		Iterator iterate = getExperimentsList().iterator();
		
		setExperimentModules(experimentId);
		setExperimentQuestions(experimentId);
		
		while(iterate.hasNext()) {
			Experiment experiment = (Experiment) iterate.next();
			if(experiment.getExperimentId() == experimentId) {
				return experiment;
			}
		}
		return null;
	}
	
	
	private void setExperimentModules(int experimentId) {
		 experimentModules = studentStatus.getModulesofExperiment(experimentId);
	}
	
	public List<Module> getExperimentModules() {
		return experimentModules;
	}

	private void setExperimentQuestions(int experimentId) {
		
		Iterator modulesList = getExperimentModules().iterator();

		Map<Module,List<Question>> assessmentQuestions = new HashMap<Module,List<Question>>();
		Map<Module,List<Question>> labQuestions = new HashMap<Module,List<Question>>();
		Map<Module,List<Question>> postLabQuestions = new HashMap<Module,List<Question>>();
			Map<Module,PreLab> prelabDisclaimers = new HashMap<Module,PreLab>();
		List<PreLab> prelabList = new ArrayList<PreLab>();
		
		while(modulesList.hasNext()) {
			List<Question> allQuestions = new ArrayList<Question>();
			Module module = (Module) modulesList.next();
			
			allQuestions.addAll(stuQuestions.getCheckBoxQuestions(experimentId, module.getId()));
			
			System.out.println("ALL CB Ques : "+allQuestions.size());
			allQuestions.addAll(stuQuestions.getBlankQuestions(experimentId, module.getId()));
			allQuestions.addAll(stuQuestions.getTextAreaDetails(experimentId, module.getId()));
			allQuestions.addAll(stuQuestions.getDataEntryDetails(experimentId, module.getId()));
		//	allQuestions.addAll(stuQuestions.getComparisionQuestionDetails(experimentId, module.getId()));
			
//			prelabDisclaimers.put(module, stuQuestions.getPreLabDetails(experimentId, module.getId()));
	//		prelabList.add(stuQuestions.getPreLabDetails(experimentId, module.getId()));
			
			assessmentQuestions.put(module, arrangeSectionQuestions(allQuestions, QuestionMember.section(SectionType.ASSESSMENT)));
			labQuestions.put(module, arrangeSectionQuestions(allQuestions, QuestionMember.section(SectionType.LAB)));
			postLabQuestions.put(module, arrangeSectionQuestions(allQuestions, QuestionMember.section(SectionType.POSTLAB)));
		}
		
		//setPrelabs(prelabList.toArray(new PreLab[prelabList.size()]));
		//System.out.println("Prelabs Size : "+getPrelabs().length);
		//setPrelabDisclaimers(prelabDisclaimers);
		
		setAssessmentQuestions(assessmentQuestions);
		setLabQuestions(labQuestions);
		System.out.println("LAB Questions : "+labQuestions.size());
		setPostLabQuestions(postLabQuestions);
	}

	private List<Question> sortSectionQuestions(List<Question> list) {
		Collections.sort(list, new Comparator<Question>() {

			public int compare(Question question1, Question question2) {
				return question1.getSection().substring(2,question1.getSection().length()).compareTo(question2.getSection().substring(2,question1.getSection().length()));
			}			
		});
		return list;
	}

	private List<Question> arrangeSectionQuestions(List<Question> allQuestions, String format) {
		if(allQuestions.size() > 0) {
			Iterator iterate = allQuestions.iterator();
			List<Question> sectionQuestions = new ArrayList<Question>();
			while(iterate.hasNext()) {
				Question question = (Question) iterate.next();			
				if(question.getSection().contains(format)) {
					sectionQuestions.add(question);
					iterate.remove();
	//				allQuestions.remove(question);
				}
			}		
			return sortSectionQuestions(sectionQuestions);
		}
		else 
			return new ArrayList<Question>();
	}
	

/*	public List<Question> getAllExperimentQuestions() {
		return allExperimentQuestions;
	}*/

	/*private void setAllExperimentQuestions(List<Question> allQuestions) {
		this.allExperimentQuestions = allQuestions;
	}*/
	
	
}
