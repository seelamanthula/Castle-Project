package com.chemistry.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.chemistry.model.PreLab;
import com.chemistry.questions.BlankQuestion;
import com.chemistry.questions.CheckBoxQuestion;
import com.chemistry.util.QuestionMember;
import com.chemistry.util.QuestionType;

@Repository
//@Scope("prototype")

public class StudentQuestionsDAO extends StoreProcess {
	
	private ResultSet getQuestionsPreparedStatement(String sql) {
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();			
			return rs;			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}

	private ResultSet getResultSetofCheckBoxQuestions(int experimentId, int moduleId) {
		String sql2 = "select *  from QuesCheckBox "
				+ "where QuesCheckBox.q_cb_id in (select q_cb_id "
				+ "from assignment "
				+ "where assignment.mod_id = "+moduleId+" and assignment.assign_id = "+experimentId+")";

		return getQuestionsPreparedStatement(sql2);
	}
	
	public List<CheckBoxQuestion> getCheckBoxQuestions(int experimentId, int moduleId) {
		
		List<CheckBoxQuestion> checkboxList = new ArrayList<CheckBoxQuestion>();
		
		try {
			ResultSet rs = getResultSetofCheckBoxQuestions(experimentId,moduleId);
			while(rs.next()) {
				CheckBoxQuestion checkbox = new CheckBoxQuestion();
				checkbox.setQuestion(rs.getString("question"));
				checkbox.setAnswer(rs.getString("valid_answer"));
				
				checkbox.getInvalidValues().add(rs.getString("invalid_answer_1"));
				checkbox.getInvalidValues().add(rs.getString("invalid_answer_2"));
				checkbox.getInvalidValues().add(rs.getString("invalid_answer_3"));
				checkbox.getInvalidValues().add(rs.getString("valid_answer"));
				
				checkbox.setInvalidSets(0, rs.getString("invalid_answer_1"));
				checkbox.setInvalidSets(1, rs.getString("invalid_answer_2"));
				checkbox.setInvalidSets(2, rs.getString("invalid_answer_3"));
				checkbox.setInvalidSets(3, rs.getString("valid_answer"));
				checkbox.setSection(rs.getString("type_ques_no"));
				checkbox.setQuestionType(QuestionMember.type(QuestionType.CHECKBOX));
				checkboxList.add(checkbox);
			}
			return checkboxList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private ResultSet getResultSetofBlankQuestions(int experimentId, int moduleId) {
		String sql2 = "select * from QuesBlank "
				+ "where QuesBlank.q_bla_id in (select q_bla_id "
				+ "from assignment where assignment.mod_id ="+moduleId+" "
						+ "and assignment.assign_id ="+ experimentId+")";

		return getQuestionsPreparedStatement(sql2);
	}
	
	public List<BlankQuestion> getBlankQuestions(int experimentId, int moduleId) {
		
		List<BlankQuestion> blankList = new ArrayList<BlankQuestion>();
	
		try {
			ResultSet rs = getResultSetofBlankQuestions(experimentId, moduleId);
			while(rs.next()) {
				BlankQuestion blank = new BlankQuestion();
				blank.setQuestion(setQuestion(rs.getString("question")));
				blank.setHintsSet(0, rs.getString("hint_1"));
				blank.setHintsSet(1, rs.getString("hint_2"));
				blank.setHintsSet(2, rs.getString("hint_3"));
				blank.setSection(rs.getString("type_ques_no"));
				blank.setFormula(rs.getString("formula"));
				blank.setRange(rs.getString("range"));
				blank.setAnswer(rs.getString("valid_answer"));
				blank.setQuestionType(QuestionMember.type(QuestionType.BLANK));
				blankList.add(blank);
			}
			return blankList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private ResultSet getResultSetofPreLab(int experimentId, int moduleId) {
		String sql = "select * from prelabDisclaimer "
				+ "where prelabDisclaimer.pre_id in "
				+ "(select prelab from Assignment "
				+ "where Assignment.assign_id="+experimentId+" and Assignment.mod_id="+moduleId+")";
		return getQuestionsPreparedStatement(sql);
	}
	
	public PreLab getPreLabDetails(int experimentId, int moduleId) {
		
		ResultSet rs = getResultSetofPreLab(experimentId, moduleId);
		PreLab prelab = new PreLab();
		
		try {
			if(rs.next()) {
				prelab.setObjective(rs.getString("objective"));
				prelab.setHypothesis(rs.getString("hypothesis"));
				prelab.setVariables(rs.getString("variables"));
				prelab.setExperimental(rs.getString("experimental"));
				prelab.setChemical(rs.getString("chemicals"));
			}
			return prelab;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String setQuestion(String question) {
		return question;
	}
}
