package com.chemistry.questions;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.chemistry.dao.AssignmentModuleDAO;
import com.chemistry.dao.InstructorQuestionsDAO;
import com.chemistry.dao.QuestionsDAO;
import com.chemistry.dao.StudentQuestionsDAO;
import com.chemistry.model.Experiment;
import com.chemistry.model.Module;

public class QuestionsDAOTest {

	Experiment experiment = null;
	final int EXPERIMENT_ID = 1;

	AssignmentModuleDAO moduleDao = new AssignmentModuleDAO();
	private InstructorQuestionsDAO insQuestions = new InstructorQuestionsDAO();
	private StudentQuestionsDAO stuQuestions = new StudentQuestionsDAO();
	//QuestionsDAO questions = new QuestionsDAO(insQuestions, stuQuestions, moduleDao);
	//QuestionsDAO questions = new QuestionsDAO();
	@Before
	public void setUp() throws Exception {
//		experiment = questions.getParticularExperiment(EXPERIMENT_ID);
	}

	//@Test
	public void experimentList() {
	/*	List<Experiment> list  = questions.getExperimentsList();
		
		Iterator iterate = list.iterator();
		while(iterate.hasNext()) {
			Experiment ex = (Experiment) iterate.next();
			System.out.println(" "+ex.getTitle());
		}*/
	}

//	@Test
	public void testParticular() {
		System.out.println("NAME : "+experiment.getTitle());
	}
	
//	@Test
	public void testCheckBox() {
		List<CheckBoxQuestion> checkboxes = stuQuestions.getCheckBoxQuestions(1, 1);
		System.out.println("Checkbox SIZE : "+checkboxes.size());
		int i =1;
		Iterator iterate = checkboxes.iterator();
		while(iterate.hasNext()) {
			CheckBoxQuestion question = (CheckBoxQuestion)iterate.next();
			System.out.println(i+++" "+question.getQuestion());
		}
	}

//	@Test
	public void testBlank() {
		List<BlankQuestion> blank = stuQuestions.getBlankQuestions(1, 1);
		System.out.println("Checkbox SIZE : "+blank.size());
		int i =1;
		Iterator iterate = blank.iterator();
		while(iterate.hasNext()) {
			BlankQuestion question = (BlankQuestion)iterate.next();
			System.out.println(i+++" "+question.getQuestion());
		}
	}

	@Test
	public void test() {
		
/*		Map<Module,List<Question>> assessment = questions.getAssessmentQuestions();
		for(Map.Entry<Module, List<Question>> map : assessment.entrySet()) {
			System.out.println("Module : "+map.getKey().getName());
			Iterator iterate = map.getValue().iterator();
			int i = 1;
				while(iterate.hasNext()) {
				Question question = (Question) iterate.next();
				System.out.println(i+++"  QUES : "+question.getQuestion());
				System.out.println("  SEC : "+question.getSection());
			}
			
		}*/
	}

	@After
	public void tearDown() throws Exception {
	}


}
