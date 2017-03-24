package com.company.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.company.model.FileContent;
import com.company.model.QuestionContent;
import com.company.model.relation.ModuleQuestionMaps;
import com.google.gson.Gson;

@Repository
@Scope("session")
public class StoreQuestions extends StoreHead {
	
	public int findQuestionExists(String questionId) {
		
		String sql = "select question_Id from question_table where question_Id='"+questionId+"'";
		
		try {
			
			ResultSet rs = selectCommand(sql);
			if(rs == null) return 0;
			
			if(rs.next()) return 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public ModuleQuestionMaps retrieveAllQuestionListForInstructor(String experimentId) {
	
		String sql2 = "select * from question_table, module_table, experiment_table "
				+ "where question_table.module_Id = module_table.module_Id AND module_table.experiment_Id = experiment_table.id AND experiment_table.id='"+experimentId+"'";
		
		String sql = "select distinct question_table.question_Id, module_table.module_Id, "
				+ "module_table.module_name, question_table.type, question_table.question_data "
				+ "from question_table, module_table "
				+ "where question_table.module_Id = module_table.module_Id AND module_table.experiment_Id = '"+experimentId+"'";
		
		try {
			
			ResultSet rs = selectCommand(sql);
			if(rs == null) return null;
			
			HashMap<Long, String> quesMap = new HashMap<Long, String>();
			HashMap<Long, String> moduleMap = new HashMap<Long, String>();
			
			int k = 1;
			while(rs.next()) {
				
				QuestionContent content = new QuestionContent();
				content.setQuestionId(rs.getString(1));
				content.setModuleId(rs.getString(2));
				content.setType(rs.getString(4));
				content.setQuestionDetails(rs.getString(5));
				
				quesMap.put(Long.parseLong(content.getQuestionId()), content.getRemovedSQLQuestionDetails());
				
				moduleMap.put(Long.parseLong(content.getModuleId()), rs.getString(3));
			}
		
			ModuleQuestionMaps maps = new ModuleQuestionMaps();
			maps.setModuleNames(moduleMap);
			maps.setQuestionsList(quesMap);
			
			return maps;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int findFileExists(String questionId, String type) {

		String sql = "select file_Id from file_table where question_Id='"+questionId+"' and file_type='"+type+"'";	
		System.out.println(sql);
		
		try {
			
			ResultSet rs = selectCommand(sql);
			if(rs.next()) return 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateQuestionContent(QuestionContent content) {
		
		String sql = "update question_table set "
				+ "question_data = '"+content.getSQLQuestionDetails()+"' "
				+ "where question_Id = '"+content.getQuestionId()+"'";
		
		return updateCommand(sql);		
	}

	public int removeQuestion(String questionId) {
	
		String sql = "delete from question_table where question_Id = '"+questionId+"'";
		return deleteCommand(sql);
	}
	
	public int storeQuestionContent(QuestionContent content) {
		String sql = "insert into question_table(question_Id, module_Id, type, question_data) "
				+ "values(\""+content.getQuestionId()+"\",\""+content.getModuleId()+"\",\""+content.getType()+"\",\""+content.getSQLQuestionDetails()+"\")";
		
	//	System.out.println(sql);
		return insertCommand(sql);
	}
	
	public int storeQuestionFiles(HashMap<String, ArrayList<FileContent>> files, String questionId) {
		
		for(String key : files.keySet()) {
			
			int k = 0;
			ArrayList<FileContent> file = files.get(key);
			
			if(findFileExists(questionId, key) == 1)
				k = updateFileContent(questionId, key, new Gson().toJson(file).replace("\"", "\\\""));
			else 
				k = storeSingleFile(questionId, key, new Gson().toJson(file).replace("\"", "\\\""));
			
			if(k == 0)
				return 0;
		}
		return 1;
	}

	public int updateFileContent(String questionId, String key, String value) {
		
		String sql = "update file_table set "
				+ "file_extension = 'up no ext', "
				+ "file_name = 'up no name', "				
				+ "content = '"+value+"' "
				+ "where question_Id = '"+questionId+"' and "
				+ "file_type = '"+key+"'";
		
		return updateCommand(sql);		
	}

	
	public int updateFileContent(FileContent file) {
		
		String sql = "update file_table set "
				+ "file_extension = '"+file.getExtension()+"', "
				+ "file_name = '"+file.getName()+"', "				
				+ "content = '"+file.getFileX()+"' "
				+ "where question_Id = '"+file.getQuestionId()+"' and "
				+ "file_type = '"+file.getType()+"'";
		
		return updateCommand(sql);		
	}
	
	public int storeSingleFile(FileContent file) {	
		String sql = "insert into file_table(question_Id, file_type, file_extension, file_name, content) "
				+ "values(\""+file.getQuestionId()+"\",\""+file.getType()+"\",\""+file.getExtension()+"\",\""+file.getName()+"\",\""+file.getFileX()+"\")";
		
	//	System.out.println(sql);
		return insertCommand(sql);
	}
	
	public int storeSingleFile(String questionId, String key, String value) {	
		String sql = "insert into file_table(question_Id, file_type, file_extension, file_name, content) "
				+ "values(\""+questionId+"\",\""+key+"\",'NO ext','No Name',\""+value+"\")";
		
	//	System.out.println(sql);
		return insertCommand(sql);
	}
}
