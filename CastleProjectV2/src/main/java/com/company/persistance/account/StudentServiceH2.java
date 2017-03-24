package com.company.persistance.account;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.model.relation.ModuleDetails;
import com.company.model.student.StudentAssessmentModule;
import com.company.model.student.StudentExperimentModule;
import com.company.model.student.StudentPrelab;
import com.company.model.student.module.EachModule;
import com.company.model.student.status.ExperimentDetails;

@Service
@Scope("session")
public class StudentServiceH2 extends StudentService {

	
	private ModuleDetails getAssessmentModuleQuestionIds(String moduleId) {
		return studentExperimentQuestions.getModuleQuestionIds(moduleId);
	}
	
	public ExperimentDetails getExperimentContent(String experimentId) {
		return studentExperimentQuestions.getExperimentContent(experimentId);
	}
	
	public EachModule gatherEachModuleAssessmentInformation(StudentExperimentModule experimentModule) {
		
		ExperimentDetails eDetails = getExperimentContent(experimentModule.getExperimentId());
		if(eDetails == null) return null;
		
		ModuleDetails mDetails = getAssessmentModuleQuestionIds(experimentModule.getModuleId());
		if(mDetails == null) return null;
		
		eDetails.setPdf2("");
		//eDetails.setPdf("");
		EachModule each = new EachModule();		
		each.setExperiment(eDetails);
		each.setModule(mDetails);
		each.setNetId(experimentModule.getNetId());
		
		return each;
	}
	
	public EachModule gatherStudentPrelabInformation(StudentExperimentModule experimentModule) {
		return studentExperimentQuestions.getStudentPrelabInformation(experimentModule);
	}
	
	public int updateStudentPrelabDataGC(StudentPrelab prelab) {
		return studentExperimentQuestions.updateStudentPrelabDataGC(prelab);
	}
	
	public String getStudentPrelabDataGC(StudentExperimentModule experimentModule) {		
		return studentExperimentQuestions.getStudentPrelabDataGC(experimentModule);
	}
	
	public String getExperimentQuestion(String questionId) {
		return studentExperimentQuestions.getExperimentQuestion(questionId);
	}
	
	public int updateModuleQuestion(StudentAssessmentModule assessment) {
		return studentExperimentQuestions.updateStudentAssessmentGC(assessment);		
	}
	
	public StudentAssessmentModule checkStudentExperimentModule(StudentExperimentModule seModule) {
		return studentExperimentQuestions.getStudentAssessmentGC(seModule);			
	}
	
}
