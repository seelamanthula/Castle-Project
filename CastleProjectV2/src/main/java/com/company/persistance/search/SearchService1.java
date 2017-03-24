package com.company.persistance.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.model.gradecenter.GradeTable2;
import com.company.model.gradecenter.GradeTable3;
import com.company.model.gradecenter.GradeTable4;
import com.company.model.registration.StudentProfile;
import com.company.model.relation.ModuleDetails;
import com.company.model.student.status.ExperimentDetails;
import com.google.gson.Gson;

@Service
@Scope("session")
public class SearchService1 extends MainSearch {

	private ArrayList<StudentProfile> studentProfiles = new ArrayList<StudentProfile>();
	private ArrayList<GradeTable3> gt3List = new ArrayList<GradeTable3>();
	private GradeTable4 gradeTable4 = new GradeTable4();
	/* Since Instructor cant change any modifications to studentProfiles and no need to hit
		database for modifications from instructor side, this is temporarily storing to 
		private variable 'studentProfiles'
		*/
	
	 
	private ArrayList<StudentProfile> getAllStudentsPersonalInformation() {
		return authenticate.getAllStudentsPersonalInformation();
	}
	
	public ArrayList<StudentProfile> getAllStudentsPersonalInformationForInstructor() {
		if(this.studentProfiles == null) return null;
		
		if(this.studentProfiles.size() == 0) {
			this.studentProfiles = getAllStudentsPersonalInformation();
		}
		
		return this.studentProfiles;
	}
	
	private HashMap<String, HashMap<Long, ArrayList<ModuleDetails>>> getInstructorAssessmentGCDetails() {
		return gradeCenter.getInstructorAssessmentGCDetails();
	}
	
	private HashMap<String, HashMap<Long, ArrayList<ModuleDetails>>> getInstructorPrelabGCDetails() {
		return gradeCenter.getInstructorPrelabGCDetails();
	}
	
	private HashMap<String, ExperimentDetails> getAllPublishExperimentDetails() {
		return gradeCenter.getAllExperimentDetails();
	}
	
	
	private HashMap<String, HashMap<ExperimentDetails, ArrayList<ModuleDetails>>> iterateAssessmentPrelabStuff(
									HashMap<String, ExperimentDetails> experimentMap,
									HashMap<String, HashMap<Long, ArrayList<ModuleDetails>>> assessmentMap) {

		//HashMap<String, ExperimentDetails> experimentMap = getAllPublishExperimentDetails();
		
		HashMap<String, HashMap<ExperimentDetails, ArrayList<ModuleDetails>>> mainMap = 
				new	HashMap<String, HashMap<ExperimentDetails, ArrayList<ModuleDetails>>>();
	
		HashMap<String, HashMap<Long, ArrayList<ModuleDetails>>> map = assessmentMap;
		for (Map.Entry<String, HashMap<Long, ArrayList<ModuleDetails>>> entry : map.entrySet())
		{
			String netId = entry.getKey();
			
		    HashMap<ExperimentDetails, ArrayList<ModuleDetails>> eMap = 
		    		new HashMap<ExperimentDetails, ArrayList<ModuleDetails>>();
		    
		    HashMap<Long, ArrayList<ModuleDetails>> innerMap = entry.getValue();
		   
		    for (Map.Entry<Long, ArrayList<ModuleDetails>> inner : innerMap.entrySet())
			{
		    	String key = inner.getKey()+"";
		    	ArrayList<ModuleDetails> list = inner.getValue();
		    	ExperimentDetails eDetails = null;
		    	if(experimentMap.containsKey(key)) {
		    		eDetails = experimentMap.get(key);
		    	}
		    	
		    	eMap.put(eDetails, list);
			}
		    
		    mainMap.put(netId, eMap);
		}
		
		return mainMap;
	}
	
	
	private String searchTAName(ArrayList<StudentProfile> list, String netId) {
		
		if(list == null) return null;
		Iterator<StudentProfile> iterate = list.iterator();
		
		while(iterate.hasNext()) {
			StudentProfile profile = iterate.next();
			if(profile.getStudentDetails().getLogin().getNetId().equalsIgnoreCase(netId))
				return profile.getClassInformation().getTaDetails().getTaName();
		}
		
		System.out.println("Came out pf loop");
		return null;
	}
	
