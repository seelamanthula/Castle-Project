package com.company.dao.student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.company.dao.StoreHead;
import com.company.model.relation.ModuleDetails;
import com.company.model.student.StudentAssessmentModule;
import com.company.model.student.StudentExperimentModule;
import com.company.model.student.StudentPrelab;
import com.company.model.student.status.ExperimentModuleDetails;
import com.company.model.student.status.ExperimentStatus;

@Repository
@Scope("session")
public class StoreStudentExperiment extends StoreHead {

	public int createStudentAssessmentGC(String netId, String experimentId, String moduleId) {
		String sql = "insert into student_assessment_table(netid, expid, moduleid, score) "
				+ "values('"+netId+"','"+experimentId+"','"+moduleId+"', 0)";
	
		System.out.println(sql);
		return updateCommand(sql);
	}

	public int createStudentPrelabGC(String netId, String experimentId, String moduleId) {
		String sql = "insert into student_prelab_table(netid, expid, moduleid, score) "
				+ "values('"+netId+"','"+experimentId+"','"+moduleId+"', 0)";
	
		System.out.println(sql);		
		return updateCommand(sql);
	}
	
	public int createStudentGCTAComments(String netId, String experimentId) {
		String sql = "insert into student_tacomments_table(netid, expid) "
				+ "values('"+netId+"','"+experimentId+"')";
	
		System.out.println(sql);		
		return updateCommand(sql);
	}
	
