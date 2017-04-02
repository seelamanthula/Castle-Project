package com.company.controller.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("session")
public class SearchController extends MainSearchAccount {

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
	
	@RequestMapping(value="get/all-search-students")
	public @ResponseBody String getAllSearchContent() {
		System.out.println("In Search Students");

		String s = searchNavigation1.getAllStudentProfiles();
		return s;
	}
	
	@RequestMapping(value="get/grade-center-info")
	public @ResponseBody String getGradeCenterInformation() {
		System.out.println("In Grade Center");
		
		String s = searchNavigation1.getAllStudentGradeCenter();
		return s;
	}
	
	@RequestMapping(value="update-student-scores.edu")
	public @ResponseBody String updateStudentGradeCenterInformation(HttpServletRequest request) {
		System.out.println("Update Student Information");
		
		String json = readFromBufferedReader(request), s = "connection_lost";
		
		if(json == null)
			json = "login-received-success";
		else  {
	        s = searchNavigation1.parseUpdateStudentGCInformation(json);
	        if(s == null) s = "connection_lost";
		}
		return s;
	}

	@RequestMapping(value="make-get-pdf2")
	public @ResponseBody String assessmentReport(HttpServletRequest request) {
		System.out.println("Make PDFs");

		String json = readFromBufferedReader(request), s = "connection_lost";
		if(json == null)
			json = "login-received-success";
		else  {
	         s = searchNavigation1.parseAssessmentReport(json);
	         if(s == null) s = "connection_lost";
		}
		return s;
	}

	@RequestMapping(value="prelab-report/{netId}/{expId}/{moduleId}")	
	public String getPrelab(@PathVariable String netId, @PathVariable String expId, 
														@PathVariable String moduleId, 
														HttpServletResponse response) {
		System.out.println("In Prelab Report");

		try {
			byte[] s = searchNavigation1.getPrelabDataForStudent(netId, expId, moduleId);
			response.setContentType("application/pdf");
			response.setContentLength(s.length);
			response.getOutputStream().write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="assessment-report/{netId}/{expId}/{moduleId}")
	public String getPAssessment(@PathVariable String netId, @PathVariable String expId, 
															 @PathVariable String moduleId, 
															 HttpServletResponse response) {
		System.out.println("In Prelab Report");

		try {
			byte[] s = searchNavigation1.getAssessmentDataForStudent(netId, expId, moduleId);
			response.setContentType("application/pdf");
			response.setContentLength(s.length);
			response.getOutputStream().write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
