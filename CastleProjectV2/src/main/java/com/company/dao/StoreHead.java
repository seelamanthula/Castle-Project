package com.company.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("session")
public class StoreHead {

	protected synchronized int updateCommand(String sql) {
		PreparedStatement ps;
		try {
			ps = StoreProcess.getConnection().prepareStatement(sql);
			int k = ps.executeUpdate();
			return k;

		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
	//		closeConnection();
		}
		return 0;
	}
	
	protected synchronized int insertCommand(String sql) {
		PreparedStatement ps;
		try {
			ps = StoreProcess.getConnection().prepareStatement(sql);
			int k = ps.executeUpdate();
			return k;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	//		closeConnection();
		}
		return 0;
	}

	protected ResultSet selectCommand(String sql)  {
		Statement state;
		try {
			state = StoreProcess.getConnection().createStatement();
			ResultSet rs = state.executeQuery(sql); 
			
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		//	closeConnection();
		}
		return null;
	}

	protected void closeConnection()  {
		
		try	{
			StoreProcess.getConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	protected int deleteCommand(String sql) {
		return updateCommand(sql);
	}
	
	protected int checkSQLSelectStatement(String sql) {
		try {
			 
			ResultSet rs = selectCommand(sql);
			if(rs.next()) return 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;		
	}
	
}
