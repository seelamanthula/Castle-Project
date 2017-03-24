package com.chemistry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chemistry.service.StudentStatusService;

@Controller
public class Authentication {

	private StudentStatusService student = null;
	
	@Autowired
	public void setStudent(StudentStatusService student) {
		this.student = student;
	}

	@RequestMapping(value="authenticate", method=RequestMethod.GET)
	public String authenticateStudent(@RequestParam String netId) {
	
		System.out.println("Student ID : "+netId);
		/*
		 * If that netId matches the Student the go to student/status
		 */
		
		return "student/status";
	}
	
	
}
