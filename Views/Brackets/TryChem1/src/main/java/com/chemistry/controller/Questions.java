package com.chemistry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chemistry.dao.InstructorQuestionsDAO;
import com.chemistry.model.Assignment;
import com.chemistry.model.PreLab;
import com.chemistry.questions.BlankQuestion;
import com.chemistry.questions.CheckBoxQuestion;

@Controller
//@Scope("prototype")

public class Questions {

	private InstructorQuestionsDAO qDao;
	private final int ASSIGNMENTID = 1;
	private final int MODULEID = 1;
	private final int NOT_INSERTED = 0;
	private final String NO_CONTENT = "NO";
	
	@Autowired
	public void setqDao(InstructorQuestionsDAO qDao) {
		this.qDao = qDao;
	}
	
	@RequestMapping(value="checkbox", method=RequestMethod.GET)
	public String handleCheckBox() {
		return "checkbox";
	}
	
	@RequestMapping(value="cb_post.html", method=RequestMethod.POST)
	public String checkBoxQuestion(@ModelAttribute CheckBoxQuestion question, ModelMap model) {
		
		System.out.println("CB Questions: ");
		System.out.println("Question : "+question.getQuestion());
		System.out.println("Answer : "+question.getAnswer());
		System.out.println("Invalid : "+question.getInvalid(0));
		System.out.println("Invalid : "+question.getInvalid(1));
		System.out.println("Invalid : "+question.getInvalid(2));
		
		model.addAttribute("message", insertCBInDao(question));
		
		return "diffquestions";
	}

	@RequestMapping(value="blank.html", method=RequestMethod.POST)
	public String blankQuestion(@ModelAttribute BlankQuestion question, ModelMap model) {
		
		System.out.println("Blank Questions: ");
		System.out.println("Question : "+question.getQuestion());
		System.out.println("Answer : "+question.getAnswer());
		System.out.println("Hint 1 : "+question.getHints(0));
		System.out.println("Hint 2 : "+question.getHints(1));
		System.out.println("Hint 3 : "+question.getHints(2));
		System.out.println("Range : "+question.getRange());
		System.out.println("Formula : "+question.getFormula());
		model.addAttribute("message", insertBlankInDao(question));
		
		return "diffquestions";
	}

	private Assignment getExperimentAssignment() {
		Assignment assignment = new Assignment();
		assignment.setAssignmentId(ASSIGNMENTID);
		assignment.setModuleId(MODULEID);
		return assignment;
	}
	private String insertBlankInDao(BlankQuestion question) {
		Assignment assignment = getExperimentAssignment();
		int id = qDao.insertBlank(question);
		if(id > 0) {
			assignment.setBlankId(id);
			assignment.setCheckboxId(NOT_INSERTED);
			assignment.setTextId(NOT_INSERTED);
			assignment.setPrelabId(NOT_INSERTED);

			if(qDao.insertExperimentAssignment(assignment) >0)
				return "Success";
		}
		return "fail";		
	}
	
	private String insertCBInDao(CheckBoxQuestion question) {
		Assignment assignment = getExperimentAssignment();
		int id = qDao.insertCheckBox(question);
		if(id > 0) {
			assignment.setBlankId(NOT_INSERTED);
			assignment.setCheckboxId(id);
			assignment.setTextId(NOT_INSERTED);
			assignment.setPrelabId(NOT_INSERTED);

			if(qDao.insertExperimentAssignment(assignment) >0)
				return "Success";
		}
		
		return "fail";		
	}

	@RequestMapping(value="disclaimer", method=RequestMethod.GET)
	public String handlePrelab() {
		return "disclaimer";
	}
	
	@RequestMapping(value="prelabEntry.html", method=RequestMethod.GET)
	public String requestPrelab(@ModelAttribute PreLab prelab, ModelMap model) {
	
		model.addAttribute("message", insertPrelab(prelab));
		return "diffquestions";
	}
	
	private String insertPrelab(PreLab prelab) {
		System.out.println("Objective : "+prelab.getObjective()+" "+prelab.getObjective().isEmpty());
		System.out.println("Hypothesis : "+prelab.getHypothesis()+" "+prelab.getHypothesis().isEmpty());
		System.out.println("Variables : "+prelab.getVariables()+" "+prelab.getVariables().isEmpty());
		System.out.println("Experimental : "+prelab.getExperimental()+" "+prelab.getExperimental().isEmpty());
		System.out.println("Chemical : "+prelab.getChemical()+" "+prelab.getChemical().isEmpty());
		
		Assignment assignment = getExperimentAssignment();
		
		int id = qDao.insertPreLab(prelab);
		if(id > 0) {
			assignment.setBlankId(NOT_INSERTED);
			assignment.setCheckboxId(NOT_INSERTED);
			assignment.setTextId(NOT_INSERTED);
			assignment.setPrelabId(id);
			if(qDao.insertExperimentAssignment(assignment) >0)
				return "Success";		
		}
		
		return "fail";
	}
}
