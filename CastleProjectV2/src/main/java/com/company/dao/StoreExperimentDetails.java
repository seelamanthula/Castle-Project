package com.company.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.company.model.ExperimentContent;
import com.company.model.QuestionContent;
import com.company.model.student.status.ExperimentStatus;

@Repository
@Scope("session")
public class StoreExperimentDetails extends StoreHead {

	public int findExperimentID(String experimentId) {

		String sql = "select id from experiment_table where id='"+experimentId+"'";	
		System.out.println(sql);
		
		return checkSQLSelectStatement(sql);
	}
	
	public int storeNewExperimentID(String id) {
		String sql = "insert into experiment_table(id, experiment_status) "
				+ "values('"+id+"', 'DRAFT')";
		System.out.println(sql);
		
		return updateCommand(sql);
	}
	
	public int updateExperimentName(String id, String name) {
		
		String sql = "update experiment_table set "
				+ "name = '"+name+"' "
				+ "where id = '"+id+"'";
	
		return updateCommand(sql);
	}
	
	public ExperimentContent getAllSetsFromExperimentTable(String experimentId) {
		
		String sql = "select * from experiment_table where id='"+experimentId+"'";
	    
		try {
			 
			ResultSet rs = selectCommand(sql);
			
			if(rs.next()) {
				
				ExperimentContent content = new ExperimentContent();
				content.setExperimentId(Long.parseLong(experimentId));
				content.setName(rs.getString(2));
				content.setModuleOrder(rs.getString(4));
				content.setStatus(rs.getString(5));
				content.setDueDate(rs.getString(6));
				content.setOrderList(rs.getString(7));
			//	content.setModuleNamesList(rs.getString(8));
//				content.setContent(rs.getString(3).replace( "\\\"", "\""));
					
				return content;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int updateExperimentFile(String id, String content) {
		String sql = "update experiment_table set "
				+ "content = '"+content+"' "
				+ "where id = '"+id+"'";

		return updateCommand(sql);
	}
	
	public int removeModuleFromModuleTable(String moduleId) {
		
		String sql = "delete from module_table where module_Id = '"+moduleId+"'";
		System.out.println(sql);
		
		return deleteCommand(sql);
	}
	
	public int removeQuestionsInModule(String moduleId) {
		String sql = "delete from question_table where module_Id = '"+moduleId+"'";
		
		System.out.println(sql);
		return deleteCommand(sql);
	}
/*	
	public int updateExperimentOrderList(String id, String orderList) {
		
		String sql = "update experiment_table set "
				+ "content = '"+orderList+"' "
				+ "where id = '"+id+"'";
		
		return updateCommand(sql);
	}*/
	
	public int updateExperimentModuleList(String id, String moduleList, String orderList, String moduleNamesList) {
		
		String sql = "update experiment_table set "
				+ "module_order = '"+moduleList+"', "
				+ "order_list = '"+orderList+"', "
				+ "module_name_list = '"+moduleNamesList.replace("\\\"", "\"")+"' "
				+ "where id = '"+id+"'";
		
		return updateCommand(sql);
	}
	
	public int storeNewModuleID(String moduleId, String experimentId) {
		String sql = "insert into module_table(module_Id, experiment_Id, module_name) "
				+ "values('"+moduleId+"','"+experimentId+"', 'Not Inserted')";
		System.out.println(sql);
		
		return updateCommand(sql);
	}
		
	public int findExperimentModule(String moduleId) {

		String sql = "select module_Id from module_table where module_Id='"+moduleId+"'";	
		System.out.println(sql);
		
		try {
			
			ResultSet rs = selectCommand(sql);
			if(rs.next()) return 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int updateModuleName(String moduleId, String experimentId, String name) {
		
		String sql = "update module_table set "
				+ "module_name = '"+name+"' "
				+ "where module_Id = '"+moduleId+"' and "
						+ "experiment_Id = '"+experimentId+"'";
	
		return updateCommand(sql);
	}
	
	public int findExperimentFileExists(String experimentId) {

		String sql = "select content from experiment_table where id='"+experimentId+"'";	
		System.out.println(sql);
		
		try {
			
			ResultSet rs = selectCommand(sql);
			if(rs.next()) {
				
				String s = rs.getString(1);
				System.out.println("File Content : "+s);
				
				if(s != null)	return 1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int actionButton(String experimentId, String status) {
		String sql = "update experiment_table set "
				+ "experiment_status = '"+status+"' "
				+ "where id = '"+experimentId+"'";
		
		return updateCommand(sql);
	}
	
	public String getAllModules(String experimentId) {
		String sql = "select module_order from experiment_table where id='"+experimentId+"'";	
		System.out.println(sql);
		
		try {
			
			ResultSet rs = selectCommand(sql);
			if(rs.next()) return rs.getString(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int updateExperimentDueDate(String experimentId, String dueDate) {
		String sql = "update experiment_table set "
				+ "exp_duedate = '"+dueDate+"' "
				+ "where id = '"+experimentId+"'";
		
		return updateCommand(sql);
	}
	
	public int removeExperimentFromExperimentTable(String experimentId) {
		String sql = "delete from experiment_table where id = '"+experimentId+"'";
		
		System.out.println(sql);
		return deleteCommand(sql);
	}
	
	public int removeAllModulesInExperiment(String experimentId) {
		String sql = "delete from module_table where experiment_Id = '"+experimentId+"'";
		
		System.out.println(sql);
		return deleteCommand(sql);
	}
	
	public List<ExperimentStatus> getAllExperimentDetailsForInstructor() { 
		String sql = "select id,name,experiment_status,exp_duedate from experiment_table where experiment_status!='DISCARD'";	
		System.out.println(sql);
		
		List<ExperimentStatus> list = new ArrayList<ExperimentStatus>();
		
		try {
			
			ResultSet rs = selectCommand(sql);
			while(rs.next()) {
				ExperimentStatus cont = new ExperimentStatus();
				cont.setExperimentId(rs.getString(1));
				cont.setExperimentName(rs.getString(2));
				cont.setStatus(rs.getString(3));
				cont.setDueDate(rs.getString(4));
				list.add(cont);
			}
			
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<ExperimentContent> getAllExperimentDetailsForStudent() { 
		String sql = "select * from experiment_table where experiment_status<>'DISCARD'";	
		System.out.println(sql);
		
		List<ExperimentContent> list = new ArrayList<ExperimentContent>();
		
		try {
			
			ResultSet rs = selectCommand(sql);
			while(rs.next()) {
				ExperimentContent cont = new ExperimentContent();
				cont.setName(rs.getString(2));
			//	cont.setModuleOrder(rs.getString(4));
			//	cont.setContent(rs.getString(3));
				cont.setExperimentId(Long.parseLong(rs.getString(1)));
				list.add(cont);
			}
			
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
