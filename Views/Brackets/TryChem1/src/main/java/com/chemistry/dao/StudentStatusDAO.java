package com.chemistry.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.chemistry.lab.AssignmentStatus;
import com.chemistry.model.Experiment;

@Repository
public class StudentStatusDAO extends AssignmentModuleDAO {
	
	private ResultSet getStudentPreparedStatement(String sql) throws SQLException {
		return getResultSetofPreparedStatement(sql);
	}
	
	private ResultSet getStudentAssignmentStatusSQL(String netId, int experimentId) throws SQLException {
		String sql = "select (studentassignment.assignment"+experimentId+").assign_id, "
				+ "(studentassignment.assignment"+experimentId+").assessment, "
				+ "(studentassignment.assignment"+experimentId+").prelab, "
				+ "(studentassignment.assignment"+experimentId+").lab, "
				+ "(studentassignment.assignment"+experimentId+").pdfFile "
				+ "from studentassignment where studentassignment.netid="+netId;
		
		return getStudentPreparedStatement(sql);		
	}
	
	public List<AssignmentStatus> getStudentAssignmentStatusList(String netId, List<Experiment> experiments) {
		
		List<AssignmentStatus> assignmentStatus = new ArrayList<AssignmentStatus>();
		Iterator<Experiment> iterate = experiments.iterator();
		
		while(iterate.hasNext()) {
			Experiment experiment = iterate.next();

			try {
				ResultSet rs = getStudentAssignmentStatusSQL(netId, experiment.getExperimentId());
				if(rs.next()) {
					AssignmentStatus status = new AssignmentStatus();
					status.setExperiment(experiment);
					status.setAssessment(rs.getInt("assessment"));
					status.setPrelab(rs.getInt("prelab"));
					status.setExperimentLab(rs.getInt("lab"));
					status.setPdfFile(rs.getBytes("pdfFile"));
					assignmentStatus.add(status);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return assignmentStatus;
	}
	
	public int updateAssessmentInExperiment(String netId, int experimentId) {
		
		// Update Assessment
		
		return 0;
	}
	
	public int updatePrelabInExperiment(String netId, int experimentId) {
		
		// Update Prelab
		
		return 0;
	}

}
