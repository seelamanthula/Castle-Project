package com.company.dao;

import java.sql.Connection;
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
		
		return checkSQLSelectStatement(sql);
	}
	
	public int storeNewExperimentID(String id) {
		String sql = "insert into experiment_table(id, experiment_status) "
				+ "values('"+id+"', 'DRAFT')";
		
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
		System.out.println(sql);
	    
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		ExperimentContent content = null;
		
		try {
			 
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 
			
			if(rs.next()) {
				
				content = new ExperimentContent();
				content.setExperimentId(Long.parseLong(experimentId));
				content.setName(rs.getString(2));
				content.setModuleOrder(rs.getString(4));
				content.setStatus(rs.getString(5));
				content.setDueDate(rs.getString(6));
				content.setOrderList(rs.getString(7));
					
				return content;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}  finally {
			try {				
				if(rs != null) rs.close();
				if(state != null) state.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return content;
	}
	
	public int updateExperimentFile(String id, String content) {
		String sql = "update experiment_table set "
				+ "content = '"+content+"' "
				+ "where id = '"+id+"'";
		
		return updateCommand(sql);
	}
	
	public int removeModuleFromModuleTable(String moduleId) {		
		String sql = "delete from module_table where module_Id = '"+moduleId+"'";
		
		return deleteCommand(sql);
	}
	
	public int removeQuestionsInModule(String moduleId) {
		String sql = "delete from question_table where module_Id = '"+moduleId+"'";
		
		return deleteCommand(sql);
	}

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
		
		return updateCommand(sql);
	}
		
	public int findExperimentModule(String moduleId) {

		String sql = "select module_Id from module_table where module_Id='"+moduleId+"'";	
		
		System.out.println(sql);
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		int k = 0;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 
			
			if(rs.next()) return k = 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}  finally {
			try {				
				if(rs != null) rs.close();
				if(state != null) state.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		}
		return k;
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
		int k = checkSQLSelectStatement(sql);
		return k;
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
		
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		String s = null;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 
			
			if(rs.next()) s = rs.getString(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}  finally {
			try {				
				if(rs != null) rs.close();
				if(state != null) state.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return s;
	}
	
	public int updateExperimentDueDate(String experimentId, String dueDate) {
		String sql = "update experiment_table set "
				+ "exp_duedate = '"+dueDate+"' "
				+ "where id = '"+experimentId+"'";
		return updateCommand(sql);
	}
	
	public int removeExperimentFromExperimentTable(String experimentId) {
		String sql = "delete from experiment_table where id = '"+experimentId+"'";
		
		return deleteCommand(sql);
	}
	
	public int removeAllModulesInExperiment(String experimentId) {
		String sql = "delete from module_table where experiment_Id = '"+experimentId+"'";
		
		return deleteCommand(sql);
	}
	
	public List<ExperimentStatus> getAllExperimentDetailsForInstructor() { 
		String sql = "select id,name,experiment_status,exp_duedate from experiment_table where experiment_status!='DISCARD'";	
		System.out.println(sql);
		
		List<ExperimentStatus> list = new ArrayList<ExperimentStatus>();
		
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 
			
			while(rs.next()) {
				ExperimentStatus cont = new ExperimentStatus();
				cont.setExperimentId(rs.getString(1));
				cont.setExperimentName(rs.getString(2));
				cont.setStatus(rs.getString(3));
				cont.setDueDate(rs.getString(4));
				list.add(cont);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			try {				
				if(rs != null) rs.close();
				if(state != null) state.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return list;
			}
		}
		return list;
	}
	
	public List<ExperimentContent> getAllExperimentDetailsForStudent() { 
		String sql = "select * from experiment_table where experiment_status<>'DISCARD'";	
		System.out.println(sql);
		
		List<ExperimentContent> list = new ArrayList<ExperimentContent>();
		
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			
			while(rs.next()) {
				ExperimentContent cont = new ExperimentContent();
				cont.setName(rs.getString(2));
				cont.setExperimentId(Long.parseLong(rs.getString(1)));
				list.add(cont);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			try {				
				if(rs != null) rs.close();
				if(state != null) state.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return list;
			}
		}
		return list;
	}
	
}
