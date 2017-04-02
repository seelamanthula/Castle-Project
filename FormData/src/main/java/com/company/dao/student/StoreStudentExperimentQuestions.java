package com.company.dao.student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.company.dao.StoreHead;
import com.company.model.relation.ModuleDetails;
import com.company.model.student.StudentAssessmentModule;
import com.company.model.student.StudentExperimentModule;
import com.company.model.student.StudentPrelab;
import com.company.model.student.module.EachModule;
import com.company.model.student.status.ExperimentDetails;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Repository
@Scope("session")
public class StoreStudentExperimentQuestions extends StoreHead {

	public ModuleDetails getModuleQuestionIds(String moduleId) {
		
		String sql = "select a.question_Id, b.module_name "
				+ "from question_table as a, module_table as b "
				+ "where a.module_Id=b.module_Id and a.module_Id = '"+moduleId+"'";

		System.out.println(sql);

		ArrayList<String> list = new ArrayList<String>();
		String name = "";
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 
			
			int k = 0;
			while(rs.next()) {
				name = rs.getString(2);
				list.add(rs.getString(1));
				k = 1;
			}

			if(k == 0) return null;
			
			ModuleDetails details = new ModuleDetails();
			details.setId(Long.parseLong(moduleId));
			details.setName(name);
			details.setQuestionIds(list);
			
			return details;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {				
				if(rs != null) rs.close();
				if(state != null) state.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}
	
	public String getExperimentQuestion(String questionId) {
		String sql = "select question_data from question_table "
				+ "where question_Id='"+questionId+"'";

		System.out.println(sql);

		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 

			while(rs.next()) {
				String s = rs.getString(1);
				return s;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {				
				if(rs != null) rs.close();
				if(state != null) state.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public ExperimentDetails getExperimentContent(String experimentId) {
	
		String sql = "select id,name, content,exp_duedate from experiment_table "
				+ "where id='"+experimentId+"'";

		System.out.println(sql);

		ExperimentDetails details = new ExperimentDetails();
		
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 

			while(rs.next()) {
				
				details.setExperimentId(rs.getString(1));
				details.setExperimentName(rs.getString(2));
				String s = rs.getString(3).replace( "\\\"", "\"");				
				details.setPdf2(s);
				details.setDuedate(rs.getString(4));
				//System.out.println(details.getPdf());
				details.setExperimentId(experimentId);
			}
			
			return details;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				if(rs != null) rs.close();
				if(state != null) state.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public int updateStudentAssessmentGC(StudentAssessmentModule assessment) {

		String sql = "update student_assessment_table set "
				+ "data = '"+assessment.getSQLData()+"', "
				+ "ques_data = '"+assessment.getSQLQuesData()+"', "	
				+ "mixed_data = '"+assessment.getSQLMixData()+"', "	
				+ "score = "+assessment.getScore()+" "
				+ "where netid = '"+assessment.getStudentExperimentModule().getNetId()+"' and "
					  + "expid = '"+assessment.getStudentExperimentModule().getExperimentId()+"' and "
					  	+ "moduleid='"+assessment.getStudentExperimentModule().getModuleId()+"'";
		
		return updateCommand(sql);
	}
	
	public StudentAssessmentModule getStudentAssessmentGC(StudentExperimentModule experimentModule) {
		
		String sql = "select mixed_data from student_assessment_table "
				+ "where netid='"+experimentModule.getNetId()+"' and "
				+ "expid='"+experimentModule.getExperimentId()+"' and "
				+ "moduleid='"+experimentModule.getModuleId()+"'";	
		
		System.out.println(sql);
		StudentAssessmentModule assessment = new StudentAssessmentModule();

		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 
			
			assessment.setStudentExperimentModule(experimentModule);
			
			if(rs.next()) {
				String s = rs.getString(1);
				if(s == null) {
					return null;
				}
				assessment.setmData(rs.getString(1).replace( "\\\"", "\"").replace("\n","\\n"));
				return assessment;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {				
				if(rs != null) rs.close();
				if(state != null) state.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public EachModule getStudentPrelabInformation(StudentExperimentModule experimentModule) {
		String sql = "select a.id, a.name, a.exp_duedate, b.module_Id, b.module_name "
				+ "from experiment_table as a, module_table as b "
				+ "where a.id = b.experiment_Id and b.module_Id = '"+experimentModule.getModuleId()+"'";
		
		System.out.println(sql);
		
		ExperimentDetails eDetails = new ExperimentDetails();
		ModuleDetails mDetails = new ModuleDetails();
		EachModule each = new EachModule();
		int k = 0;
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 
			
			while(rs.next()) {
				k = 1;
				eDetails.setExperimentId(rs.getString(1));
				eDetails.setExperimentName(rs.getString(2));
				eDetails.setDuedate(rs.getString(3));				
				mDetails.setId(rs.getLong(4));
				mDetails.setName(rs.getString(5));
				each.setExperiment(eDetails);
				each.setModule(mDetails);
			}
			if(k == 0) return null;
			return each;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				if(rs != null) rs.close();
				if(state != null) state.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	public String getStudentPrelabDataGC(StudentExperimentModule experimentModule) {
		
		String sql = "select data from student_prelab_table "
				+ "where netid='"+experimentModule.getNetId()+"' and "
				+ "expid='"+experimentModule.getExperimentId()+"' and "
				+ "moduleid='"+experimentModule.getModuleId()+"'";	
			
		System.out.println(sql);
		
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 
			
			if(rs.next()) {
				String s = rs.getString(1);
				if(s == null) return null;
				
				return s.replace("\\\"", "\"").replace("\n","\\n");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {				
				if(rs != null) rs.close();
				if(state != null) state.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public int updateStudentPrelabGC(StudentPrelab prelab) {

		String sql = "update student_prelab_table set "
				+ "data = '"+prelab.getSQLWrittenText()+"', "
				+ "pdf = '"+prelab.getSQLPdfContent()+"', "
				+ "tac_score = '"+prelab.getSQLTa_s_c_data()+"' "
				+ "where netid = '"+prelab.getStudentExperimentModule().getNetId()+"' and "
					  + "expid = '"+prelab.getStudentExperimentModule().getExperimentId()+"' and "
					  	+ "moduleid='"+prelab.getStudentExperimentModule().getModuleId()+"'";
		
		return updateCommand(sql);

	}
	
	public int updateStudentPrelabDataGC(StudentPrelab prelab) {

		String sql = "update student_prelab_table set "
				+ "data = '"+prelab.getSQLWrittenText()+"' "
				+ "where netid = '"+prelab.getStudentExperimentModule().getNetId()+"' and "
					  + "expid = '"+prelab.getStudentExperimentModule().getExperimentId()+"' and "
					  	+ "moduleid='"+prelab.getStudentExperimentModule().getModuleId()+"'";
		
		return updateCommand(sql);

	}
	
	public int updateStudentPrelabPDFGC(StudentPrelab prelab) {

		String sql = "update student_prelab_table set "
				+ "pdf = '"+prelab.getSQLPdfContent()+"' "
				+ "where netid = '"+prelab.getStudentExperimentModule().getNetId()+"' and "
					  + "expid = '"+prelab.getStudentExperimentModule().getExperimentId()+"' and "
					  	+ "moduleid='"+prelab.getStudentExperimentModule().getModuleId()+"'";
		
		return updateCommand(sql);

	}
	
	public int updateStudentPrelabTA_InfoGC(StudentPrelab prelab) {

		String sql = "update student_prelab_table set "
				+ "tac_score = '"+prelab.getSQLTa_s_c_data()+"' "
				+ "where netid = '"+prelab.getStudentExperimentModule().getNetId()+"' and "
					  + "expid = '"+prelab.getStudentExperimentModule().getExperimentId()+"' and "
					  	+ "moduleid='"+prelab.getStudentExperimentModule().getModuleId()+"'";
		
		return updateCommand(sql);

	}
}