	private ArrayList<GradeTable3> iterateAssessmentPrelabStuff(
			HashMap<String, ExperimentDetails> experimentMap,
			HashMap<String, HashMap<Long, ArrayList<ModuleDetails>>> assessmentMap,
			HashMap<String, HashMap<Long, ArrayList<ModuleDetails>>> prelabMap) {

		//HashMap<String, ExperimentDetails> experimentMap = getAllPublishExperimentDetails();
		
		if(this.studentProfiles == null) return null;
		
		if(this.studentProfiles.size() == 0) {
			this.studentProfiles = getAllStudentsPersonalInformation();
		}
			
		ArrayList<GradeTable3> mainList = new ArrayList<GradeTable3> ();

		HashMap<String, HashMap<Long, ArrayList<ModuleDetails>>> aMap = assessmentMap;
		HashMap<String, HashMap<Long, ArrayList<ModuleDetails>>> pMap = prelabMap;

		for (Map.Entry<String, HashMap<Long, ArrayList<ModuleDetails>>> entry : aMap.entrySet())	{
				
			String netId = entry.getKey();
			String taName = searchTAName(this.studentProfiles, netId);
			
			ArrayList<GradeTable2> gt2List = new ArrayList<GradeTable2>();

			HashMap<Long, ArrayList<ModuleDetails>> innerAMap = entry.getValue();
			HashMap<Long, ArrayList<ModuleDetails>> innerPMap = pMap.get(netId);
			
			int stuAssessmentPoints = 0;
			int stuPrelabPoints = 0;
			
			for (Map.Entry<Long, ArrayList<ModuleDetails>> inner : innerAMap.entrySet())	{
				
				String key = inner.getKey()+"";
				ArrayList<ModuleDetails> aList = inner.getValue();
				ArrayList<ModuleDetails> pList = innerPMap.get(inner.getKey());

				ExperimentDetails eDetails = experimentMap.get(key);
				
				GradeTable2 gt2 = new GradeTable2();
				gt2.setExperiment(eDetails);
				gt2.setAssessmentList(aList);
				gt2.setPrelabList(pList);
				int a = iterateModuleList(aList);
				int p = iterateModuleList(pList);

				gt2.setAssessmentPoints(a);
				gt2.setPrelabPoints(p);				

				stuAssessmentPoints += a;
				stuPrelabPoints += p;

				gt2List.add(gt2);
			}

			GradeTable3 gt3 = new GradeTable3();
			gt3.setNetId(netId);
			gt3.setTaName(taName);
			gt3.setStudents(gt2List);
			gt3.setStudentAssessmentPoints(stuAssessmentPoints);
			gt3.setStudentPrelabPoints(stuPrelabPoints);
			
			mainList.add(gt3);
		}

		return mainList;
	}

	private int iterateModuleList(ArrayList<ModuleDetails> list) {
		
		Iterator<ModuleDetails> iterate = list.iterator();
		
		int total = 0;
		while(iterate.hasNext()) {
			ModuleDetails module = iterate.next();
			total += module.getScore();
		}
		
		return total;
	}
	
	private GradeTable4 iterateGradeTable() {
		
		HashMap<String, ExperimentDetails> experimentMap = getAllPublishExperimentDetails();
		HashMap<String, HashMap<Long, ArrayList<ModuleDetails>>> assessmentMap = getInstructorAssessmentGCDetails();
		HashMap<String, HashMap<Long, ArrayList<ModuleDetails>>> prelabMap = getInstructorPrelabGCDetails();
		
		if(assessmentMap == null || prelabMap == null || experimentMap == null) return null;
		
		ArrayList<GradeTable3> gt3List = iterateAssessmentPrelabStuff(experimentMap, assessmentMap, prelabMap);
		if(gt3List == null) return null;
		
		
		ArrayList<ExperimentDetails> expList = new ArrayList<ExperimentDetails>(experimentMap.values());
		this.gt3List = gt3List;

		GradeTable4 gt4 = new GradeTable4();
		gt4.setPublishedExps(expList);
		gt4.setStudentList(gt3List);
		
		this.gradeTable4 = gt4;
		
		return gt4;
	}
	
	public GradeTable4 getAllStudentGradeCenter() {
	// 	Need to test if there are no students but published experiments
		// Students without pushed experiments
		// No Students No published Experiments
		return iterateGradeTable();
	}
	
}
