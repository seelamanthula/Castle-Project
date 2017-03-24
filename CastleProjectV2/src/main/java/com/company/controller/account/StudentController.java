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
		BufferedReader br;
		String json = "login-received-success", s = "connection_lost";
		
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        if(br != null){
	            json = br.readLine();
	        }
	   //     System.out.println(json);
	        s = studentNavigation.parseStudentRegistrationInformation(json);
	        
	        if(s == null) s = "already_registered";
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return s;
	}
	
	// Getting only class number considering class-no unique
	@RequestMapping(value="register/studentslot.json")
	public @ResponseBody String registerStudentSlot(HttpServletRequest request) {

		System.out.println("Register Student Slot");
		BufferedReader br;
		String json = "login-received-success", s = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        if(br != null){
	            json = br.readLine();
		        s = studentNavigation.storeStudentInformation(json);
	        }
	        // System.out.println("class no : "+json);
	        
	        System.out.println("S : "+s);
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*ModelAndView model = new ModelAndView("index");
		model.addObject("success", "Successfully Registered");*/
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
		
		System.out.println(s);
		
		return s;
	}
	
	@RequestMapping(value="experiment/{netid}/assessment/{expid}")
	public @ResponseBody String getStudentExperimentAssesmnet(@PathVariable String netid, @PathVariable String expid) {
		System.out.println("Single Experiment Assessment");
		System.out.println("Net id : "+ netid);
		System.out.println("exper id : "+expid);
		
		String s = studentNavigation.getStudentExperimentAssessmentModulesScore(netid, expid);
		
		System.out.println(s);
		return s;
	}
	
	@RequestMapping(value="experiment/{netid}/prelab/{expid}")
	public @ResponseBody String getStudentExperimentPrelab(@PathVariable String netid, @PathVariable String expid) {
		System.out.println("Single Experiment Prelab");
/*		System.out.println("Net id : "+ netid);
		System.out.println("exper id : "+expid);*/
		
		String s = studentNavigation.getStudentExperimentPrelabModulesScore(netid, expid);
		
		System.out.println(s);
		return s;
	}
	
	@RequestMapping(value={"assessment.edu"})
	public ModelAndView getStudentExperimentModuleAssesment(HttpServletRequest request) {
		System.out.println("Experiment Module Assessment");
		
		String expid = request.getParameter("eid");
		String moduleid = request.getParameter("mid");
		String netid = studentNavigation.getAccountLogin().getNetId();
		
	/*	System.out.println("ex Id : "+expid);
		System.out.println("mod id : "+moduleid);
		System.out.println("net id : "+netid);
	*/	
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

/*		System.out.println("exper id : "+expid);
		System.out.println("Module id : "+ moduleid);
		*/
		StudentExperimentModule student = new StudentExperimentModule();
		student.setExperimentId(expid);
		student.setModuleId(moduleid);
		
		/*String login = studentNavigation.getAccountLogin().getNetId();
		if(login == null) return "no-experiment";
		*/
		
	//	student.setNetId(studentNavigation.getAccountLogin().getNetId());
		
		String s = studentQuestionNavigation.gatherEachModuleAssessmentInformation(student);
	//	System.out.println(s.replace( "\\\"", "\""));
		return s;
	}
	

	@RequestMapping(value={"request/prelab/{expid}/moduledata/{moduleid}"})
	public @ResponseBody String getStudentPrelabModuleAnswers(@PathVariable String expid, 
			@PathVariable String moduleid) {

		System.out.println("Gathering Experiment Prelab Module Answers");

/*		System.out.println("exper id : "+expid);
		System.out.println("Module id : "+ moduleid);*/
		
		StudentExperimentModule student = new StudentExperimentModule();
		student.setExperimentId(expid);
		student.setModuleId(moduleid);
		String login = studentNavigation.getAccountLogin().getNetId();
	//	if(login == null) return "no-experiment";
		
		student.setNetId(login);
		String s = studentQuestionNavigation.gatherStudentPrelabInformation(student);
		
		System.out.println("Prelab Response");
		System.out.println(s);
		return s;
	}
	
	@RequestMapping(value={"request/experiment-file/{expid}"})
	public @ResponseBody String getStudentExperimentFile(@PathVariable String expid) {

		System.out.println("Gathering Experiment File");

		//System.out.println("exper id : "+expid);
		
		String s = studentQuestionNavigation.getExperimentFile(expid);
		//System.out.println(s.replace( "\\\"", "\""));
		return s;
	}
	
	@RequestMapping(value="request/question/{questionId}")
	public @ResponseBody String getStudentModuleQuestion(@PathVariable String questionId) {

		System.out.println("Gathering Quesiton");
		System.out.println("question id : "+questionId);
		if(questionId.equals("done")) return "finished";
		String s = studentQuestionNavigation.getExperimentQuestion(questionId);
		//System.out.println(s.replace( "\\\"", "\""));
		
		System.out.println(s);
		return s;
	}

	@RequestMapping(value="update/module-answers")
	public @ResponseBody String getStudentModuleAnswers(HttpServletRequest request) {

		System.out.println("Student Module Answers");
		BufferedReader br;
		String json = "success", s = "null";		
		
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        if(br != null){
	            json = br.readLine();
	          
	            System.out.println("Module Answer Info : "+json);
		         s = studentQuestionNavigation.parseUpdateModuleQuestion(json);
		            
	        }
	         System.out.println(s);
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}

	@RequestMapping(value="update/prelab-answers")
	public @ResponseBody String getStudentPrelabAnswers(HttpServletRequest request) {

		System.out.println("Student Prelab Answers");
		BufferedReader br;
		String json = "success", s = "null";		
		
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        if(br != null){
	            json = br.readLine();
	          
	            System.out.println("Module Prelab Info : "+json);
		         s = studentQuestionNavigation.parseUpdatePrelabAnswers(json);
		            
	        }
	         System.out.println(s);
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}

	@RequestMapping(value="check/{netid}/assessment/{expid}/moduledata/{moduleid}")
	public @ResponseBody String checkStudentModuleAnswers(@PathVariable String netid, 
				@PathVariable String expid, @PathVariable String moduleid) {

		System.out.println("Check Student Module Answers");
		System.out.println();
		
/*		System.out.println("net id : "+netid);
		System.out.println("net id : "+expid);
		System.out.println("net id : "+moduleid);		*/
		
		StudentExperimentModule stm = new StudentExperimentModule();
		stm.setExperimentId(expid);
		stm.setModuleId(moduleid);
		stm.setNetId(netid);
		
		String s = studentQuestionNavigation.checkStudentExperimentModule(stm);
		
		System.out.println(s);
		return s;
	}
	
	@RequestMapping(value="check/{netid}/prelab/{expid}/moduledata/{moduleid}")
	public @ResponseBody String checkStudenPrelabtModuleAnswers(@PathVariable String netid, 
				@PathVariable String expid, @PathVariable String moduleid) {

		System.out.println("Check Student Prelab Module Answers");
		System.out.println();
		
/*		System.out.println("net id : "+netid);
		System.out.println("exp id : "+expid);
		System.out.println("mod id : "+moduleid);		
		*/
		StudentExperimentModule stm = new StudentExperimentModule();
		stm.setExperimentId(expid);
		stm.setModuleId(moduleid);
		stm.setNetId(netid);
		
		String s = studentQuestionNavigation.checkGetStudentPrelabDataGC(stm);
//		String s = "no-saving";
		
	/*	String s1 = s.replace("\n", "\\n");*/
		System.out.println(s);
		return s;
	}
			
}
