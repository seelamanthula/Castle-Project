package com.company.demo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.company.json.questions.MultipleChoiceJson;
import com.company.service.NavigateQuestions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
@Scope("session")
public class Read1 {

	NavigateQuestions navigate;
	
	@Autowired
	private void setNavigateQuestions(NavigateQuestions navigate) {
		this.navigate = navigate;
	}
	
	@RequestMapping(value="/uploadquestions", method=RequestMethod.POST)
	public void readFiles(@RequestParam("data") String data, @RequestParam("files") MultipartFile[] files) {
		System.out.println("Hitted");
		System.out.println("Files No : "+files.length);
		System.out.println("Data : "+data);
	}
	
	@RequestMapping(value="/showMessage.html")
	public String helloMessage() {
		System.out.println("Show the message");
		return "showMessage";
	}
	
	@RequestMapping(value="/readdata")
	public String readQuestion(HttpServletRequest request) {
		System.out.println("Reading MCQ Question");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
	        System.out.println(json);
	        
	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(json);    
	        JsonObject obj = element.getAsJsonObject();
	        		
	       // navigate.parseMCQ(obj);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "showMessage";
	}	
}
