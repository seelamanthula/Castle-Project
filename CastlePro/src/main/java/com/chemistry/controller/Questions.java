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
import com.chemistry.model.Module;
import com.chemistry.model.PreLab;
import com.chemistry.questions.BlankQuestion;
import com.chemistry.questions.CheckBoxQuestion;
import com.chemistry.questions.Comparision;
import com.chemistry.questions.DataEntry;
import com.chemistry.questions.TextArea;

@Controller
@Scope("session")
public class Questions {

	private InstructorQuestionsDAO qDao;
	private final int ASSIGNMENTID = 1;
	private final int MODULEID = 2;
	private final int NOT_INSERTED = 0;
	private final String NO_CONTENT = "NO";
	
	@Autowired
	public void setqDao(InstructorQuestionsDAO qDao) {
		this.qDao = qDao;
	}
	
	@RequestMapping(value="diff", method=RequestMethod.GET)
	public String handleDifferentQuestions(ModelMap map) {
		map.addAttribute("conn", qDao.testConnection());
		return "diffquestions";
	}
	
	@RequestMapping(value="module", method=RequestMethod.POST)
	public String handleModule(@ModelAttribute Module module, ModelMap map) {

		System.out.println("In Module");
		System.out.println(module.getName());
		map.addAttribute("message", "This is in module");
		System.out.println("File bytes : "+module.getFile().getBytes());
		qDao.insertModule(module);
		return "diffquestions";
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
		System.out.println("Hint : "+question.getHint());
		model.addAttribute("message", insertCBInDao(question));
		
		return "diffquestions";
	}

	@RequestMapping(value="blankques", method=RequestMethod.GET)
	public String handleBlank() {
		return "blankQues";
	}
	@RequestMapping(value="blank.html", method=RequestMethod.POST)
	public String blankQuestion(@ModelAttribute BlankQuestion question, ModelMap model) {
		
		System.out.println("Blank Questions: ");
		System.out.println("Question : "+question.getQuestion());
		System.out.println("Answer : "+question.getAnswer());
		System.out.println("Hint 1 : "+question.getHints(0));
		System.out.println("Hint 2 : "+question.getHints(1));
		System.out.println("Hint 3 : "+question.getHints(2));
		System.out.println("Range : "+question.getRanged());
		System.out.println("Units : "+question.getUnits());
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
			assignment.setDataEntryId(NOT_INSERTED);
			assignment.setComparisionId(NOT_INSERTED);
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
			assignment.setDataEntryId(NOT_INSERTED);
			assignment.setComparisionId(NOT_INSERTED);

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
			assignment.setDataEntryId(NOT_INSERTED);
			assignment.setComparisionId(NOT_INSERTED);

			if(qDao.insertExperimentAssignment(assignment) >0)
				return "Success";		
		}
		
		return "fail";
	}
	
	@RequestMapping(value="deentry.html", method=RequestMethod.GET)
	public String toDeEntry() {
		System.out.println("To Data Entry");
		return "dentry";
	}
	
	@RequestMapping(value="de_post.html", method=RequestMethod.GET)
	public String requestDataEntry(@ModelAttribute DataEntry dataEntry, ModelMap model) {
	
		System.out.println("Data Entry");
		System.out.println("Question : "+dataEntry.getQuestion());
		System.out.println("Cols : "+dataEntry.getCols());
		System.out.println("Rows : "+dataEntry.getRows());
		System.out.println("Units : "+dataEntry.getUnits());
		System.out.println("Column Names : "+dataEntry.getColumnNames());
		System.out.println("Diff : "+dataEntry.getDifference());
		System.out.println("Hint : "+dataEntry.getHint());
		model.addAttribute("message", insertDataEntryInDao(dataEntry));
		return "diffquestions";
	}
	
	private String insertDataEntryInDao(DataEntry question) {
		Assignment assignment = getExperimentAssignment();
		int id = qDao.insertDataEntry(question);
		if(id > 0) {
			assignment.setBlankId(NOT_INSERTED);
			assignment.setCheckboxId(NOT_INSERTED);
			assignment.setTextId(NOT_INSERTED);
			assignment.setPrelabId(NOT_INSERTED);
			assignment.setDataEntryId(id);
			assignment.setComparisionId(NOT_INSERTED);

			if(qDao.insertExperimentAssignment(assignment) >0)
				return "Success";
		}
		return "fail";		
	}

	@RequestMapping(value="text.html", method=RequestMethod.GET)
	public String handleText() {
		return "text";
	}
	
	@RequestMapping(value="text_post.html", method=RequestMethod.GET)
	public String handleTextAdd(@ModelAttribute TextArea text, ModelMap model) {

		System.out.println("QUestion : "+text.getQuestion());
		System.out.println("Type : "+text.getSection());
	
		model.addAttribute("message", insertData(text));
		return "diffquestions";
		
	}
	
	private String insertData(TextArea area) {
		Assignment assignment = getExperimentAssignment();
		int id = qDao.insertTextArea(area);
		if(id > 0) {
			assignment.setBlankId(NOT_INSERTED);
			assignment.setCheckboxId(NOT_INSERTED);
			assignment.setTextId(id);
			assignment.setPrelabId(NOT_INSERTED);
			assignment.setDataEntryId(NOT_INSERTED);
			assignment.setComparisionId(NOT_INSERTED);

			if(qDao.insertExperimentAssignment(assignment) >0)
				return "Success";
		}
		return "fail";		
	}
	
	@RequestMapping(value="compare.html", method=RequestMethod.GET)
	public String handleComparision() {
		return "compare";
	}

	@RequestMapping(value="compare_post.html", method=RequestMethod.GET)
	public String handleComparision(@ModelAttribute Comparision compare, ModelMap model) {

		System.out.println("QUestion : "+compare.getQuestion());
		System.out.println("Type : "+compare.getSection());
	
		model.addAttribute("message", insertComparision(compare));
		return "diffquestions";
	}

	private String insertComparision(Comparision compare) {
		Assignment assignment = getExperimentAssignment();
		int id = qDao.insertComparison(compare);
		if(id > 0) {
			assignment.setBlankId(NOT_INSERTED);
			assignment.setCheckboxId(NOT_INSERTED);
			assignment.setTextId(NOT_INSERTED);
			assignment.setPrelabId(NOT_INSERTED);
			assignment.setDataEntryId(NOT_INSERTED);
			assignment.setComparisionId(id);

			if(qDao.insertExperimentAssignment(assignment) >0)
				return "Success";
		}
		return "fail";		
	}

}
