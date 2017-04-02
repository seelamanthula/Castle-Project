package com.company.persistance.search;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.model.gradecenter.UpdateAPListsTAComments;
import com.company.model.gradecenter.UpdateScore;

@Service
@Scope("session")
public class SearchService2 extends MainSearch {

	private int updateAssessmentScore(String netId, String expId, UpdateScore update) {
		return gradeCenter.updateAssessmentScore(netId, expId, update);
	}
	
	private int updatePrelabScore(String netId, String expId, UpdateScore update) {
		return gradeCenter.updatePrelabScore(netId, expId,update);
	}
	
	private int iterateAssessmentArrayLists(String netId, String expId, ArrayList<UpdateScore> list) {
		
		Iterator<UpdateScore> iterate = list.iterator();
		int k = 0;
		while(iterate.hasNext()) {
			UpdateScore score = iterate.next();
			if(updateAssessmentScore(netId, expId, score) == 0) k = 1;
		}
		
		if(k == 1) return 0;
		
		return 1;
	}
	
	private int iteratePrelabArrayLists(String netId, String expId, ArrayList<UpdateScore> list) {
		
		int k = 0;
		Iterator<UpdateScore> iterate = list.iterator();
		while(iterate.hasNext()) {
			UpdateScore score = iterate.next();
			if(updatePrelabScore(netId, expId, score) == 0) k = 1;
		}	
		
		if(k == 1) return 0;
		
		return 1;
	}
	
	private int updateStudentTAComments(String netId, String expId, String taComments) {
		return gradeCenter.updateTACommentsGC(netId, expId, taComments);
	}
	public int updateUpdateAPListsTAComments(UpdateAPListsTAComments aplComments) {
	
		UpdateAPListsTAComments ulcomments = aplComments;
		int k = iterateAssessmentArrayLists(ulcomments.getNetId(), ulcomments.getExperimentId(), ulcomments.getAssessmentList());
		int l = iteratePrelabArrayLists(ulcomments.getNetId(), ulcomments.getExperimentId(), ulcomments.getPrelabList());
		int m = updateStudentTAComments(ulcomments.getNetId(), ulcomments.getExperimentId(), ulcomments.get_SQL_TaComments());
		
		if(k==1 && l== 1 && m == 1) return 1;
		
		return 0;
	}
	
	public String getAssessmentDataForPDF(String netId, String expId, String moduleId) {
		return gradeCenter.getAssessmentDataForPDF(netId, expId, moduleId);
	}
	
	public String getPrelabDataForPDF(String netId, String expId, String moduleId) {
		return gradeCenter.getPrelabDataForPDF(netId, expId, moduleId);
	}
	
	
}
