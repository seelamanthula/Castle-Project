package com.company.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.json.questions.JsonFiles;
import com.company.model.FileContent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
@Scope("session")
public class ReadExperimentModule extends ReadCreateAssignment {

	/**
	 * 
	 * 
	 */
	@RequestMapping(value="navigate/experiment/new", method=RequestMethod.POST)
	public @ResponseBody String navigateNewExperiment(HttpServletRequest request) {
		System.out.println("Inside Navigate Experiment");
		return "create-assignment";
	}
	
	@RequestMapping(value="get/all-experiments")
	public @ResponseBody String getAllExperimentsWithStatus(HttpServletRequest request) {
		System.out.println("Retrieve Exp Status ");
		String json = navigateExperiment.getAllExperimentsForInstructor();
		return json;
	}
	
	@RequestMapping(value={"create-assignment.com"})
	public ModelAndView moveToCreateAssignment(HttpServletRequest request) {	
		System.out.println("Create Assignment");
		String id = request.getParameter("id");
		ModelAndView model = new ModelAndView("createAssignment");
		model.addObject("expId", id);
		model.addObject("expStatus","new");
		return model;
	}
	
	@RequestMapping(value="get-assignment.com")
	public ModelAndView moveToDraftAssignment(HttpServletRequest request) {	
		System.out.println("Draft Assignment");
		String id = request.getParameter("id");
		ModelAndView model = new ModelAndView("createAssignment");
		model.addObject("expId", id);
		model.addObject("expStatus","exists");
		return model;
	}
	
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
	
	@RequestMapping(value="get-experiment-info.json")
	public @ResponseBody String getDraftExperimentInformation(HttpServletRequest request) {	
		System.out.println("Get Draft Experiment");

	    String json = readFromBufferedReader(request);

	    if(json != null) {
            JsonObject mainObject = new JsonParser().parse(json).getAsJsonObject();
	        json = mainObject.get("eId").getAsString();
	        json = navigateExperiment.getInstructorExperimentQuestionsInformation(json);
            json = json.replace("\\\"", "\"");
	    }
		
		return json;
	}
	

	@RequestMapping(value="remove/module")
	public @ResponseBody String removeModule(HttpServletRequest request) {	
		System.out.println("Reading Remove Module");
	    String json = readFromBufferedReader(request);
	    if(json == null) return "failed";
        
	    navigateExperiment.removeModule(json);
		return "success";
	}
	
	@RequestMapping(value="update/modulename")
	public @ResponseBody String updateModule(HttpServletRequest request) {
		System.out.println("Update Module NAME");
	    String json = readFromBufferedReader(request);
	    if(json == null) return "failed";
        
	    navigateExperiment.updateModuleName(json);
		return "success";
	}

	@RequestMapping(value="new-experiment/id")
	public @ResponseBody String createNewExperimentId(HttpServletRequest request) {
		System.out.println("Reading New Experiment ID");

		String json = readFromBufferedReader(request);
        if(json == null) return "failed";
        
        navigateExperiment.storeNewExperimentID(json);
		return "success";
	}
	
	@RequestMapping(value="new-experiment/name")
	public @ResponseBody String createNewExperimentName(HttpServletRequest request) {
		System.out.println("Reading New Experiment NAME");
		
		String json = readFromBufferedReader(request);
		if(json == null) return "failed";
        
		System.out.println("N ID : "+json);
		navigateExperiment.updateNewExperimentName(json);
	    return "success";
	}
	
	@RequestMapping(value="new-experiment/files")
	public @ResponseBody String createNewExperimentFiles(HttpServletRequest request) {
		System.out.println("Reading New Experiment Files");
		
		String json = readFromBufferedReader(request);
		if(json == null) return "failed";
        
		navigateExperiment.updateExperimentFile(json);	    
		return "success";
	}
	
	@RequestMapping(value="check/experiment-file")
	public @ResponseBody String checkNewExperimentFiles(HttpServletRequest request) {
		System.out.println("Check New Experiment FILES ....");
	
		String json = readFromBufferedReader(request);
		if(json == null) return "failed";
            
        boolean k = navigateExperiment.checkExperimentFile(json);
        System.out.println("Files Status : "+k);
        if(k) return "success";
	
        return "failed";
	}
		
	
	@RequestMapping(value="update/duedate")
	public @ResponseBody String updateExperimentDueDate(HttpServletRequest request) {
		System.out.println("Update Duedate");
		
		String json = readFromBufferedReader(request);
		if(json == null) return "failed";
        
		navigateExperiment.updateExperimentDueDate(json);
	    return "success";
	}
	
	@RequestMapping(value="read_order_modules")
	public @ResponseBody String readOrderModulesList(HttpServletRequest request) {
		System.out.println("Read order modules list");
		
		String json = readFromBufferedReader(request);
		if(json == null) return "failed";
        
		navigateExperiment.updateExperimentModuleList(json);
	    return "success";
	}
	

	@RequestMapping(value="newexperiment/publish")
	public @ResponseBody String publishButton(HttpServletRequest request) {
		System.out.println("Publish Button");

		String json = readFromBufferedReader(request);
		if(json == null) return "failed";
        
		if(navigateExperiment.publishExperiment(json))
        	return "move";
         	
		return "failed";
	}
	
	@RequestMapping(value="newexperiment/draft")
	public @ResponseBody String draftButton(HttpServletRequest request) {
		System.out.println("Draft Button");
		
		String json = readFromBufferedReader(request);
		if(json == null) return "failed";
        
		if(navigateExperiment.draftExperiment(json))
	      	return "move";
	        
	    return "failed";
	}
	
	@RequestMapping(value="newexperiment/discard")
	public @ResponseBody String discardButton(HttpServletRequest request) {
		System.out.println("Discard Button");
		
		String json = readFromBufferedReader(request);
		if(json == null) return "failed";
        
		if(navigateExperiment.discardExperiment(json))
	       	return "move";
		        
	    return "failed";
	}
	
}
