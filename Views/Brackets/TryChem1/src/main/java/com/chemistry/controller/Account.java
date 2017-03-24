package com.chemistry.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chemistry.lab.Assessment;
import com.chemistry.model.Concept;
import com.chemistry.model.Experiment;
import com.chemistry.model.Module;
import com.chemistry.model.PreLab;
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
public class Account {

	private Experiment experiment = null;
	private StudentStatusService student = null;
	private JsonToClassConverter jsonToClassConverter = null;
	private String netId;
	private PdfGeneration generation = null;
	
	@Autowired
	public void setStudent(StudentStatusService student) {
		this.student = student;
	}

	@Autowired
	public void setJsonToClassConverter(JsonToClassConverter jsonToClassConverter) {
		this.jsonToClassConverter = jsonToClassConverter;
	}

	@Autowired
	public void setGeneration(PdfGeneration generation) {
		this.generation = generation;
	}

	public String getNetId() {
		return netId;
	}

	public void setNetId(String netId) {
		this.netId = netId;
	}

	public Experiment getExperiment() {
		return experiment;
	}

	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}

	@RequestMapping(value={"experiment/status.html","status.html"}, method=RequestMethod.GET)
	public String handleStatus() {		
		return "student/status";
	}
	
	@RequestMapping(value={"experiment/status.json","status.json"}, method=RequestMethod.GET)
	public @ResponseBody String handleStatusJSON() {
		String json = new Gson().toJson(student.getExperimentArray());
		System.out.println("STATUS : "+json);
		return json;
	}
	
	@RequestMapping(value="assessment", method=RequestMethod.GET)
	public String handleParticularExperiment() {
		return "student/assessment";
	}
	
	@RequestMapping(value={"assessment.json", "experiment/assessment.json"}, method=RequestMethod.GET)
	public @ResponseBody String handleAssessmentArray() {
		Assessment[] assessment = student.getAssessmentArray();
		String json = new Gson().toJson(assessment);
		System.out.println("ASSESSMENT JSON : "+json);
		return json;
	}
	
	@RequestMapping(value="experiment/prelab.json", method=RequestMethod.GET)
	public @ResponseBody String handlePrelabJson() {
		Concept[] concept = student.getPrelabModuleConcepts();
		String json = new Gson().toJson(concept);
		System.out.println("Prelab Concept : "+json);
		return json;
	}
	
	@RequestMapping(value="experiment/{id}", method=RequestMethod.GET)
	public String handleParticularExperiment(@PathVariable int id) {
		experiment = student.getParticularExperiment(id);
		System.out.println("Experiment ID : "+id+" -- "+experiment.getExperimentId());
		return "student/assessment";
	}

	@RequestMapping(value="experiment/makepdf.json", method=RequestMethod.GET)
	public @ResponseBody String makePrelabPDF(@RequestParam String prelab) {

		System.out.println("In PDF Making");
		System.out.println(prelab);
		JsonParser parser = new JsonParser();
		JsonElement tradeElement = parser.parse(prelab);
		JsonArray trade = tradeElement.getAsJsonArray();
		System.out.println(trade);
		System.out.println("Before Convertion");
		List<PreLab> prelabList = jsonToClassConverter.preLabConverter(trade);
		byte[] pdf = generation.preLabPDFGeneration(prelabList, "netId", experiment);
		
		System.out.println("PDF Bytes created");
		fileCreation(pdf);
		System.out.println("Came out of FileOutputStream");

		return "status.html";
	}
	
	private void fileCreation(byte[] pdf) {
		try {
			System.out.println("Generated PDF");
			FileOutputStream fos = new FileOutputStream("C:/Users/Harish/Desktop/Magnus/one.pdf");
			fos.write(pdf);
			fos.close();
			System.out.println("Done FOS");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Update the Prelab status in the database along with saving the PDF file.
	 */
	public String updatePrelabStatus() {
		student.updatePrelabInExperiment(experiment.getExperimentId());
		return "";
	}


	
	@RequestMapping(value="assessmentupdate.ins", method=RequestMethod.GET)
	public String updateAssessmentStatus() {
		student.updateAssessmentInExperiment(experiment.getExperimentId());
		return "";
	}
}
