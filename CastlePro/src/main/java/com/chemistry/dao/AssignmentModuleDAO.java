package com.chemistry.dao;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.chemistry.model.Experiment;
import com.chemistry.model.Module;

@Repository
@Scope("session")
public class AssignmentModuleDAO extends StoreProcess {
	
	
	protected ResultSet getResultSetofPreparedStatement(String sql) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(sql);
		return ps.executeQuery();
	}
	
	public Module retrieveModule(int fileId)	{
		String sql = "SELECT * FROM moduledetails WHERE mod_id = "+fileId;

		Module module = new Module();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
				if (rs != null) {
			    while (rs.next()) {
			    	module.setId(rs.getInt("mod_id"));
			    	module.setName(rs.getString("module_name"));
			    	module.setFileBytes(rs.getBytes("pdfFile"));
			    }
			    rs.close();
			}
			return module;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Module> getModulesofExperiment(int experimentId) {

		String sql = "select * from moduledetails "
				+ "where moduledetails.mod_id in (select distinct mod_id "
				+ "from assignment where assignment.assign_id = "+experimentId+")";

		List<Module> moduleList = new ArrayList<Module>();
		
		try {
			ResultSet rs = getResultSetofPreparedStatement(sql);
			while(rs.next()) {
				Module module = new Module();
				module.setId(rs.getInt("mod_id"));
				module.setName(rs.getString("module_name"));
				module.setFileBytes(rs.getBytes("pdfFile"));
				moduleList.add(module);
			}
			return moduleList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*public Experiment getParticularAssignmentDetails(int assignmentId) {
		
		String sql = "select * from assignmentdetails where a_id="+assignmentId;
		
		try {
			ResultSet rs = getResultSetofPreparedStatement(sql);
			Experiment details = new Experiment();
			if(rs.next()) {
				details.setExperimentId(rs.getInt("a_id"));
				details.setTitle(rs.getString("title"));
				details.setDueDate(rs.getTimestamp("duedate"));
			}
			return details;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	*/
	
	public List<Experiment> getAllAssignmentDetails() {
		String sql = "select * from assignmentdetails";
		
		List<Experiment> allDetails = new ArrayList<Experiment>();
		try {
			ResultSet rs = getResultSetofPreparedStatement(sql);
			
			while(rs.next()) {
				Experiment details = new Experiment();
				details.setExperimentId(rs.getInt("a_id"));
				details.setTitle(rs.getString("title"));
				details.setDueDate(rs.getTimestamp("duedate"));
				allDetails.add(details);
			}
			return allDetails;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
