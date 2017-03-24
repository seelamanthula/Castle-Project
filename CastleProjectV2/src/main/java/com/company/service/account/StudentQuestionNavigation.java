package com.company.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.json.questions.UpdateAssessmentPrelabModuleJson;
import com.company.model.account.registration.AccountLogin;
import com.company.model.registration.StudentProfile;
import com.company.model.student.StudentAssessmentModule;
import com.company.model.student.StudentExperimentModule;
import com.company.model.student.StudentPrelab;
import com.company.model.student.module.EachModule;
import com.company.model.student.status.ExperimentDetails;
import com.company.persistance.account.StudentServiceH2;
import com.google.gson.Gson;

@Service
@Scope("session")
public class StudentQuestionNavigation extends StudentNavigation {
	
	public String gatherEachModuleAssessmentInformation(StudentExperimentModule experimentModule) {
		EachModule each = studentServiceH2.gatherEachModuleAssessmentInformation(experimentModule);
		
		System.out.println("Each : "+each);
		if(each == null) return "no-experiment";
		
		each.setNetId(experimentModule.getNetId());
		each.setType("Assessment");
	//	each.setFirstName("Get the Bio");
		each.setFirstName(studentService.getStudentProfile().getStudentDetails().getFirstName());
		//each.setNetId(login.getNetId());
		return new Gson().toJson(each);
	}
	
	public String gatherStudentPrelabInformation(StudentExperimentModule experimentModule) {
	
		EachModule each = studentServiceH2.gatherStudentPrelabInformation(experimentModule);
		if(each == null) return "no-experiment";
		
		each.setNetId(experimentModule.getNetId());
	//	each.setFirstName("Get Bio Info");
		each.setFirstName(studentService.getStudentProfile().getStudentDetails().getFirstName());
		each.setType("Prelab");
		
		return new Gson().toJson(each);
	}
	public String getExperimentFile(String experimentId) {
		ExperimentDetails eDetails = studentServiceH2.getExperimentContent(experimentId);
		return eDetails.getPdf2();
	}
	
	public String updateStudentPrelabDataGC(StudentPrelab prelab) {
		int a = studentServiceH2.updateStudentPrelabDataGC(prelab);
		if(a == 0) return "failed";
		
		return "success";
	}
	public String checkGetStudentPrelabDataGC(StudentExperimentModule experimentModule) {		
		String s = studentServiceH2.getStudentPrelabDataGC(experimentModule);
		if(s == null) return "no-saving";
		
		return s;
	}
	
	public String getExperimentQuestion(String questionId) {
		return studentServiceH2.getExperimentQuestion(questionId);
	}
	
	public String parseUpdateModuleQuestion(String json) {
		
		UpdateAssessmentPrelabModuleJson questionJson = new UpdateAssessmentPrelabModuleJson();		
		StudentAssessmentModule moduleScore = questionJson.parseStudentQuestionAnswer(json);
		
		int k = studentServiceH2.updateModuleQuestion(moduleScore);
		if(k == 1) return "success";
		
		return "failed";
	}
	
	public String parseUpdatePrelabAnswers(String json) {
		
		UpdateAssessmentPrelabModuleJson questionJson = new UpdateAssessmentPrelabModuleJson();		
		StudentPrelab prelab = questionJson.updatePrelabAnswersJSON(json);
		
		int k = studentServiceH2.updateStudentPrelabDataGC(prelab);
		if(k == 1) return "success";
		
		return "failed";
	}
	
	public String checkStudentExperimentModule(StudentExperimentModule seModule) {
		StudentAssessmentModule saModule = studentServiceH2.checkStudentExperimentModule(seModule);
		if(saModule == null) return "firsttime";
		
		return saModule.getmData();
	}
}
