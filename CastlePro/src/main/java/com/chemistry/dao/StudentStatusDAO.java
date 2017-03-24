package com.chemistry.dao;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
@Scope("session")
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
	
		String sql2 = "select * from assignmentdetails, userassignment "
				+ "where assignmentdetails.a_id = userassignment.experimentid and "
				+ "userassignment.experimentid="+experimentId+" and userassignment.netid='"+netId+"'";
		
	String sql3 = "select * from assignmentdetails, userassignment where assignmentdetails.a_id = userassignment.experimentid and userassignment.netid = '"+netId+"'";
		System.out.println(sql3);
	
		return getStudentPreparedStatement(sql3);		
	}
	
	
	public AssignmentStatus getStudentAssignmentStatus(String netId, int experimentId) {
		
		//System.out.println("Came to AssignmentSttaus..");
		
		try {
			ResultSet rs = getStudentAssignmentStatusSQL(netId, experimentId);
			if(rs.next()) {
				Experiment experiment = new Experiment();				
				AssignmentStatus status = new AssignmentStatus();
				
				experiment.setDueDate(rs.getTimestamp("duedate"));
				experiment.setTitle(rs.getString("title"));
				experiment.setExperimentId(rs.getInt("a_id"));
				
				status.setExperiment(experiment);
				status.setAssessmentVolumetric(rs.getInt("assessment"));
				status.setAssessmentAcid(rs.getInt("assessmentacid"));
				status.setPrelabVolumetric(rs.getInt("prelab"));
				status.setPrelabAcid(rs.getInt("prelabacid"));
				status.setExperimentLabVolumetric(rs.getInt("lab"));
				status.setExperimentLabAcid(rs.getInt("labacid"));
				status.setPdfFile(rs.getBytes("pdffile"));
				status.setPostlab(rs.getInt("postlab"));
				return status;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private int updateQueries(String sql) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(sql);
		return ps.executeUpdate();
	}
	public int updateAssessmentInExperiment(String netId, int experimentId, String column) {
		
		String sql = "update userassignment set "+column+" = 1 where netId = '"+netId+"'";
		try {
			return updateQueries(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updatePrelabInExperiment(String netId, int experimentId, String column) {
		
		String sql = "update userassignment set "+column+" = 1 where netId = '"+netId+"'";
		try {
			return updateQueries(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	public int updateVolumetricLabInExperiment(String netId, int experimentId) {
		
		String sql = "update userassignment set lab = 1 where netId = '"+netId+"'";
		try {
			return updateQueries(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
public int updateAcidBaseLabInExperiment(String netId, int experimentId) {
		
		String sql = "update userassignment set labacid = 1 where netId = '"+netId+"'";
		try {
			return updateQueries(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int updatePDFFIleinDB(byte[] stream, String column, String netId, int experimentId) {
		
		String sql = "update userassignment set "+column+" = ? where netId = ?";
		System.out.println("SQL : "+sql);
		try {
			
			ByteArrayInputStream bais = new ByteArrayInputStream(stream);
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setBinaryStream(1, bais, stream.length);
			ps.setString(2, netId);

			ps.executeUpdate();
			ps.close();
			bais.close();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public byte[] getPrelabPDF(String netId, String column, int experimentId) {
		String sql = "select "+column+" from userassignment where netid = ? and experimentid = ?";
		
		byte[] pdf = null;
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, netId);
			ps.setInt(2, experimentId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				pdf = rs.getBytes(column);
			
			return pdf;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pdf;
	}
	
}
