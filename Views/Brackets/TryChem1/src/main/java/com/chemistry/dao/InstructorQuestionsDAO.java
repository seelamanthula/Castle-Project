package com.chemistry.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.chemistry.model.Assignment;
import com.chemistry.model.Module;
import com.chemistry.model.PreLab;
import com.chemistry.questions.BlankQuestion;
import com.chemistry.questions.CheckBoxQuestion;

@Repository
//@Scope("prototype")

public class InstructorQuestionsDAO extends StoreProcess{

	public int insertModule(Module module)	{

		String sql = "insert into moduledetails(module_name, pdfFile)"
				+ " values(?,?)";

		try {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, module.getName());
			ps.setBytes(2, module.getFile().getBytes());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return 0;
	}
	
	public int insertExperimentAssignment(Assignment assignment) {
		
		String sql = "insert into Assignment(assign_id, mod_id, q_cb_id, "
				+ "q_bla_id, q_txt_id, prelab) values(?,?,?,?,?, ?)";

		try {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, assignment.getAssignmentId());
			ps.setInt(2, assignment.getModuleId());
			ps.setInt(3, assignment.getCheckboxId());
			ps.setInt(4, assignment.getBlankId());
			ps.setInt(5, assignment.getTextId());
			ps.setInt(6, assignment.getPrelabId());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	public int insertCheckBox(CheckBoxQuestion question) {
		
		String sql = "insert into QuesCheckBox(question, valid_answer, invalid_answer_1, "
				+ "invalid_answer_2, invalid_answer_3, type_ques_no) values(?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, question.getQuestion());
			ps.setString(2, question.getAnswer());
			ps.setString(3, question.getInvalid(0));
			ps.setString(4, question.getInvalid(1));
			ps.setString(5, question.getInvalid(2));
			ps.setString(6, question.getSection());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int insertBlank(BlankQuestion question) {
		
		String sql = "insert into QuesBlank(question, valid_answer, formula, type_ques_no, hint_1, hint_2, hint_3, range) "
				+ "values(?,?,?,?,?,?, ?,?)";

		try {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, question.getQuestion());
			ps.setString(2, question.getAnswer());
			ps.setString(3, question.getFormula());
			ps.setString(4, question.getSection());
			ps.setString(5, question.getHints(0));
			ps.setString(6, question.getHints(1));
			ps.setString(7, question.getHints(2));
			ps.setString(8, question.getRange());
			
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int insertPreLab(PreLab preLab) {
		
		String sql = "insert into prelabDisclaimer(objective, hypothesis, variables, experimental, chemicals) "
				+ "values(?,?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, preLab.getObjective());
			ps.setString(2, preLab.getHypothesis());
			ps.setString(3, preLab.getVariables());
			ps.setString(4, preLab.getExperimental());
			ps.setString(5, preLab.getChemical());			
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
}