	public ArrayList<String> checkGetExperimentsStudentGradeCenter(String netId) {
		/*
		 * Performing this operation only on 'student_assessment_table' not
		 * on 'student_prelab_table'
		 */
		
		String sql = "select distinct a.id "
				+ "from experiment_table as a "
				+ "where a.experiment_status='PUBLISH' and "
				+ "a.id NOT IN (select b.expid "
				+ "from student_assessment_table as b "
				+ "where b.netid='"+netId+"')";

		System.out.println(sql);
		ArrayList<String> list = new ArrayList<String>();

		try {

			ResultSet rs = selectCommand(sql);
			while(rs.next()) {
				String s = rs.getString(1);
				list.add(s);
			}
			
			if(list.size() > 0) return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ArrayList<String>();
	}

	public ArrayList<String> getModulesOfExperiment(String experimentId) {
		String sql = "select module_Id from module_table "
				+ "where experiment_Id='"+experimentId+"'";
		
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			ResultSet rs = selectCommand(sql);
			while(rs.next()) {			
				list.add(rs.getString(1));			
			}
			if(list.size() > 0) return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
		return null;
	}

	public ExperimentModuleDetails getStudentExperimentAssessmentModulesScore(String netId, String experimentId) {
		
		String sql = "select distinct b.moduleid, a.module_name, b.score, c.name "
				+ "from student_assessment_table as b, module_table as a, experiment_table as c "
				+ "where a.module_Id = b.moduleid and c.id = a.experiment_Id "
				+ "and b.expid='"+experimentId+"' "
				+ "and b.netid='"+netId+"'";
		
		ArrayList<ModuleDetails> list = new ArrayList<ModuleDetails>();
		ExperimentModuleDetails emDetails = new ExperimentModuleDetails();
		
		try {
			ResultSet rs = selectCommand(sql);
			
			String experimentName = "";
			while(rs.next()) {
				
				ModuleDetails details = new ModuleDetails();
				details.setId(Long.parseLong(rs.getString(1)));
				details.setName(rs.getString(2));
				details.setScore(rs.getInt(3));
				
				list.add(details);
				experimentName = rs.getString(4);
			}
			
			emDetails.setExperimentName(experimentName);
			emDetails.setExperimentId(experimentId);
			emDetails.setModules(list);
			emDetails.setType("Assessment");
			emDetails.setNetId(netId);
			
			return emDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ExperimentModuleDetails getStudentExperimentPrelabModulesScore(String netId, String experimentId) {
		
		String sql = "select distinct b.moduleid, a.module_name, b.score, c.name, c.exp_duedate "
				+ "from student_prelab_table as b, module_table as a, experiment_table as c "
				+ "where a.module_Id = b.moduleid and c.id = a.experiment_Id "
				+ "and b.expid='"+experimentId+"' "
				+ "and b.netid='"+netId+"'";
		
		ArrayList<ModuleDetails> list = new ArrayList<ModuleDetails>();
		ExperimentModuleDetails emDetails = new ExperimentModuleDetails();
		
		try {
			ResultSet rs = selectCommand(sql);
			
			String experimentName = "";
			while(rs.next()) {
				
				ModuleDetails details = new ModuleDetails();
				details.setId(Long.parseLong(rs.getString(1)));
				details.setName(rs.getString(2));
				details.setScore(rs.getInt(3));
				
				list.add(details);
				experimentName = rs.getString(4);
				emDetails.setDuedate(rs.getString(5));

			}
			
			emDetails.setExperimentName(experimentName);
			emDetails.setExperimentId(experimentId);
			emDetails.setModules(list);
			emDetails.setType("Prelab");
			emDetails.setNetId(netId);
			return emDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	// TA comments on Student Assignment

	public Map<String, String> getStudentTAComments(String netId) {
		
		String sql = "select expid, ta_comments "
				+ "from student_tacomments_table as a, experiment_table as b "
				+ "where a.expid = b.id and a.netid = '"+netId+"'";
		
		System.out.println(sql);
		
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			ResultSet rs = selectCommand(sql);
			while(rs.next()) {
				String e = rs.getString(1);				
				String tac = rs.getString(2);
				if(tac != null) tac = tac.replace("\n","\\n");
				map.put(e, tac);
			}
			
			return map;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	// Experiment Status with TA comments from student_tacomments_table

	public Map<String, ExperimentStatus> getStudentAssessmentStatus(String netId) {
		
		String sql = "select distinct expid, a.name, sum(b.score), a.exp_duedate "
				+ "from student_assessment_table as b, experiment_table as a "
				+ "where a.id = b.expid and a.experiment_status='PUBLISH' "
				+ "and b.netid='"+netId+"' "
				+ "group by b.expid";
		
	/*	String sql = "select distinct b.expid, a.name, sum(b.score), a.exp_duedate, c.ta_comments "
				+ "from student_assessment_table as b, experiment_table as a,   student_tacomments_table as c "
				+ "where a.id = b.expid and a.id=c.expid and a.experiment_status='PUBLISH' "
				+ "and b.netid='"+netId+"' group by b.expid";
*/		
		Map<String, ExperimentStatus> map = new HashMap<String, ExperimentStatus>();
		
		try {
			ResultSet rs = selectCommand(sql);
			while(rs.next()) {
				ExperimentStatus status = new ExperimentStatus();
				status.setExperimentId(rs.getString(1));
				status.setExperimentName(rs.getString(2));
				status.setAssessmentScore(rs.getInt(3));
				status.setDueDate(rs.getString(4));
			//	String s = rs.getString(5);
			//	if(s != null) s = s.replace("\n","\\n");
			//	status.setTaComments(s);
				map.put(rs.getString(1), status);
			}
			
			return map;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Map<String, Integer> getStudentPrelabStatus(String netId) {
		
		String sql = "select distinct a.id, sum(c.score) "
				+ "from experiment_table as a, student_prelab_table as c "
				+ "where c.expid = a.id and a.experiment_status='PUBLISH' "
				+ "and c.netid='"+netId+"' "
				+ "group by c.expid";
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		try {
			ResultSet rs = selectCommand(sql);
			while(rs.next()) {
				String s = rs.getString(1);
				int i = rs.getInt(2);
				
				map.put(rs.getString(1), i);
			}
			
			return map;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	/*public int checkStudentAssessmentGC(String netId, String experimentId, String moduleId) {
		String sql = "select netid from student_assessment_table "
					+ "where netid='"+experimentId+"' and expid='"+experimentId+"' "
					+ "and moduleid='"+moduleId+"'";	
		System.out.println(sql);
		
		return checkSQLSelectStatement(sql);
	}
	
	public int checkStudentPrelabGC(String netId, String experimentId, String moduleId) {
		String sql = "select netid from student_prelab_table "
					+ "where netid='"+experimentId+"' and expid='"+experimentId+"' "
					+ "and moduleid='"+moduleId+"'";	
		System.out.println(sql);
		
		return checkSQLSelectStatement(sql);
	}
	*/
	
	
}
