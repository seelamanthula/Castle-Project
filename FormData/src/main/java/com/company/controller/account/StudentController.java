package com.company.controller.account;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.model.account.registration.AccountLogin;
import com.company.model.student.StudentExperimentModule;
import com.company.model.student.StudentPrelab;
import com.company.model.student.module.EachModule;
import com.google.gson.Gson;

@Controller
@Scope("session")
public class StudentController extends MainAccount {

	private String readFromBufferedReader(HttpServletRequest request) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			if(br != null) return br.readLine();
		} 
        catch (IOException e) {
			e.printStackTrace();
		} finally {
	        try { br.close(); } catch (IOException e) {
				e.printStackTrace();
			}
		}
        return null;
	}
	
	@RequestMapping(value="user-logout.edu")
	public ModelAndView userLogout(HttpServletRequest request) {
		System.out.println("User Logout");
		
		ModelAndView model = new ModelAndView("index");
		model.addObject("logout", "Logged out Successfully");
		return model;
	}

	
	@RequestMapping(value="register/validation")
	public @ResponseBody String loginValidation(HttpServletRequest request) {
		System.out.println("Student Login Validation");

		String s = "connection_lost";
		String json = readFromBufferedReader(request);
		
		if(json == null){
			json = "login-received-success";
	    } else {
	    	s = studentNavigation.parseStudentRegistrationInformation(json);
	        if(s == null) s = "already_registered";
	    }
		
		return s;
	}
	
	// Getting only class number considering class-no unique
	@RequestMapping(value="register/studentslot.json")
	public @ResponseBody String registerStudentSlot(HttpServletRequest request) {
		System.out.println("Register Student Slot");
		
		String json = readFromBufferedReader(request), s = null;
		
		if(json == null){
			json = "login-received-success";
	    } else {
	    	s = studentNavigation.storeStudentInformation(json);
	    }
	    return s;
	}
	
	@RequestMapping(value="move-student.com")
	public String moveStudent() {
		System.out.println("Student Main Page");
		
		AccountLogin login = studentNavigation.getAccountLogin();
		if(login == null) return "index";
		
		return "student";
	}

	
	@RequestMapping(value="json/student/stu-status.json")
	public @ResponseBody String studentStatusPage() {
		System.out.println("Status JSON");		
		
		String s = studentNavigation.getStudentStatus();
		return s;
	}
	
	@RequestMapping(value="experiment/{netid}/assessment/{expid}")
	public @ResponseBody String getStudentExperimentAssesmnet(@PathVariable String netid, @PathVariable String expid) {
		System.out.println("Single Experiment Assessment");
		
		String s = studentNavigation.getStudentExperimentAssessmentModulesScore(netid, expid);
		return s;
	}
	
	@RequestMapping(value="experiment/{netid}/prelab/{expid}")
	public @ResponseBody String getStudentExperimentPrelab(@PathVariable String netid, @PathVariable String expid) {
		System.out.println("Single Experiment Prelab");
		
		String s = studentNavigation.getStudentExperimentPrelabModulesScore(netid, expid);
		return s;
	}
	
	@RequestMapping(value={"assessment.edu"})
	public ModelAndView getStudentExperimentModuleAssesment(HttpServletRequest request) {
		System.out.println("Experiment Module Assessment");
		
		String expid = request.getParameter("eid");
		String moduleid = request.getParameter("mid");
		String netid = studentNavigation.getAccountLogin().getNetId();
		
		ModelAndView model = new ModelAndView("assessment");
		model.addObject("expid", expid);
		model.addObject("moduleid", moduleid);
		model.addObject("netid", netid);
		// NAVIGATE TO NEW-ASSESSMENT PAGE
		return model;
	}

	@RequestMapping(value={"prelab.edu"})
	public ModelAndView getStudentExperimentModulePrelab(HttpServletRequest request) {
		System.out.println("Experiment Module Prelab");
		
		String expid = request.getParameter("eid");
		String moduleid = request.getParameter("mid");
		String netid = studentNavigation.getAccountLogin().getNetId();
		
		ModelAndView model = new ModelAndView("prelab");
		model.addObject("expid", expid);
		model.addObject("moduleid", moduleid);
		model.addObject("netid", netid);
		// NAVIGATE TO NEW-ASSESSMENT PAGE
		return model;
	}

	@RequestMapping(value={"request/assessment/{expid}/moduledata/{moduleid}"})
	public @ResponseBody String getStudentAssessmentModuleQuestions(@PathVariable String expid, 
																	@PathVariable String moduleid) {

		System.out.println("Gathering Experiment Module");

		StudentExperimentModule student = new StudentExperimentModule();
		student.setExperimentId(expid);
		student.setModuleId(moduleid);
		
		String s = studentQuestionNavigation.gatherEachModuleAssessmentInformation(student);
		return s;
	}
	

	@RequestMapping(value={"request/prelab/{expid}/moduledata/{moduleid}"})
	public @ResponseBody String getStudentPrelabModuleAnswers(@PathVariable String expid, 
															  @PathVariable String moduleid) {

		System.out.println("Gathering Experiment Prelab Module Answers");

		StudentExperimentModule student = new StudentExperimentModule();
		student.setExperimentId(expid);
		student.setModuleId(moduleid);
		String netId = studentNavigation.getAccountLogin().getNetId();
		student.setNetId(netId);

		String s = studentQuestionNavigation.gatherStudentPrelabInformation(student);
		return s;
	}
	
	@RequestMapping(value={"request/experiment-file/{expid}"})
	public @ResponseBody String getStudentExperimentFile(@PathVariable String expid) {
		System.out.println("Gathering Experiment File");

		String s = studentQuestionNavigation.getExperimentFile(expid);
		return s;
	}
	
	@RequestMapping(value="request/question/{questionId}")
	public @ResponseBody String getStudentModuleQuestion(@PathVariable String questionId) {
		System.out.println("Gathering Quesiton");

		if(questionId.equals("done")) return "finished";
		String s = studentQuestionNavigation.getExperimentQuestion(questionId);
		return s;
	}

	@RequestMapping(value="update/module-answers")
	public @ResponseBody String getStudentModuleAnswers(HttpServletRequest request) {
		System.out.println("Student Module Answers");

		String json = readFromBufferedReader(request), s = "null";		
		if(json == null) {
			json = "success";
		} else {
			s = studentQuestionNavigation.parseUpdateModuleQuestion(json);
		}
		return s;
	}

	@RequestMapping(value="update/prelab-answers")
	public @ResponseBody String getStudentPrelabAnswers(HttpServletRequest request) {
		System.out.println("Student Prelab Answers");

		String json = readFromBufferedReader(request), s = "null";		
		if(json == null) {
			json = "success";
		} else {	          
			s = studentQuestionNavigation.parseUpdatePrelabAnswers(json);
		}
	    return s;
	}

	@RequestMapping(value="check/{netid}/assessment/{expid}/moduledata/{moduleid}")
	public @ResponseBody String checkStudentModuleAnswers(@PathVariable String netid, 
														  @PathVariable String expid, 
														  @PathVariable String moduleid) {

		System.out.println("Check Student Module Answers");
	
		StudentExperimentModule stm = new StudentExperimentModule();
		stm.setExperimentId(expid);
		stm.setModuleId(moduleid);
		stm.setNetId(netid);
		
		String s = studentQuestionNavigation.checkStudentExperimentModule(stm);
		return s;
	}
	
	@RequestMapping(value="check/{netid}/prelab/{expid}/moduledata/{moduleid}")
	public @ResponseBody String checkStudenPrelabtModuleAnswers(@PathVariable String netid, 
				@PathVariable String expid, @PathVariable String moduleid) {
		System.out.println("Check Student Prelab Module Answers");
		
		StudentExperimentModule stm = new StudentExperimentModule();
		stm.setExperimentId(expid);
		stm.setModuleId(moduleid);
		stm.setNetId(netid);
		String s = studentQuestionNavigation.checkGetStudentPrelabDataGC(stm);
		return s;
	}
			
}
