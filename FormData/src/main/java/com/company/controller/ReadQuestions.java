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

import com.company.service.NavigateQuestions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
@Scope("session")
public class ReadQuestions  extends ReadCreateAssignment{

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
	
	@RequestMapping(value="/remove/question")
	public @ResponseBody String readRemoveQuestion(HttpServletRequest request) {
		System.out.println("Reading Remove Question");

		String json = readFromBufferedReader(request);
        navigate.removeQuestion(json);
		return "success";
	}	

	@RequestMapping(value="/readshortdata")
	public @ResponseBody String readShortQuestion(HttpServletRequest request) {		
		System.out.println("Reading Short Question");

		String json = readFromBufferedReader(request);
		navigate.parseAnyQuestion(json);
	    return "success";
	}	

	@RequestMapping(value="/readnumdata")
	public @ResponseBody String readNUMQuestion(HttpServletRequest request) {
		System.out.println("Reading Numerical Question");
		
		String json = readFromBufferedReader(request);
		navigate.parseAnyQuestion(json);
	    return "success";		
	}	
	
	@RequestMapping(value="/readmcqdata")
	public @ResponseBody String readMCQuestion(HttpServletRequest request) {
		System.out.println("Reading MCQ Question");
		
		String json = readFromBufferedReader(request);
		navigate.parseAnyQuestion(json);
	    return "success";
	}	
}
