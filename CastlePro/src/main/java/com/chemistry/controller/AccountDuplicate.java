package com.chemistry.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chemistry.lab.Assessment;
import com.chemistry.lab.AssignmentStatus;
import com.chemistry.model.Concept;
import com.chemistry.model.PreLab;
import com.chemistry.model.User;
import com.chemistry.util.PDFPostLabGenerate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Controller
@Scope("session")
public class AccountDuplicate extends Arrange {

	private int NETID_LENGTH = 5;
	private HttpSession session = null;
	
	@RequestMapping(value="status.html", method=RequestMethod.GET)
	public String handleStatus(HttpServletRequest request, ModelMap map) {
		
		System.out.println("Came to Status.html");
		session =request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			System.out.println("User null");
			map.addAttribute("status", "Login Required");
			session.setAttribute("login", "false");
			return "student/login";
		}
		System.out.println("User not null");
		System.out.println("Capital ONe : "+user.getCapitalFirstName());
		map.addAttribute("username", user.getCapitalFirstName());
		return "duplicate/status";
	}
	
	@RequestMapping(value="logout.html", method=RequestMethod.GET)
	public String handleLogout(HttpServletRequest request, ModelMap map) {
		
		session = request.getSession();
		session.setAttribute("login", "false");
		session.invalidate();
		map.addAttribute("status", "Successfully logged out");
		return "student/login";
	}


	@RequestMapping(value="status.json", method=RequestMethod.GET)
	public @ResponseBody String handleStatusJSON() {
		System.out.println("Status JSON");
		System.out.println("CO : "+this.getUser().getCapitalFirstName());
		System.out.println("ID : "+this.getUser().getNetId());
		AssignmentStatus status = getStudent().getStudentAssignmentStatus(this.getUser().getNetId(), 1);
		System.out.println("status : "+status);
		String json = new Gson().toJson(status);
		System.out.println("STATUS JSON : "+json);
		System.out.println("CO : "+this.getUser().getCapitalFirstName());

		return json;
	}

	private void updatePrelabStatus22(String column) {
		getStudent().updatePrelabInExperiment(getUser().getNetId(),getExperiment().getExperimentId(), column);
	}

	private void updateAssessmentStatus2(String column) {
		getStudent().updateAssessmentInExperiment(getUser().getNetId(), getExperiment().getExperimentId(), column);
	}
	
	@RequestMapping(value="authenticate.html", method=RequestMethod.POST)
	public String userAuthentication(HttpServletRequest request, ModelMap map) {
		
		User user = new User();
		user.setNetId(request.getParameter("netid"));
		user.setFirstName(request.getParameter("firstname"));
		
		if(user.getNetId().length() < NETID_LENGTH) {
			map.addAttribute("status", "Check your NetID or FirstName");
			return "student/login";
		}
		else if(getAuthentication().authenticateUser(user)) {

			map.addAttribute("username", user.getCapitalFirstName());
			this.setUser(user);
			setExperiment(getStudent().getParticularExperiment(1));

		
			session = request.getSession();
			session.setAttribute("login", "true");
			session.setAttribute("user", getUser());
			return "duplicate/status";
		}
		else {
			map.addAttribute("status", "Login Failed");
			return "student/login";
		}
	}
	@RequestMapping(value="assessmentvolumetric.html", method=RequestMethod.GET)
	public String handleAssessmentVolumetric(HttpServletRequest request, ModelMap map) {

		session =request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			map.addAttribute("status", "Login Required");
			session.setAttribute("login", "false");
			return "student/login";
		}

		System.out.println("assessment 1");
		return "duplicate/assessment1";
	}

	@RequestMapping(value="assessmentvolumetricupdate", method=RequestMethod.GET)
	public @ResponseBody String handleAssessmentVolumetricUpdate() {
		System.out.println("update Volumetric 1");
		updateAssessmentStatus2("assessment");
		AssignmentStatus status = getStudent().getStudentAssignmentStatus(this.getUser().getNetId(), 1);
		String json;
		
		if(status.getAssessmentAcid() == 0) {
			json = new Gson().toJson("assessmentacidbase.html");				
		} else if(status.getPrelabVolumetric() == 0){
			json = new Gson().toJson("prelabvolumetric.html");				
		} else if(status.getPrelabAcid() == 0){
			json = new Gson().toJson("prelabacidbase.html");				
		} else 
			json = new Gson().toJson("status.html");	
		
		System.out.println("Vol Update : "+json);
		return json;
	}

	@RequestMapping(value="assessmentacidbase.html", method=RequestMethod.GET)
	public String handleAssessmentAcidBase(HttpServletRequest request, ModelMap map) {
		System.out.println("assessment 2");
		session =request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			map.addAttribute("status", "Login Required");
			session.setAttribute("login", "false");
			return "student/login";
		}

		return "duplicate/assessment2";
	}

	@RequestMapping(value="assessmentacidbaseupdate", method=RequestMethod.GET)
	public @ResponseBody String handleAssessmentAcidBaseUpdate() {
		System.out.println("update Acid base Assess");
		updateAssessmentStatus2("assessmentacid");
		AssignmentStatus status = getStudent().getStudentAssignmentStatus(this.getUser().getNetId(), 1);
		String json = "";
		if(status.getAssessmentVolumetric() == 0) {
			json = new Gson().toJson("assessmentvolumetric.html");
		} if(status.getPrelabVolumetric() == 0){
			System.out.println("Prelab Volumetric");
			json = new Gson().toJson("prelabvolumetric.html");
		} else if(status.getPrelabAcid() == 0){
			json = new Gson().toJson("prelabacidbase.html");
		} else 
			json = new Gson().toJson("status.html");
		return json;
	}

	@RequestMapping(value="prelabvolumetric.html", method=RequestMethod.GET)
	public String handlePrelabVolumetric(HttpServletRequest request, ModelMap map) {
		System.out.println("Prelab 1");
		
		session =request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			map.addAttribute("status", "Login Required");
			session.setAttribute("login", "false");
			return "student/login";
		}

		return "duplicate/prelab1";
	}

	@RequestMapping(value="prelabvolumetricupdate", method=RequestMethod.GET)
	public @ResponseBody String handlePrelabVolumetricUpdate(@RequestParam String prelab) {
		System.out.println("update Prelab Volume");
		System.out.println("Prelab : "+prelab);
		updatePrelabStatus22("prelab");
		
		JsonParser parser = new JsonParser();
		JsonElement tradeElement = parser.parse(prelab);
		JsonArray trade = tradeElement.getAsJsonArray();
		List<PreLab> prelabList = getJsonToClassConverter().preLabConverter(trade);
		
		byte[] pdf = getGeneration().preLabPDFGeneration1(prelabList, getUser().getNetId(), getExperiment());
		
		setPrelabPdfs1(pdf);
		getStudent().updatePDFofPrelabVolumetric(pdf, getUser().getNetId(), getExperiment().getExperimentId());
	
		AssignmentStatus status = getStudent().getStudentAssignmentStatus(this.getUser().getNetId(), 1);
		if(status.getPrelabAcid() == 0) {
			return "prelabacidbase.html";
		} else if(status.getAssessmentVolumetric() == 0) {
			return "assessmentvolumetric.html";
		} else if(status.getAssessmentAcid() == 0) {
			return "assessmentacidbase.html";
		} else {		
			return "status.html";
		}
	}

	@RequestMapping(value="prelabstorage.html", method=RequestMethod.POST)
	public String handlePrelabVolumetricstorage(HttpServletRequest request) {
		System.out.println("update Prelab Volume");


		String objective = request.getParameter("objective");
		String hypothesis = request.getParameter("hypothesis");
		String variables = request.getParameter("variables");
		String experimental = request.getParameter("experimentalOutline");
		String chemical = request.getParameter("chemical-hazards");

		List<PreLab> prelabList = new ArrayList<PreLab>();
		PreLab pre = new PreLab();
		pre.setObjective(objective);
		pre.setHypothesis(hypothesis);
		pre.setExperimental(experimental);
		pre.setChemical(chemical);
		pre.setVariables(variables);
		
		prelabList.add(pre);
		byte[] pdf = getGeneration().preLabPDFGeneration1(prelabList, getUser().getNetId(), getExperiment());
		
		setPrelabPdfs1(pdf);
		getStudent().updatePDFofPrelabVolumetric(pdf, getUser().getNetId(), getExperiment().getExperimentId());
	
		AssignmentStatus status = getStudent().getStudentAssignmentStatus(this.getUser().getNetId(), 1);
		if(status.getPrelabAcid() == 0) {
			return "duplicate/prelab2";
		} else if(status.getAssessmentVolumetric() == 0) {
			return "duplicate/assessment1";
		} else if(status.getAssessmentAcid() == 0) {
			return "duplicate/assessment2";
		} else {		
			return "duplicate/status";
		}
	}

	@RequestMapping(value="prelabacidbase.html", method=RequestMethod.GET)
	public String handlePrelabAcidBase(HttpServletRequest request, ModelMap map) {
		System.out.println("Prelab 2");
		
		session =request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			map.addAttribute("status", "Login Required");
			session.setAttribute("login", "false");
			return "student/login";
		}

		return "duplicate/prelab2";
	}

	@RequestMapping(value="prelabacidbaseupdate", method=RequestMethod.GET)
	public @ResponseBody String handlePrelabAcidBaseUpdate(@RequestParam String prelab) {
		System.out.println("update Acid Base Volume");
		updatePrelabStatus22("prelabacid");
		
		
		JsonParser parser = new JsonParser();
		JsonElement tradeElement = parser.parse(prelab);
		JsonArray trade = tradeElement.getAsJsonArray();
		List<PreLab> prelabList = getJsonToClassConverter().preLabConverter(trade);
		
		byte[] pdf = getGeneration().preLabPDFGeneration2(prelabList, getUser().getNetId(), getExperiment());
		
		setPrelabPdfs(pdf);
		getStudent().updatePDFofPrelabAcid(pdf, getUser().getNetId(), getExperiment().getExperimentId());
			
		AssignmentStatus status = getStudent().getStudentAssignmentStatus(this.getUser().getNetId(), 1);
		if(status.getPrelabVolumetric() == 0) {
			return "prelabvolumetric.html";
		} else if(status.getAssessmentVolumetric() == 0) {
			return "assessmentvolumetric.html";
		} else if(status.getAssessmentAcid() == 0) {
			return "assessmentacidbase.html";
		} else {		
			return "status.html";
		}
	}

	@RequestMapping(value="prelabacidstorage.html", method=RequestMethod.POST)
	public String handlePrelabAcidstorage(HttpServletRequest request) {
		System.out.println("update Prelab Acid");

		String objective = request.getParameter("objective");
		String hypothesis = request.getParameter("hypothesis");
		String variables = request.getParameter("variables");
		String experimental = request.getParameter("experimentalOutline");
		String chemical = request.getParameter("chemical-hazards");

		List<PreLab> prelabList = new ArrayList<PreLab>();
		PreLab pre = new PreLab();
		pre.setObjective(objective);
		pre.setHypothesis(hypothesis);
		pre.setExperimental(experimental);
		pre.setChemical(chemical);
		pre.setVariables(variables);
		
		prelabList.add(pre);
		byte[] pdf = getGeneration().preLabPDFGeneration2(prelabList, getUser().getNetId(), getExperiment());
		
		setPrelabPdfs1(pdf);
		getStudent().updatePDFofPrelabAcid(pdf, getUser().getNetId(), getExperiment().getExperimentId());
	
		AssignmentStatus status = getStudent().getStudentAssignmentStatus(this.getUser().getNetId(), 1);
		if(status.getPrelabVolumetric() == 0) {
			return "duplicate/prelab1";
		} else if(status.getAssessmentVolumetric() == 0) {
			return "duplicate/assessment1";
		} else if(status.getAssessmentAcid() == 0) {
			return "duplicate/assessment2";
		} else {		
			return "duplicate/status";
		}
	}
	private Assessment[] getAssessmentArray() {
		return getStudent().getAssessmentArray();
	}
	
	@RequestMapping(value="assessmentvolumetric.json", method=RequestMethod.GET)
	public @ResponseBody String handleAssessmentVolumetricJSON() {
		System.out.println("assessment JSON 1");
		Assessment[] assessment = getAssessmentArray();
		String json;
		if(assessment[0].getConcept().getName().contains("metric")) {
			json = new Gson().toJson(assessment[0]);				
		} else {
			json = new Gson().toJson(assessment[1]);
		}
		
		System.out.println(json);
		return json;
	}
	
	@RequestMapping(value="assessmentacidbase.json", method=RequestMethod.GET)
	public @ResponseBody String handleAssessmentAcidBaseJSON() {
		System.out.println("assessment JSON 2");
		Assessment[] assessment = getAssessmentArray();
		String json;
		if(assessment[0].getConcept().getName().contains("metric")) {
			json = new Gson().toJson(assessment[1]);				
		} else {
			json = new Gson().toJson(assessment[0]);
		}
		
		return json;
	}

	private Concept[] getPrelabModuleConcepts() {
		return getStudent().getPrelabModuleConcepts();		
	}

	@RequestMapping(value="prelabvolumetric.json", method=RequestMethod.GET)
	public @ResponseBody String handlePrelabVolumetricJSON() {
		System.out.println("Prelab JSON 1");
		Concept[] concept = getPrelabModuleConcepts();
		String json;
		if(concept[0].getName().contains("metric")) {
			json = new Gson().toJson(concept[0]);
		} else {
			json = new Gson().toJson(concept[1]);
		}
		return json;
	}

	@RequestMapping(value="prelabacidbase.json", method=RequestMethod.GET)
	public @ResponseBody String handlePrelabAcidBaseJSON() {
		System.out.println("Prelab JSON 2");
		Concept[] concept = getPrelabModuleConcepts();
		String json;
		if(concept[0].getName().contains("metric")) {
			json = new Gson().toJson(concept[1]);
		} else {
			json = new Gson().toJson(concept[0]);
		}
		return json;
	}

	@RequestMapping(value={"experiment/prelab/{module}/document.pdf"})
	public String prelabBrowser1(HttpServletRequest request, HttpServletResponse response, 
			ModelMap map, @PathVariable String module) {
		
		System.out.println("PRELAB Doc start : "+module);
		byte[] pdf = null;
		
		session =request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			map.addAttribute("status", "Login Required");
			session.setAttribute("login", "false");
			return "student/login";
		}

		if(module.equalsIgnoreCase("volumetric")) {
			pdf = getStudent().getPrelabVolumetric(getUser().getNetId(), getExperiment().getExperimentId());
		} else {
			pdf = getStudent().getPrelabAcid(getUser().getNetId(), getExperiment().getExperimentId());			
		}
		try {
			response.setContentType("application/pdf");
			response.setContentLength(pdf.length);
			response.getOutputStream().write(pdf);
			System.out.println("Prelab Doc done");
			} catch (IOException e) {
				e.printStackTrace();
		}

		return null;
	}
	
	@RequestMapping(value="VolumetricLab", method=RequestMethod.POST)
	public String makeVolumetricLabPDF(HttpServletRequest request) {

		System.out.println("In Lab PDF");
		
		String lab1 = request.getParameter("Q13");
		System.out.println("STR 1 " +lab1);
		
		String lab2 = request.getParameter("Q14");
		System.out.println("STR 2 " +lab2);
		
		System.out.println("Student : ");
		System.out.println(getStudent());

		byte[] vlab = getGeneration().volumetricLabPDFGeneration1(lab1, lab2, getUser().getNetId(), getExperiment());
		getStudent().updateVolumetricLabPDFInDB(vlab, getUser().getNetId(), getExperiment().getExperimentId());
		getStudent().updateVolumetricLabInExperiment(getUser().getNetId(), getExperiment().getExperimentId());
		
		AssignmentStatus status = getStudent().getStudentAssignmentStatus(this.getUser().getNetId(), 1);
		System.out.println("Before Lab Convertion");
		
 		return "duplicate/status";
	}
	
	@RequestMapping(value="labacidbaseupdate", method=RequestMethod.GET)
	public @ResponseBody String handleLabAcidBaseUpdate(@RequestParam String data) {
		System.out.println("update Acid Base LAB");
	//	updatePrelabStatus2("prelabacid");
		System.out.println("DATA : "+data);
		byte[] acidlab = getGeneration().acidBaseLabPDFGeneration1(data, getUser().getNetId(), getExperiment());
		System.out.println("Got the PDF : "+acidlab.length);
		getStudent().updateAcidBaseLabPDFInDB(acidlab, getUser().getNetId(), getExperiment().getExperimentId());
		System.out.println("PDF stored");
		getStudent().updateAcidBaseLabInExperiment(getUser().getNetId(), getExperiment().getExperimentId());
		System.out.println("Update AB lab");
		AssignmentStatus status = getStudent().getStudentAssignmentStatus(this.getUser().getNetId(), 1);
		System.out.println("Status");
		
		return "status.html";
	}
	
	@RequestMapping(value={"experiment/lab/{module}/document.pdf"})
	public String LabBrowser1(HttpServletRequest request, HttpServletResponse response, 
			ModelMap map, @PathVariable String module) {
		
		System.out.println("LAB Doc start : "+module);
		byte[] pdf = null;
		
		session =request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			map.addAttribute("status", "Login Required");
			session.setAttribute("login", "false");
			return "student/login";
		}

		if(module.equalsIgnoreCase("volumetric")) {
			pdf = getStudent().getLabVolumetricPDF(getUser().getNetId(), getExperiment().getExperimentId());
		} else {
			pdf = getStudent().getLabAcidBasePDF(getUser().getNetId(), getExperiment().getExperimentId());			
		}
		try {
			response.setContentType("application/pdf");
			response.setContentLength(pdf.length);
			response.getOutputStream().write(pdf);
			System.out.println("Prelab Doc done");
			} catch (IOException e) {
				e.printStackTrace();
		}

		return null;
	}
	
	@RequestMapping(value="Postlab", method=RequestMethod.POST)
	public String getPostLabData(HttpServletRequest request) {
		
		System.out.println("Came to submit PostLab ");
		
		String t1, t2, t3, t4, t5;
		t1 = request.getParameter("T0");
		t2 = request.getParameter("T1");
		t3 = request.getParameter("T2");
		t4 = request.getParameter("T3");
		t5 = request.getParameter("T4");
		
		System.out.println("T1 : "+t1);
		System.out.println("T2 : "+t2);
		System.out.println("T3 : "+t3);
		System.out.println("T4 : "+t4);
		System.out.println("T5 : "+t5);

		String[] questions = new String[5];
		questions[0] = t1;
		questions[1] = t2;
		questions[2] = t3;
		questions[3] = t4;
		questions[4] = t5;
		
		System.out.println("Before PDF Made");
		byte[] pdf  = getPostlabGenerate().PostlabPDFGeneration(questions, getUser().getNetId());
		System.out.println("PDF Made");
		getStudent().updatePostLabPDFInDB(pdf, getUser().getNetId(), getExperiment().getExperimentId());
		System.out.println("Stored pdf in DB");
		getStudent().updatePostLabInDB(getUser().getNetId(), getExperiment().getExperimentId());
		System.out.println("Updated Status in DB");
		return "duplicate/status";
	}
	
	@RequestMapping(value={"postlab/document.pdf"})
	public String getPostLabDocument(HttpServletRequest request, HttpServletResponse response, 
			ModelMap map) {
		
		System.out.println("PostLAB Doc start : ");
		byte[] pdf = null;
		
		session =request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			map.addAttribute("status", "Login Required");
			session.setAttribute("login", "false");
			return "student/login";
		}

		pdf = getStudent().getPostLabPDF(getUser().getNetId(), getExperiment().getExperimentId());
		System.out.println("PDF len : "+pdf.length);
		try {
			response.setContentType("application/pdf");
			response.setContentLength(pdf.length);
			response.getOutputStream().write(pdf);
			System.out.println("Postlab Doc done");
			} catch (IOException e) {
				e.printStackTrace();
		}

		return null;
	}
	

}
