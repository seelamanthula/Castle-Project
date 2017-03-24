package com.company.service.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.json.gradecenter.UpdateStudentScoresJSON;
import com.company.model.gradecenter.GradeTable3;
import com.company.model.gradecenter.GradeTable4;
import com.company.model.gradecenter.UpdateAPListsTAComments;
import com.company.model.registration.StudentProfile;
import com.company.model.relation.ModuleDetails;
import com.company.model.student.status.ExperimentDetails;
import com.company.pdf.AssessPDF;
import com.company.pdf.PrelabPDF;
import com.company.pdf.AssessmentJSON;
import com.company.pdf.PrelabJSON;
import com.company.pdf.model.AssessmentModelPDF;
import com.company.pdf.model.PrelabModel;
import com.google.gson.Gson;

@Service
@Scope("session")
public class SearchNavigation1 extends SearchMainNavigation {

	public String getAllStudentProfiles() {
		ArrayList<StudentProfile> profiles = seachService1.getAllStudentsPersonalInformationForInstructor();
		if(profiles == null) return "No_Students_Enrolled";
		
		return new Gson().toJson(profiles);
	}
	
	public String getAllStudentGradeCenter() {
		GradeTable4 gt4 = seachService1.getAllStudentGradeCenter();
		if(gt4 == null) return "no-students";
		
		return new Gson().toJson(gt4);
	}
	
	public String parseUpdateStudentGCInformation(String json) {
		
		UpdateStudentScoresJSON scoreJson = new UpdateStudentScoresJSON();
		
		UpdateAPListsTAComments aplComments = scoreJson.parseStudentExperimentScores(json);
		int k = seachService2.updateUpdateAPListsTAComments(aplComments);
		
		if(k == 1)
		return "success";
		else
			return "failed";
	}
	
	public String getAssessmentDataForPDF(String netId, String expId, String moduleId) {
		return seachService2.getAssessmentDataForPDF(netId, expId, moduleId);
	}
	
	public String getPrelabDataForPDF(String netId, String expId, String moduleId) {
		return seachService2.getPrelabDataForPDF(netId, expId, moduleId);
	}
	
	public String parseAssessmentReport(String json) {
		/*MakeAssessmentJSON aJSON = new MakeAssessmentJSON();
		aJSON.parseResults(json);*/
		return null;
	}
	public byte[] getAssessmentDataForStudent(String netId, String expId, String moduleId) {
		String s =  getAssessmentDataForPDF(netId, expId, moduleId);
		
		AssessmentJSON trail = new AssessmentJSON();
		AssessPDF pdf = new AssessPDF();
		byte[] out;
		if(s != null) {
			AssessmentModelPDF tPdf = trail.parseResults(s);
			out  = pdf.makePDF(tPdf);
			
		}  else {
			System.out.println("Student Didnt started yet");
		//	return "student is not attempted";
			out =pdf.makePDF(null);
		}
		
		
		return out;
	}
	
	public byte[] getPrelabDataForStudent(String netId, String expId, String moduleId) {
		String s =  getPrelabDataForPDF(netId, expId, moduleId);
		
		System.out.println("Prelab Data : "+s);
		PrelabModel pPdf = null;
		 PrelabPDF pdf = new PrelabPDF();
		 byte[] out;

		 if(s != null) {
			PrelabJSON trail = new PrelabJSON();
			pPdf = trail.parseResults(s);
			out =pdf.makePDF(pPdf);
		} else {
			System.out.println("Student Didnt started yet");
	//		return "student is not attempted";
			out =pdf.makePDF(null);
		}
		
		
		return out;
	}
	
}
