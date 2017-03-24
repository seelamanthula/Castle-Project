package com.chemistry.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chemistry.lab.Assessment;
import com.chemistry.lab.AssignmentStatus;
import com.chemistry.lab.LabExperiment;
import com.chemistry.lab.PreLabDisclaimers;
import com.chemistry.model.Concept;
import com.chemistry.model.Experiment;
import com.chemistry.model.Module;
import com.chemistry.model.PreLab;
import com.chemistry.model.User;
import com.chemistry.service.JsonToClassConverter;
import com.chemistry.service.StudentService;
import com.chemistry.service.StudentStatusService;
import com.chemistry.util.PdfGeneration;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
@Scope("session")
public class Account extends Arrange {
	
	private int NETID_LENGTH = 5;
	
/*	@RequestMapping(value="status.html", method=RequestMethod.GET)
	public String handleStatus() {
		return "duplicate/status";
	}

	@RequestMapping(value="status.json", method=RequestMethod.GET)
	public @ResponseBody String handleStatusJSON() {
		AssignmentStatus status = getStudent().getStudentAssignmentStatus(this.getUser().getNetId(), 1);
		String json = new Gson().toJson(status);
		System.out.println("STATUS JSON : "+json);
		return json;
	}
*/	
	@RequestMapping(value="assessment", method=RequestMethod.GET)
	public String handleParticularExperiment() {
		return "student/assessment";
	}
	
	@RequestMapping(value="assessment.json", method=RequestMethod.GET)
	public @ResponseBody String handleAssessmentArray() {
		Assessment[] assessment = getStudent().getAssessmentArray();
		String json = new Gson().toJson(assessment);
		return json;
	}
	
	@RequestMapping(value="prelab.json", method=RequestMethod.GET)
	public @ResponseBody String handlePrelabJson() {
		Concept[] disclaimers = getStudent().getPrelabModuleConcepts();
		String json2 = new Gson().toJson(disclaimers);
		return json2;
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public String handleParticularExperiment(@PathVariable int id) {
		setExperiment(getStudent().getParticularExperiment(id));
		return "student/assessment";
	}
	
	
	@RequestMapping(value="middle/{name}", method=RequestMethod.GET)
	public String handleMiddleAssessment(@PathVariable String name) {
		return "student/assessment";
	}
	
	@RequestMapping(value="prelab.html", method=RequestMethod.GET)
	public String handlePrelab(){
		getStudent().updateAssessmentInExperiment(getUser().getNetId(), getExperiment().getExperimentId(), "assessment");
		return "student/prelab";
	}

	@RequestMapping(value={"middle.json","experiment/middle.json"}, method=RequestMethod.GET)
	public @ResponseBody String handleMiddlePage() {
		String json = new Gson().toJson(getStudent().getPrelabModuleConcepts());
		return json;
	}
	
	@RequestMapping(value="one.html", method=RequestMethod.GET)
	public String handleonePrelabPDF() {
	//	updatePrelabStatus();
		return "student/one";
	}

	@RequestMapping(value="makepdf.json", method=RequestMethod.GET)
	public @ResponseBody String makePrelabPDF(@RequestParam String prelab, HttpServletResponse response) {
		JsonParser parser = new JsonParser();
		JsonElement tradeElement = parser.parse(prelab);
		JsonArray trade = tradeElement.getAsJsonArray();
		List<PreLab> prelabList = getJsonToClassConverter().preLabConverter(trade);
		
		byte[] pdf = getGeneration().preLabPDFGeneration1(prelabList, getUser().getNetId(), getExperiment());
		
		setPrelabPdfs(pdf);
		fileCreation(pdf);

		return "one";
	}
	

	private void fileCreation(byte[] pdf) {
		try {
			System.out.println("Generated PDF");
			FileOutputStream fos = new FileOutputStream("C:/Users/Harish/Desktop/Magnus/one.pdf");
			File file = new File("C:/Users/Harish/Desktop/Magnus/one.pdf");
			FileInputStream fis = new FileInputStream(file);
	//		getStudent().updatePDFofPrelab(file, fis, getUser().getNetId(),getExperiment().getExperimentId());

			fos.write(pdf);
			fos.close();
			System.out.println("Done FOS");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	



	/*
	 * Update the Prelab status in the database along with saving the PDF file.
	 */
	public void updatePrelabStatus(String column) {
		getStudent().updatePrelabInExperiment(getUser().getNetId(),getExperiment().getExperimentId(), column);
	}

	public void updateAssessmentStatus(String column) {
		getStudent().updateAssessmentInExperiment(getUser().getNetId(), getExperiment().getExperimentId(), column);
	}
	
	public void updateExperimentLabStatus() {
	//	getStudent().updateLabInExperiment(getUser().getNetId(),getExperiment().getExperimentId());
	}

	@RequestMapping(value="register.html")
	public String handleRegister() {
		return "student/register";
	}
	@RequestMapping(value="login.html")
	public String handleLogin() {
		return "student/login";
	}
	@RequestMapping(value="register_post.html", method=RequestMethod.POST)
	public String handleRegistration( HttpServletRequest request, ModelMap map) {
		User user = new User();
		user.setNetId(request.getParameter("netid"));
		user.setFirstName(request.getParameter("firstname"));
		user.setLastName(request.getParameter("lastname"));

		if(user.getNetId().length() < NETID_LENGTH) {
			map.addAttribute("status", "Check your NetID or FirstName");
			return "student/register";
		}
		else if(getAuthentication().authenticateUser(user)) 
			map.addAttribute("status", "Account Already Exists.");
		else if(getAuthentication().insertNewUserDetails(user) > 0)
			map.addAttribute("status", "Account Created Successfully..");
		else {
			getAuthentication().deleteUser(user);
			map.addAttribute("status", "Account Not created. Try Again!");
		}
		return "student/login";
	}
	
}
