package com.company.persistance.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.dao.account.AuthenticationDAO;
import com.company.model.account.registration.AccountLogin;
import com.company.model.account.registration.StudentRegistration;
import com.company.model.registration.StudentProfile;
import com.company.model.student.status.ExperimentModuleDetails;
import com.company.model.student.status.ExperimentStatus;

@Service
@Scope("session")
public class StudentServiceH1 extends StudentService {

	private StudentProfile studentProfile = null;
	
	public int validateUser(AccountLogin login) {
		return authenticate.validateStudent(login);
	}
	
	public StudentProfile extractUserProfile(AccountLogin login) {
		this.studentProfile = authenticate.getStudentPersonalInformation(login);
		return this.studentProfile;
	}
	
	public StudentProfile getStudentProfile() {
		return this.studentProfile;
	}
	public String getStudentRegistrationInfo() {
		return authenticate.getStudentRegistrationInfo();
	}
	
	public int storeStudentAccount(StudentRegistration student, String classno) {
		return authenticate.storeStudentAccount(student, classno);
	}
	
	private ArrayList<String> checkGetExperimentsStudentGradeCenter(String netId) {
		return studentExperiment.checkGetExperimentsStudentGradeCenter(netId);
	}
	
	private ArrayList<String> getModulesOfExperiment(String experimentId) {
		return studentExperiment.getModulesOfExperiment(experimentId);
	}
	
	public int checkStudentGradeCenter(String netId) {
		
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> experiments = checkGetExperimentsStudentGradeCenter(netId);		
		if(experiments.size() == 0){ return 1;}
		
		Iterator<String> iterate = experiments.iterator();
		
		while(iterate.hasNext()) {
			
			String s = iterate.next();
			ArrayList<String> list = getModulesOfExperiment(s);			
			map.putIfAbsent(s, list);
		}
		
		int k = createStudentAssessmentPrelabGC(netId, map);
		
		if(k == 0) return 0;
		
		return 1;
	}
		
	private Map<String, ExperimentStatus> getStudentAssessmentStatus(String netId) {
		return studentExperiment.getStudentAssessmentStatus(netId);
	}
	
	
	private Map<String, Integer> getStudentPrelabStatus(String netId) {
		return studentExperiment.getStudentPrelabStatus(netId);
	}
	
	public ArrayList<ExperimentStatus> getStudentExperimentStatus(String netId) {
		return processStudentExperimentStatus(netId);
	}
	
	public ExperimentModuleDetails getStudentExperimentAssessmentModulesScore(String netId, String experimentId) {
		return studentExperiment.getStudentExperimentAssessmentModulesScore(netId, experimentId);
	}
	
	public ExperimentModuleDetails getStudentExperimentPrelabModulesScore(String netId, String experimentId) {
		return studentExperiment.getStudentExperimentPrelabModulesScore(netId, experimentId);
	}
	
	private Map<String, String> getStudentTAComments(String netId) {
		return studentExperiment.getStudentTAComments(netId);
	}
	
	private  ArrayList<ExperimentStatus> processStudentExperimentStatus(String netId) {
		Map<String, ExperimentStatus> map = getStudentAssessmentStatus(netId);		
		Map<String, String> comments = getStudentTAComments(netId);
		Map<String, Integer> map2 =  getStudentPrelabStatus(netId);
		
		ArrayList<ExperimentStatus> list = new ArrayList<ExperimentStatus>();
		
		Iterator<Entry<String, ExperimentStatus>> iterate = map.entrySet().iterator();		
		while(iterate.hasNext()) {
			
			Map.Entry<String, ExperimentStatus> pair = iterate.next();
			String exp = pair.getKey();			
			int prelab = map2.get(exp);
			String tac = comments.get(exp);
			
			ExperimentStatus status = pair.getValue();
			status.setPrelabScore(prelab);
			status.setTaComments(tac);
			
			list.add(status);
		}
		
		return list;
	}
	
	private int createStudentAssessmentPrelabGC(String netId, HashMap<String, ArrayList<String>> map) {
		
		Iterator<Entry<String, ArrayList<String>>> iterate = map.entrySet().iterator();		
		while(iterate.hasNext()) {
			
			Map.Entry<String, ArrayList<String>> pair = iterate.next();
			
			String exp = pair.getKey();
			
			int k = iterateModuleList(netId, exp, pair.getValue());
			int l = createTACommentsList(netId, exp);
			if(k == 0) return 0;
		}
		
		return 1;
	}
	
	private int createTACommentsList(String netId, String experimentId) {
		return createStudentGCTAComments(netId, experimentId);
	}
	

	private int iterateModuleList(String netId, String experimentId, ArrayList<String> modules) {
		
		if(modules == null) return 0;
		
		Iterator<String> iterate = modules.iterator();
		
		while(iterate.hasNext()) {
			String s = iterate.next();
			int k = createStudentAssessmentGC(netId, experimentId, s);
			int l =	createStudentPrelabGC(netId, experimentId, s);
			
			if(k == 0 || l == 0) return 0;					
		}
		
		return 1;
	}
	
	private int createStudentAssessmentGC(String netId, String experimentId, String moduleId) {
		return studentExperiment.createStudentAssessmentGC(netId, experimentId, moduleId);
	}

	private int createStudentPrelabGC(String netId, String experimentId, String moduleId) {
		return studentExperiment.createStudentPrelabGC(netId, experimentId, moduleId);
	}
	
	private int createStudentGCTAComments(String netId, String experimentId) {
		return studentExperiment.createStudentGCTAComments(netId, experimentId);
	}
	
}
