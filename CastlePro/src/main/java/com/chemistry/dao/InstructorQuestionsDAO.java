package com.chemistry.dao;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.chemistry.model.Assignment;
import com.chemistry.model.Module;
import com.chemistry.model.PreLab;
import com.chemistry.questions.BlankQuestion;
import com.chemistry.questions.CheckBoxQuestion;
import com.chemistry.questions.Comparision;
import com.chemistry.questions.DataEntry;
import com.chemistry.questions.TextArea;

@Repository
@Scope("session")
public class InstructorQuestionsDAO extends StoreProcess{

	public Connection testConnection() {
		return connection;
	}
	public int insertModule(Module module)	{

		String sql = "insert into moduledetails(module_name, pdfFile)"
				+ " values(?,?)";

		try {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			InputStream inputStream = module.getFile().getInputStream();

			ps.setString(1, module.getName());
			ps.setBinaryStream(2, inputStream);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return 0;
	}
	
	public int insertExperimentAssignment(Assignment assignment) {
		
		String sql = "insert into assignment(assign_id, mod_id, q_cb_id, "
				+ "q_bla_id, q_txt_id, prelab, q_de_id, q_cp_id) values(?,?,?,?,?, ?,?,?)";

		try {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, assignment.getAssignmentId());
			ps.setInt(2, assignment.getModuleId());
			ps.setInt(3, assignment.getCheckboxId());
			ps.setInt(4, assignment.getBlankId());
			ps.setInt(5, assignment.getTextId());
			ps.setInt(6, assignment.getPrelabId());
			ps.setInt(7, assignment.getDataEntryId());
			ps.setInt(8, assignment.getComparisionId());
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
		
		String sql = "insert into quescheckbox(question, valid_answer, invalid_answer_1, "
				+ "invalid_answer_2, invalid_answer_3, type_ques_no, hint) values(?,?,?,?,?,?, ?)";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, question.getQuestion());
			ps.setString(2, question.getAnswer());
			ps.setString(3, question.getInvalid(0));
			ps.setString(4, question.getInvalid(1));
			ps.setString(5, question.getInvalid(2));
			ps.setString(6, question.getSection());
			ps.setString(7, question.getHint());
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
		
		String sql = "insert into quesblank(question, valid_answer, formula, type_ques_no, hint_1, hint_2, hint_3, ranged, units) "
				+ "values(?,?,?,?,?,?, ?,?,?)";

		try {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, question.getQuestion());
			ps.setString(2, question.getAnswer());
			ps.setString(3, question.getFormula());
			ps.setString(4, question.getSection());
			ps.setString(5, question.getHints(0));
			ps.setString(6, question.getHints(1));
			ps.setString(7, question.getHints(2));
			ps.setString(8, question.getRanged());
			ps.setString(9, question.getUnits());
			
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
	
				
	public int insertDataEntry(DataEntry question) {
		String sql = "insert into quesdataentry(question, units, cols, rows, columns, diff, type_ques_no) "
				+ "values(?,?,?,?,?,?, ?)";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, question.getQuestion());
			ps.setString(2, question.getUnits());
			ps.setInt(3, question.getCols());
			ps.setInt(4, question.getRows());
			ps.setString(5, question.getColumnNames());
			ps.setString(6, question.getDifference());
			ps.setString(7, question.getSection());
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;		
	}

		public int insertTextArea(TextArea question) {
			String sql = "insert into questextarea(question, valid_answer, type_ques_no) "
				+ "values(?,?,?)";
			
			try {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, question.getQuestion());
				ps.setString(2, question.getAnswer());
				ps.setString(3, question.getSection());
				ps.executeUpdate();
				
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next())
					return rs.getInt(1);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return 0;		
		}

		public int insertComparison(Comparision question) {
			String sql = "insert into quescomparision(question, one, two, type_ques_no, hint) "
					+ "values(?,?,?,?, ?)";
			
			try {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, question.getQuestion());
				ps.setString(2, question.getFrom());
				ps.setString(3, question.getWith());
				ps.setString(4, question.getSection());
				ps.setString(5, question.getHint());
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
