package com.chemistry.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chemistry.lab.PostLab;
import com.chemistry.util.PDFPostLabGenerate;
import com.google.gson.Gson;

@Controller
@Scope("session")
public class PostLabController extends Arrange {

	@RequestMapping(value="postlab.html", method=RequestMethod.GET)
	public String getPostLab() {
		System.out.println("TO PostLab");
		

		return "student/postlab";
	}
	
	@RequestMapping(value="postlab.json", method=RequestMethod.GET)
	public @ResponseBody String getPostLabJSON() {
		
	
		String json;;

		PostLab[] postlab = getStudent().getPostLabArray();
		if(postlab[0].getConcept().getId() == 2) {
			json = new Gson().toJson(postlab[0]);
		} else {
			json = new Gson().toJson(postlab[1]);			
		}
		System.out.println("Post lab JSON : "+json);
		return json;
	}

	@RequestMapping(value="experiment/makepostlabpdf.json", method=RequestMethod.GET)
	public String getPostLabJSON(@RequestBody String solutions) {
		
		System.out.println("Came to submit PostLab ");
		
		PostLab[] postlab = getStudent().getPostLabArray();
		String json = new Gson().toJson(postlab);
		System.out.println("Post lab JSON : "+json);
		return json;
	}
	
	
	/*@RequestMapping(value="Postlab", method=RequestMethod.POST)
	public String getPostLabData(HttpServletRequest request) {
		
		System.out.println("Came to submit PostLab ");
		
		String t1, t2, t3, t4, t5;
		t1 = request.getParameter("T0");
		t2 = request.getParameter("T1");
		t3 = request.getParameter("T2");
		t4 = request.getParameter("T3");
		t5 = request.getParameter("T4");
		
		System.out.println("T1 : "+t1);
		System.out.println("T2 : "+t2);
		System.out.println("T3 : "+t3);
		System.out.println("T4 : "+t4);
		System.out.println("T5 : "+t5);

		String[] questions = new String[5];
		questions[0] = t1;
		questions[1] = t2;
		questions[2] = t3;
		questions[3] = t4;
		questions[4] = t5;
		
		PostLab[] postlab = getStudent().getPostLabArray();
		String json = new Gson().toJson(postlab);
		System.out.println("Post lab JSON : "+json);
		
		new PDFPostLabGenerate().PostlabPDFGeneration(questions, getUser().getNetId());
		return "duplicate/status";
	}
*/
}
