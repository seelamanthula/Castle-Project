package com.company.dao.gradecenter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.company.dao.StoreHead;
import com.company.model.gradecenter.UpdateScore;
import com.company.model.relation.ModuleDetails;
import com.company.model.student.status.ExperimentDetails;

@Repository
@Scope("session")
public class GradeCenterDAO extends StoreHead {

	private HashMap<String, HashMap<Long, ArrayList<ModuleDetails>>> accessAssessmentPrelab(ResultSet rs) {

		try {
			
			HashMap<String, HashMap<Long, ArrayList<ModuleDetails>>> outerMap = new HashMap<String, HashMap<Long, ArrayList<ModuleDetails>>>();
			HashMap<Long, ArrayList<ModuleDetails>>  iMap = new HashMap<Long, ArrayList<ModuleDetails>>();

			while(rs.next()) {

				ModuleDetails module = new ModuleDetails();				
				module.setId(Long.parseLong(rs.getString("b.module_Id")));
				module.setName(rs.getString("b.module_name"));
				module.setScore(rs.getInt("c.score"));				

				long exp = Long.parseLong(rs.getString("a.id"));

				String netId = rs.getString("netid");
				if(outerMap.containsKey(netId)) {
					iMap = outerMap.get(netId);
					
					if(iMap.containsKey(exp)) {
						ArrayList<ModuleDetails> list = iMap.get(exp);
						list.add(module);						
						iMap.put(exp, list);						
					} else {
						ArrayList<ModuleDetails> list = new ArrayList<ModuleDetails>();
						list.add(module);
						iMap.put(exp, list);	
					}
					outerMap.put(netId, iMap);
				} else {
					iMap = new HashMap<Long, ArrayList<ModuleDetails>>();
					ArrayList<ModuleDetails> list = new ArrayList<ModuleDetails>();
					list.add(module);
					
					iMap.put(exp, list);
					outerMap.put(netId, iMap);
				}				
				
			}
			
			return outerMap;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	
	}
	
	public HashMap<String, HashMap<Long, ArrayList<ModuleDetails>>> getInstructorAssessmentGCDetails() {
		
		String sql = "select a.id, b.module_Id, b.module_name, c.netid, c.score  "
				+ "from experiment_table as a, module_table as b, student_assessment_table as c "
				+ "where a.id = b.experiment_Id and b.module_Id = c.moduleid and a.experiment_status = 'PUBLISH'";
		
		System.out.println(sql);
		ResultSet rs = selectCommand(sql);

		return accessAssessmentPrelab(rs);
	}
	
	public HashMap<String, HashMap<Long, ArrayList<ModuleDetails>>> getInstructorPrelabGCDetails() {
		
		String sql = "select a.id, b.module_Id, b.module_name, c.netid, c.score  "
				+ "from experiment_table as a, module_table as b, student_prelab_table as c "
				+ "where a.id = b.experiment_Id and b.module_Id = c.moduleid and a.experiment_status = 'PUBLISH'";
		
		System.out.println(sql);
		ResultSet rs = selectCommand(sql);

		return accessAssessmentPrelab(rs);
	}
	
	public HashMap<String, ExperimentDetails> getAllExperimentDetails() {
		String sql = "select id,name,exp_duedate from experiment_table "
				+ "where experiment_status = 'PUBLISH'";
		
		System.out.println(sql);
		try {
			ResultSet rs = selectCommand(sql);
			
			HashMap<String, ExperimentDetails> map = new HashMap<String, ExperimentDetails>();

			while(rs.next()) {
				ExperimentDetails eDetails = new ExperimentDetails();
				
				eDetails.setExperimentId(rs.getString("id"));
				eDetails.setExperimentName(rs.getString(2));
				eDetails.setDuedate(rs.getString(3));
				
				map.put(eDetails.getExperimentId(), eDetails);
			}
			
			return map;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int updateAssessmentScore(String netId, String expId, UpdateScore update) {
		

		String sql = "update student_assessment_table set score="+update.getScore()+" "
				+ "where netid='"+netId+"' and expid='"+expId+"' "
						+ "and moduleid= '"+update.getModuleId()+"'";
		
		return updateCommand(sql);
	}
	
	public int updatePrelabScore(String netId, String expId,UpdateScore update) {
		

		String sql = "update student_prelab_table set score="+update.getScore()+" "
				+ "where netid='"+netId+"' and expid='"+expId+"' "
						+ "and moduleid= '"+update.getModuleId()+"'";
		
		return updateCommand(sql);
	}
	
	public int updateTACommentsGC(String netId, String expId, String taComments) {
		
		String sql = "update student_tacomments_table set ta_comments='"+taComments+"' "
				+ "where netid='"+netId+"' and expid='"+expId+"'";
		
		return updateCommand(sql);
	}
	
	private String getAssessmentPrelabDataForPDF(String sql) {
		ResultSet rs = selectCommand(sql);
		try {
			if(rs.next()) {
				String s = rs.getString(1);
				System.out.println("S : "+s);
				String s1 = null;
					if(s != null) s1 = s.replace("\\\"", "\"");
				System.out.println("S1 : "+s1);
				if( s != null) return s;		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getAssessmentDataForPDF(String netId, String expId, String moduleId) {
		
		String sql = "select mixed_data from student_assessment_table "
				+ "where netid='"+netId+"' and expid='"+expId+"' and moduleid='"+moduleId+"'";
		
		return getAssessmentPrelabDataForPDF(sql);
	}
	
	public String getPrelabDataForPDF(String netId, String expId, String moduleId) {
		
		String sql = "select data from student_prelab_table "
				+ "where netid='"+netId+"' and expid='"+expId+"' and moduleid='"+moduleId+"'";
		
		return getAssessmentPrelabDataForPDF(sql);
	}
}
