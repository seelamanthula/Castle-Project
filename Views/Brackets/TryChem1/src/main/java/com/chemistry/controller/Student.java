package com.chemistry.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chemistry.lab.Assessment;
import com.chemistry.lab.PreLabDisclaimers;
import com.chemistry.model.Experiment;
import com.chemistry.model.Module;
import com.chemistry.questions.Question;
import com.chemistry.service.StudentService;
import com.chemistry.service.StudentStatusService;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class Student {

	private StudentStatusService student = null;
	
	@Autowired
	public void setStudent(StudentStatusService student) {
		this.student = student;
	}

	@RequestMapping(value="experiment/prelab.html", method=RequestMethod.GET)
	public String handlePrelab(){
		return "student/prelab";
	}
	
	@RequestMapping(value="experiment/prelabDisclaimers.json", method=RequestMethod.GET)
	public @ResponseBody String handlePrelabArray() {
		
		PreLabDisclaimers[] disclaimers = student.getPrelabDisclaimerArray();

		String json = new Gson().toJson(disclaimers);
		System.out.println("PRELAB DISCLAIMERS JSON : "+json);
		return json;
	}

	@RequestMapping(value={"experiment/getPdf/file/{id}", "getPdf/file/{id}"})
	public String handlePDFFileInExperiment(@PathVariable String id, HttpServletResponse response) {
		
		System.out.println("PDF ID Experiment : "+id);
		
		Iterator iterate = student.getExperimentModules().iterator();
		while(iterate.hasNext()) {
			Module module = (Module) iterate.next();
			if(module.getId() == Integer.parseInt(id)) {
				try {
					response.setContentType("application/pdf");
					response.setContentLength(module.getFileBytes().length);
					response.getOutputStream().write(module.getFileBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	

}
