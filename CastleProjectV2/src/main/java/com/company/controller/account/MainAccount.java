package com.company.controller.account;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.json.others.TARegistration;
import com.company.service.NavigateQuestions;
import com.company.service.NavigateRegistration;
import com.company.service.account.StudentNavigationH1;
import com.company.service.account.StudentQuestionNavigation;

@Controller
@Scope("session")
public class MainAccount {

	protected NavigateRegistration navigateRegistration;
	
	@Autowired
	private void setNavigateRegistration(NavigateRegistration navigateRegistration) {
		this.navigateRegistration = navigateRegistration;
	}
	
	protected StudentNavigationH1 studentNavigation;

	@Autowired
	public void setStudentNavigation(StudentNavigationH1 studentNavigation) {
		this.studentNavigation = studentNavigation;
	}

	protected StudentQuestionNavigation studentQuestionNavigation;

	@Autowired
	public void setStudentQuestionNavigation(StudentQuestionNavigation studentQuestionNavigation) {
		this.studentQuestionNavigation = studentQuestionNavigation;
	}

	
}
