package com.company.controller.account;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.json.others.TARegistration;
import com.company.model.account.registration.AccountLogin;
import com.company.service.NavigateRegistration;

@Controller
@Scope("session")
public class InstructorController extends MainAccount {

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
	/*
	 * Authentication for all (Instructor, TA and Student)
	 */
	@RequestMapping(value="login/validation")
	public @ResponseBody String loginValidation(HttpServletRequest request) {	
		System.out.println("In Login Validation");

		String json = readFromBufferedReader(request);
        if(json != null){
	        AccountLogin login = studentNavigation.parseAndValidateAccountLogin(json);
            if(login == null) 	{
            	return "login_failure";
            } else {
            	if(login.getAccountType().equalsIgnoreCase("student")) {
            		int k = studentNavigation.checkStudentGradeCenter(login.getNetId());
            		if(k == 0)    	return "login_failure";
            	}
            }	
            return "login_success";
        }
		return "login_failure";
	}
	

	@RequestMapping(value="move-instructor.com")
	public String moveToInstructor(HttpServletRequest request) {	
		System.out.println("move Instructor");

		return "instructor";
	}

	private String mainRegistrationJson = "";
	
	@RequestMapping(value="parse/registration.json")
	public @ResponseBody String parseTAExcelInformation(HttpServletRequest request) {
		System.out.println("Parse Registration json");

		String json =readFromBufferedReader(request);
		if(json == null){
        	json = "excel-received-success";
        } else{
        	json = navigateRegistration.parseTARegistration(json);
        }
        return json;
	}

	@RequestMapping(value="register/studentRegistration.json")
	public @ResponseBody String registerStudentregistration(HttpServletRequest request) {
		System.out.println("Register Student Registration json");		
		
	    String json = readFromBufferedReader(request);
        if(json != null){
            navigateRegistration.modifiedAndNotModifiedJSONStore(json, mainRegistrationJson);
	        navigateRegistration.storeRegistration();
        }
		
		return "excel-received-success";
	}
	
}
