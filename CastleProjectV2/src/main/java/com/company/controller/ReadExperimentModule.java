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
import com.company.persistence.FileStorageService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
@Scope("session")
public class ReadExperimentModule extends ReadCreateAssignment {

	@RequestMapping(value="navigate/experiment/new", method=RequestMethod.POST)
	public @ResponseBody String navigateNewExperiment(HttpServletRequest request) {
		System.out.println("Inside Navigate Experiment");
		return "create-assignment";
	}
	
	@RequestMapping(value="get/all-experiments")
	public @ResponseBody String getAllExperimentsWithStatus(HttpServletRequest request) {
		System.out.println("Retrieve Exp Status ");
		
		// Retrieve all the experiments with status
		String json = navigateExperiment.getAllExperimentsForInstructor();
		
		return json;
	}
	
	@RequestMapping(value={"create-assignment.com"})
	public ModelAndView moveToCreateAssignment(HttpServletRequest request) {	
		System.out.println("Create Assignment");
		String id = request.getParameter("id");
		
		System.out.println("New Experiment ID : "+id);
		
		ModelAndView model = new ModelAndView("createAssignment");
		model.addObject("expId", id);
		model.addObject("expStatus","new");
	//	return "createAssignment";
		return model;
	}
	
	@RequestMapping(value="get-assignment.com")
	public ModelAndView moveToDraftAssignment(HttpServletRequest request) {	
		System.out.println("Draft Assignment");
		String id = request.getParameter("id");
		System.out.println("Draft Experiment ID : "+id);
		
		ModelAndView model = new ModelAndView("createAssignment");
		model.addObject("expId", id);
		model.addObject("expStatus","exists");
		return model;
	}
	
	
	@RequestMapping(value="get-experiment-info.json")
	public @ResponseBody String getDraftExperimentInformation(HttpServletRequest request) {	
		System.out.println("Get Draft Experiment");
/*		String id = request.getParameter("id");
		System.out.println("Draft Experiment ID : "+id);
		
		String json = navigateExperiment.getInstructorExperimentQuestionsInformation(id);
		return json;
		*/
		BufferedReader br;
	    String json = "";
		   
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        if(br != null){
	            json = br.readLine();
	            System.out.println("Input : "+json);
	            JsonObject mainObject = new JsonParser().parse(json).getAsJsonObject();
		        json = mainObject.get("eId").getAsString();
		        System.out.println("after parsing : "+json);
	            json = navigateExperiment.getInstructorExperimentQuestionsInformation(json);
	            json = json.replace("\\\"", "\"");
	            
	            System.out.println("E output : "+json);
	        }
	        
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	

	@RequestMapping(value="remove/module")
	public @ResponseBody String removeModule(HttpServletRequest request) {	
		System.out.println("Reading Remove Module");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
	        System.out.println(json);
	        navigateExperiment.removeModule(json);
	        
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	@RequestMapping(value="update/modulename")
	public @ResponseBody String updateModule(HttpServletRequest request) {
		System.out.println("Update Module NAME");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
	        System.out.println(json);
	        navigateExperiment.updateModuleName(json);
	
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

	@RequestMapping(value="new-experiment/id")
	public @ResponseBody String createNewExperimentId(HttpServletRequest request) {
		System.out.println("Reading New Experiment ID");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
	        System.out.println("Before :"+json);
		      
	   /*     JsonObject mainObject = new JsonParser().parse(json).getAsJsonObject();
	        json = mainObject.get("eId").getAsString()*/;
	      //  System.out.println("After : "+json);
	        if(json != null)
	        navigateExperiment.storeNewExperimentID(json);
	        
	        br.close();
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	@RequestMapping(value="new-experiment/name")
	public @ResponseBody String createNewExperimentName(HttpServletRequest request) {
		System.out.println("Reading New Experiment NAME");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
	        System.out.println(json);
	        navigateExperiment.updateNewExperimentName(json);
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}
	
	@RequestMapping(value="new-experiment/files")
	public @ResponseBody String createNewExperimentFiles(HttpServletRequest request) {

		System.out.println("Reading New Experiment Files");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	            System.out.println("Experiment File Length : "+json.length());
	        }
	   //     System.out.println(json);
	     
	        navigateExperiment.updateExperimentFile(json);
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}
	
	@RequestMapping(value="check/experiment-file")
	public @ResponseBody String checkNewExperimentFiles(HttpServletRequest request) {
		System.out.println("Check New Experiment FILES ....");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
	        
/*	        JsonObject mainObject = new JsonParser().parse(json).getAsJsonObject();
	        json = mainObject.get("eId").getAsString();*/
	        
	        System.out.println(json);
	        boolean k = navigateExperiment.checkExperimentFile(json);
	
	        System.out.println("Files Status : "+k);
	        if(k) return "success";
	        
	        System.out.println("Coming inside");
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "failed";
		}
		return "failed";
	}
		
	
	@RequestMapping(value="update/duedate")
	public @ResponseBody String updateExperimentDueDate(HttpServletRequest request) {
		System.out.println("Update Duedate");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
	        System.out.println(json);
	        navigateExperiment.updateExperimentDueDate(json);
	
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
		
		
	}
	
	@RequestMapping(value="read_order_modules")
	public @ResponseBody String readOrderModulesList(HttpServletRequest request) {
		System.out.println("Read order modules list");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
	        System.out.println(json);
	        navigateExperiment.updateExperimentModuleList(json);
//	        navigateExperiment.checkExperimentFile(experimentId)
	
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}
	

	@RequestMapping(value="newexperiment/publish")
	public @ResponseBody String publishButton(HttpServletRequest request) {
		System.out.println("Publish Button");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
	        /*
	        JsonObject mainObject = new JsonParser().parse(json).getAsJsonObject();
	        json = mainObject.get("eId").getAsString();
	        */
	        System.out.println(json);
	        if(navigateExperiment.publishExperiment(json))
	        	return "move";
	        
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Return all the valid experiment list IDs as a response
		//return new Gson().toJson(navigateExperiment.getAllExperimentContent());
		
		return "failed";
	}
	
	@RequestMapping(value="newexperiment/draft")
	public @ResponseBody String draftButton(HttpServletRequest request) {
		System.out.println("Draft Button");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
	        System.out.println(json);
	        if(navigateExperiment.draftExperiment(json))
	        	return "move";
	        
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Return all the valid experiment list IDs as a response
		// use getAllExperimentsForInstructor

		/*	String s = new Gson().toJson(navigateExperiment.getAllExperimentContent());
		System.out.println("GSON : "+s);*/

		return "failed";
	}
	
	@RequestMapping(value="newexperiment/discard")
	public @ResponseBody String discardButton(HttpServletRequest request) {
		System.out.println("Discard Button");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
	        System.out.println(json);
	        if(navigateExperiment.discardExperiment(json))
	       	return "move";
		        
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Return all the valid experiment list IDs as a response
	//	String s = new Gson().toJson(navigateExperiment.getAllExperimentContent());
		// use getAllExperimentsForInstructor
		
//		System.out.println("GSON : "+s);		
		return "failed";
				
		//return "success";
	}
	
}
