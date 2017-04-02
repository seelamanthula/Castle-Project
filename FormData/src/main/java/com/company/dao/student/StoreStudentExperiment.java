package com.company.dao.student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	
		return updateCommand(sql);
	}

	public int createStudentPrelabGC(String netId, String experimentId, String moduleId) {
		String sql = "insert into student_prelab_table(netid, expid, moduleid, score) "
				+ "values('"+netId+"','"+experimentId+"','"+moduleId+"', 0)";
	
		return updateCommand(sql);
	}
	
	public int createStudentGCTAComments(String netId, String experimentId) {
		String sql = "insert into student_tacomments_table(netid, expid) "
				+ "values('"+netId+"','"+experimentId+"')";
	
		return updateCommand(sql);
	}
	
	public ArrayList<String> checkGetExperimentsStudentGradeCenter(String netId) {
		String sql = "select distinct a.id "
				+ "from experiment_table as a "
				+ "where a.experiment_status='PUBLISH' and "
				+ "a.id NOT IN (select b.expid "
				+ "from student_assessment_table as b "
				+ "where b.netid='"+netId+"')";

		System.out.println(sql);
		ArrayList<String> list = new ArrayList<String>();

		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 

			while(rs.next()) {
				String s = rs.getString(1);
				list.add(s);
			}
			
			if(list.size() > 0) return list;
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
		
		return new ArrayList<String>();
	}

	public ArrayList<String> getModulesOfExperiment(String experimentId) {
		String sql = "select module_Id from module_table "
				+ "where experiment_Id='"+experimentId+"'";
	
		System.out.println(sql);
		ArrayList<String> list = new ArrayList<String>();
		
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 

			while(rs.next()) {			
				list.add(rs.getString(1));			
			}
			if(list.size() > 0) return list;

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

	public ExperimentModuleDetails getStudentExperimentAssessmentModulesScore(String netId, String experimentId) {
		
		String sql = "select distinct b.moduleid, a.module_name, b.score, c.name "
				+ "from student_assessment_table as b, module_table as a, experiment_table as c "
				+ "where a.module_Id = b.moduleid and c.id = a.experiment_Id "
				+ "and b.expid='"+experimentId+"' "
				+ "and b.netid='"+netId+"'";
		
		System.out.println(sql);

		ArrayList<ModuleDetails> list = new ArrayList<ModuleDetails>();
		ExperimentModuleDetails emDetails = new ExperimentModuleDetails();
		
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 
			
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
	
	public ExperimentModuleDetails getStudentExperimentPrelabModulesScore(String netId, String experimentId) {
		
		String sql = "select distinct b.moduleid, a.module_name, b.score, c.name, c.exp_duedate "
				+ "from student_prelab_table as b, module_table as a, experiment_table as c "
				+ "where a.module_Id = b.moduleid and c.id = a.experiment_Id "
				+ "and b.expid='"+experimentId+"' "
				+ "and b.netid='"+netId+"'";
		
		System.out.println(sql);

		ArrayList<ModuleDetails> list = new ArrayList<ModuleDetails>();
		ExperimentModuleDetails emDetails = new ExperimentModuleDetails();
		
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 
			
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

	
	public String getStudentTAComments(String netId, String experimentId) {
	
		String sql = "select ta_comments from student_tacomments_table "
				+ "where expid='"+experimentId+"' and netid='"+netId+"'";
		
		System.out.println(sql);

		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 
			
			if(rs.next()) {
				
				String s = rs.getString("ta_comments");
				if(s != null) {
					s = s.replace("\n","\\n");
					return s;
				}
			}
			
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
	
	// Experiment Status with TA comments from student_tacomments_table

	public Map<String, ExperimentStatus> getStudentAssessmentStatus(String netId) {
		
		String sql = "select distinct expid, a.name, sum(b.score), a.exp_duedate "
				+ "from student_assessment_table as b, experiment_table as a "
				+ "where a.id = b.expid and a.experiment_status='PUBLISH' "
				+ "and b.netid='"+netId+"' "
				+ "group by b.expid";
		
		System.out.println(sql);
		
		Map<String, ExperimentStatus> map = new HashMap<String, ExperimentStatus>();
		
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 
			
			while(rs.next()) {
				ExperimentStatus status = new ExperimentStatus();
				status.setExperimentId(rs.getString(1));
				status.setExperimentName(rs.getString(2));
				status.setAssessmentScore(rs.getInt(3));
				status.setDueDate(rs.getString(4));
				map.put(rs.getString(1), status);
			}
			
			return map;
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
	
	public Map<String, Integer> getStudentPrelabStatus(String netId) {
		
		String sql = "select distinct a.id, sum(c.score) "
				+ "from experiment_table as a, student_prelab_table as c "
				+ "where c.expid = a.id and a.experiment_status='PUBLISH' "
				+ "and c.netid='"+netId+"' "
				+ "group by c.expid";

		System.out.println(sql);

		Map<String, Integer> map = new HashMap<String, Integer>();
		
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			
			conn = getDataSource().getConnection();			
			state = conn.createStatement();
			rs = state.executeQuery(sql); 
			
			while(rs.next()) {
				String s = rs.getString(1);
				int i = rs.getInt(2);
				
				map.put(rs.getString(1), i);
			}
			
			return map;
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
	
}
