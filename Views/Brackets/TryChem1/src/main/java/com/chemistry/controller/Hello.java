package com.chemistry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chemistry.dao.AssignmentModuleDAO;
import com.chemistry.dao.InstructorQuestionsDAO;
import com.chemistry.model.Module;

@Controller
public class Hello {

	private InstructorQuestionsDAO mDao;
	
	@Autowired
	public void setmDao(InstructorQuestionsDAO mDao) {
		this.mDao = mDao;
	}

	@RequestMapping(value="/showMessage.html", method=RequestMethod.GET)
	public String handleRequest() {
		return "showMessage";
	}
	
	@RequestMapping(value="call", method=RequestMethod.GET)
	public String handleRequestQuestions() {
		return "diffquestions";
	}

	@RequestMapping(value="blankques", method=RequestMethod.GET)
	public String handleRequestBlankQuestions() {
		return "blankQues";
	}

	@RequestMapping(value="module", method=RequestMethod.GET)
	public String handleModules(@ModelAttribute Module module, ModelMap model) {
		
		model.addAttribute("message", insertModule(module));
		return "diffquestions";
	}
	
	private String insertModule(Module module) {
		
		if(mDao.insertModule(module) >0)
			return "success";
		else
			return "fail";
	}
	
}
