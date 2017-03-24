package com.company.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.persistence.FileStorageService;
import com.company.service.NavigateQuestions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
@Scope("session")
public class ReadQuestions  extends ReadCreateAssignment{

	@RequestMapping(value="/remove/question")
	public @ResponseBody String readRemoveQuestion(HttpServletRequest request) {
		System.out.println("Reading Remove Question");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
	        
	        System.out.println("Remove Question : "+json);
	        navigate.removeQuestion(json);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return "success";
	}	

	@RequestMapping(value="/readmoduleslist")
	public @ResponseBody String readModulesList(HttpServletRequest request) {
		System.out.println("Reading Modules List");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
	        
	 //       System.out.println("readmoduleslist : "+json);
	        
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return "success";
	}	
	
	/*@RequestMapping(value="/read_order_modules")
	public @ResponseBody String readOrder(HttpServletRequest request) {
		System.out.println("Reading Order Modules");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }   
	    //    System.out.println("Order : "+json);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return "success";
	}	
*/
	@RequestMapping(value="/readshortdata")
	public @ResponseBody String readShortQuestion(HttpServletRequest request) {
		
		System.out.println("Reading Short Question");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
            System.out.println("SHORT Length : "+json.length());
	       // System.out.println("Short Question : "+json);
	        
	        navigate.parseAnyQuestion(json);
	        
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return "success";
	}	

	@RequestMapping(value="/readnumdata")
	public @ResponseBody String readNUMQuestion(HttpServletRequest request) {
	
		System.out.println("Reading Numerical Question");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
            System.out.println("NUM Length : "+json.length());
	      //  System.out.println("Numerical Question : "+json);
	        
	        navigate.parseAnyQuestion(json);
	        
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return "success";		
	}	
	
	@RequestMapping(value="/readmcqdata")
	public @ResponseBody String readMCQuestion(HttpServletRequest request) {
		System.out.println("Reading MCQ Question");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
          //  System.out.println("MCQ Length : "+json.length());
	   //     System.out.println(json);
	  
	        navigate.parseAnyQuestion(json);
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}	
	

}
