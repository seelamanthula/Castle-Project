package com.chemistry.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.chemistry.model.Concept;
import com.chemistry.model.PreLab;
import com.chemistry.questions.BlankQuestion;
import com.chemistry.questions.CheckBoxQuestion;
import com.chemistry.questions.Comparision;
import com.chemistry.questions.DataEntry;
import com.chemistry.questions.TextArea;
import com.chemistry.util.QuestionMember;
import com.chemistry.util.QuestionType;

@Repository
@Scope("session")
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
		String sql2 = "select *  from quescheckbox "
				+ "where quescheckbox.q_cb_id in (select q_cb_id "
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
				checkbox.setHint(rs.getString("hint"));
				
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
		String sql2 = "select * from quesblank "
				+ "where quesblank.q_bla_id in (select q_bla_id "
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
				blank.setRanged(rs.getString("vrange"));
				blank.setUnits(rs.getString("units"));
				blank.setAnswer(rs.getString("valid_answer"));
				blank.setQuestionType(QuestionMember.type(QuestionType.BLANK));
				blank.setAnswerFromTo(arrangeRange(blank.getAnswer()));
				blankList.add(blank);
			}
			return blankList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	private double[] arrangeRange(String answer) {
		
		StringTokenizer tokenizer = new StringTokenizer(answer);
		double[] fromTo = new double[2];
		int i = 0;
		while(tokenizer.hasMoreTokens()) {
			try {
				fromTo[i] = Double.parseDouble(tokenizer.nextToken());
				i++;
			} catch(NumberFormatException e) {
				
			}
		}
		
		return fromTo;
	}
	private ResultSet getResultSetofPreLab(int experimentId, int moduleId) {
		String sql = "select * from prelabDisclaimer "
				+ "where prelabDisclaimer.pre_id in "
				+ "(select prelab from Assignment "
				+ "where Assignment.assign_id="+experimentId+" and Assignment.mod_id="+moduleId+")";
		
		String sql2 = "select mod.mod_id, mod.module_name, mod.pdffile, pre.objective, pre.hypothesis, pre.variables, pre.experimental, pre.chemicals "+
					"from assignment as assign, prelabdisclaimer as pre, moduledetails as mod "+
					"where assign.prelab = pre.pre_id and assign.mod_id = mod.mod_id and assign.assign_id = "+experimentId+" and assign.mod_id = "+moduleId;

		return getQuestionsPreparedStatement(sql2);
	}
	
	public PreLab getPreLabDetails(int experimentId, int moduleId) {
		
		ResultSet rs = getResultSetofPreLab(experimentId, moduleId);
		PreLab prelab = new PreLab();
		Concept concept = new Concept();
		
		try {
			if(rs.next()) {
				concept.setId(rs.getInt("mod.mod_id"));
				concept.setName(rs.getString("mod.module_name"));
				prelab.setConcept(concept);
				prelab.setObjective(rs.getString("pre.objective"));
				prelab.setHypothesis(rs.getString("pre.hypothesis"));
				prelab.setVariables(rs.getString("pre.variables"));
				prelab.setExperimental(rs.getString("pre.experimental"));
				prelab.setChemical(rs.getString("pre.chemicals"));
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
	
		public List<DataEntry> getDataEntryDetails(int experimentId, int moduleId) {
			List<DataEntry> dataEntry = new ArrayList<DataEntry>();
				ResultSet rs = getResultSetofDataEntryQuestions(experimentId, moduleId);
				try {
					while(rs.next()) {
						DataEntry entry = new DataEntry();
						entry.setQuestion(rs.getString("question"));
						entry.setUnits(rs.getString("units"));
						entry.setCols(rs.getInt("cols"));
						entry.setRows(rs.getInt("rows"));
						entry.setColumnNames(rs.getString("columns"));
						
						entry.setColumns(arrangeColumnNames(entry.getColumnNames()));
						
						entry.setSection(rs.getString("type_ques_no"));
						entry.setQuestionType(QuestionMember.type(QuestionType.DATAENTRY));
						entry.setDiff(arrangeRange(rs.getString("diff")));
						dataEntry.add(entry);
					}
					return dataEntry;
				}catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
			
	private List<String> arrangeColumnNames(String str) {
		
		StringTokenizer token = new StringTokenizer(str, ",");
		List<String> list = new ArrayList<String>();
		
		while(token.hasMoreTokens()) {
			list.add(token.nextToken());
		}
		
		return list;
	}
		
	private ResultSet getResultSetofDataEntryQuestions(int experimentId, int moduleId) {
		String sql2 = "select *  from quesdataentry "
				+ "where quesdataentry.q_de_id in (select q_de_id "
				+ "from assignment "
				+ "where assignment.mod_id = "+moduleId+" and assignment.assign_id = "+experimentId+")";

		return getQuestionsPreparedStatement(sql2);
	}

	private ResultSet getResultSetofTextAreaQuestions(int experimentId, int moduleId) {
		String sql2 = "select *  from questextarea "
				+ "where questextarea.q_txt_id in (select q_txt_id "
				+ "from assignment "
				+ "where assignment.mod_id = "+moduleId+" and assignment.assign_id = "+experimentId+")";

		return getQuestionsPreparedStatement(sql2);
	}

	public List<TextArea> getTextAreaDetails(int experimentId, int moduleId) {
		
		List<TextArea> textArea = new ArrayList<TextArea>();
		ResultSet rs = getResultSetofTextAreaQuestions(experimentId, moduleId);
		try {
			while(rs.next()) {
				TextArea area = new TextArea();
				area.setQuestion(rs.getString("question"));
				area.setAnswer(rs.getString("valid_answer"));
				area.setSection(rs.getString("type_ques_no"));
				area.setQuestionType(QuestionMember.type(QuestionType.TEXT));
				textArea.add(area);			
			}
			return textArea;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ResultSet getResultSetofComparisionQuestions(int experimentId, int moduleId) {
		String sql2 = "select *  from quescomparision "
				+ "where quescomparision.q_cp_id in (select q_cp_id "
				+ "from assignment "
				+ "where assignment.mod_id = "+moduleId+" and assignment.assign_id = "+experimentId+")";

		return getQuestionsPreparedStatement(sql2);
	}

	public List<Comparision> getComparisionQuestionDetails(int experimentId, int moduleId) {
		
		List<Comparision> comparision = new ArrayList<Comparision>();
		ResultSet rs = getResultSetofComparisionQuestions(experimentId, moduleId);
		try {
			while(rs.next()) {
				Comparision area = new Comparision();
				area.setQuestion(rs.getString("question"));
				area.setFrom(rs.getString("comp"));
				area.setWith(rs.getString("comp2"));
				area.setFromValues(arrangeComparisionDoubles(area.getFrom()));
				area.setWithValues(arrangeComparisionDoubles(area.getWith()));
				area.setSection(rs.getString("type_ques_no"));
				area.setSection(rs.getString("hint"));						
				area.setQuestionType(QuestionMember.type(QuestionType.COMPARISION));
				comparision.add(area);			
			}
			return comparision;
		}catch (SQLException e) {
			System.out.println("In Comparision");
			e.printStackTrace();
		}
		return null;
	}

	private List<Double> arrangeComparisionDoubles(String str) {
		
		StringTokenizer token = new StringTokenizer(str, ",");
		List<Double> list = new ArrayList<Double>();
		
		while(token.hasMoreTokens()) {
			list.add(Double.parseDouble(token.nextToken()));
		}
		
		return list;
	}

}
