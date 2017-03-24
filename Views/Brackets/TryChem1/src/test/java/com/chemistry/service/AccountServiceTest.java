package com.chemistry.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.chemistry.dao.AssignmentModuleDAO;
import com.chemistry.dao.InstructorQuestionsDAO;
import com.chemistry.dao.QuestionsDAO;
import com.chemistry.dao.StudentQuestionsDAO;

public class AccountServiceTest {

/*	InstructorQuestionsDAO insQuestions = new InstructorQuestionsDAO();
	StudentQuestionsDAO stuQuestions = new StudentQuestionsDAO();
	AssignmentModuleDAO assignMod = new AssignmentModuleDAO();
	
	QuestionsDAO questions = new QuestionsDAO(insQuestions, stuQuestions,assignMod );*/
	
	StudentService student = new StudentService();
	
	@Before
	public void setUp() throws Exception {
//		student.setQuestionsDao(questions);
		student.getParticularExperiment(1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		System.out.println("ASSESS LENGTH : "+student.getArrayAssessment());
	}

}
