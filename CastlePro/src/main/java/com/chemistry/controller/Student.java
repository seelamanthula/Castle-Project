package com.chemistry.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chemistry.lab.Assessment;
import com.chemistry.lab.AssignmentStatus;
import com.chemistry.lab.LabExperiment;
import com.chemistry.lab.PreLabDisclaimers;
import com.chemistry.model.Experiment;
import com.chemistry.model.Module;
import com.chemistry.model.PreLab;
import com.chemistry.questions.Question;
import com.chemistry.service.StudentService;
import com.chemistry.service.StudentStatusService;
import com.chemistry.util.Calculations;
import com.chemistry.util.VolumetricCalculations;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
@Scope("session")
public class Student extends Arrange{

/*	private HSSFWorkbook workbook = new HSSFWorkbook();
	private HSSFWorkbook workbook2 = new HSSFWorkbook();*/
	
	private HSSFWorkbook workbook;
	private HSSFWorkbook workbook2;
	
	public HSSFWorkbook getWorkbook2() {
		return workbook2;
	}

	public void setWorkbook2(HSSFWorkbook workbook2) {
		this.workbook2 = workbook2;
	}

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	@RequestMapping(value="getPdf/file/{id}")
	public String handlePDFFileInExperiment(@PathVariable String id, HttpServletResponse response) {
		
		System.out.println("inside Excel Part ");
		System.out.println("ID : "+id);
		
		Iterator iterate = getStudent().getExperimentModules().iterator();
		while(iterate.hasNext()) {
			Module module = (Module) iterate.next();
		//	module.getName();
			
			if(module.getId() == Integer.parseInt(id)) {
				try {
					response.setContentType("application/pdf");
					response.setContentLength(module.getFileBytes().length);
					response.getOutputStream().write(module.getFileBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@RequestMapping(value="volumetriclab.html", method=RequestMethod.GET)
	public String handleLab() {		
		return "duplicate/lab1";
	}
	
	@RequestMapping(value="acidbaselab.html", method=RequestMethod.GET)
	public String handleAcidBaseLab() {		
		return "duplicate/lab2";
	}
	
	@RequestMapping(value="volumetriclab.json", method=RequestMethod.GET)
	public @ResponseBody String handleLabJSON() {	
	
		LabExperiment[] labExperiment = getStudent().getLabArray();
		
		String json = "";
		if(labExperiment[0].getConcept().getName().contains("metric"))
			json = new Gson().toJson(labExperiment[0]);
		else 
			json = new Gson().toJson(labExperiment[1]);

		System.out.println("Volumetric JSON : "+json);
		
		return json;
	}
	
	@RequestMapping(value="acidbaselab.json", method=RequestMethod.GET)
	public @ResponseBody String handleAcidBaseLabJSON() {	
	
		LabExperiment[] labExperiment = getStudent().getLabArray();
		
		String json = "";
		if(labExperiment[0].getConcept().getName().contains("metric"))
			json = new Gson().toJson(labExperiment[1]);
		else 
			json = new Gson().toJson(labExperiment[0]);

		System.out.println("AcidBase JSON : "+json);
		
		return json;
	}
	
	
	@RequestMapping(value="makeexcel1.json", method=RequestMethod.GET)
	public @ResponseBody String makeLabExcel1(@RequestParam String excel, HttpServletResponse response) {
	
		workbook = new HSSFWorkbook();
		
		System.out.println("Came to Excel Conversion");
		JsonParser parser = new JsonParser();
		JsonElement tradeElement = parser.parse(excel);
		JsonArray trade = tradeElement.getAsJsonArray();

		System.out.println(trade);
		Map<String, Object[]> map = getJsonToExcelConverter().excel1Converter(trade);		
		workbook = getExcelGeneration().toExcelWorkbook1(workbook, map);

		String json = new Gson().toJson("yes");
		System.out.println("Calculated : "+json);
		return json;
	}
	
	@RequestMapping(value="senddensity.json", method=RequestMethod.GET)
	public @ResponseBody String getDensity(@RequestParam double density, HttpServletResponse response) {

		System.out.println("Density Received : ");
		System.out.println(density);
		VolumetricCalculations calcu = new VolumetricCalculations();

		try {
		
		double[] mass = getExcelGeneration().getMassOfWaterOfVolumetric(workbook);
		System.out.println("Made Masses");
		calcu.setMass(mass);
		
		Calculations calculations = new Calculations();
		
		double[] volumes = calculations.getVolumeOfWater(mass, density);
		System.out.println("Got Densities : "+volumes);
		calcu.setVolumes(volumes[0]);
		
		double g = calcu.getVolumes();
//		for(int i = 0; i <calcu.getVolumes().length; i++)
			System.out.println(g);
		
		double average = calculations.average(volumes);
		System.out.println("AVG : "+average);
		calcu.setAverage(average);
		
		double rsd = calculations.RSD(volumes);
		System.out.println("RSD : "+rsd);
		calcu.setRsd(rsd);
		
		double pe = calculations.percentError(average,5);
		System.out.println("PE : "+pe);
		calcu.setPercentError(pe);
}
catch(Exception e) {
	e.getMessage();
	e.printStackTrace();
}
		String json = new Gson().toJson(calcu);
		System.out.println("Calculated : "+json);
		return json;
	}
	
	
	@RequestMapping(value="makeexcel2.json", method=RequestMethod.GET)
	public @ResponseBody String makeLabExcel2(@RequestParam String excel, HttpServletResponse response) {
	
		workbook2 = new HSSFWorkbook();

		JsonParser parser = new JsonParser();
		JsonElement tradeElement = parser.parse(excel);
		JsonArray trade = tradeElement.getAsJsonArray();
		System.out.println("Before Conversion");
		Map<String, Object[]> map = getJsonToExcelConverter().excel1Converter(trade);		
		System.out.println("After Conversion");
		
		workbook2 = getExcelGeneration().toExcelWorkbook2(workbook2, map);
		System.out.println("Workbook Generated");

		double[] volumes = getExcelGeneration().getVolumesOfNaOHOfAcidBase(workbook2);
		System.out.println("Volumes Calculated");
		System.out.println(" "+volumes[0]);
		System.out.println(" "+volumes[1]);
		System.out.println(" "+volumes[2]);
		System.out.println(" "+volumes[3]);
		System.out.println(" "+volumes[4]);
		
		Calculations calculations = new Calculations();
		double[] molarities = calculations.getMolarites(volumes);
		System.out.println("Molarities Calculated");
		System.out.println(" "+molarities[0]);
		System.out.println(" "+molarities[1]);
		System.out.println(" "+molarities[2]);
		System.out.println(" "+molarities[3]);
		System.out.println(" "+molarities[4]);
		
		double avg = calculations.average(molarities);
		System.out.println("Average : "+avg);
		
		double rsd = calculations.RSD(molarities);
		System.out.println("RSD : "+rsd);
		VolumetricCalculations calcu = new VolumetricCalculations();
		calcu.setAverage(avg);
		calcu.setRsd(rsd);
		calcu.setVolumes(volumes[0]);
		
		String json = new Gson().toJson(calcu);
		System.out.println("AB Calculated : "+json);
		return json;
	}
	
	@RequestMapping(value="getExcel/file/{id}")
	public String handleExcelFileInExperiment(@PathVariable String id, HttpServletResponse response) {
		
		System.out.println("ID : "+id);
		try {
			 response.setContentType("application/vnd.ms-excel");
		     ServletOutputStream stream = response.getOutputStream();
			 if(id.equalsIgnoreCase("volumetric") || id.equalsIgnoreCase("0")) {
				 System.out.println("In 0");
				 workbook.write(stream);
			 }
			 else  {
				 System.out.println("In 1");
				workbook2.write(stream);							
			 }
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
						
		return null;
	}
	
	
	@RequestMapping(value="makevolumelabpdf.json", method=RequestMethod.GET)
	public @ResponseBody String makeLabPDF(@RequestParam String lab, @RequestParam String lab2, HttpServletRequest request) {

		System.out.println("In Lab PDF");
		System.out.println("STR 1 " +lab);
		System.out.println("STR 2 " +lab2);

		System.out.println("HTTPSR");
		System.out.println("I1 : "+request.getParameter("lab"));
		System.out.println("I1 : "+request.getParameter("lab2"));
		
		/*		System.out.println(textarea);
		JsonParser parser = new JsonParser();
		JsonElement tradeElement = parser.parse(textarea);
		JsonArray trade = tradeElement.getAsJsonArray();
		System.out.println(trade);*/
		System.out.println("Before Lab Convertion");
		
 		return "one";
	}
	
	
}
