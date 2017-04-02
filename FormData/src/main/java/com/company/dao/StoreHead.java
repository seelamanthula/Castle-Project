package com.company.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("session")
public class StoreHead extends StoreProcess {

	protected synchronized int updateCommand(String sql) {
		System.out.println(sql);

		PreparedStatement ps = null;
		Connection conn = null;
		int k = 0;
		try {
			conn = getDataSource().getConnection();
			ps = conn.prepareStatement(sql);
			k = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return k;
	}
	
	protected synchronized int insertCommand(String sql) {
		System.out.println(sql);

		PreparedStatement ps = null;
		Connection conn = null;
		int k = 0;
		try {
			conn = getDataSource().getConnection();
			ps = conn.prepareStatement(sql);
			k = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return k;
	}

	protected synchronized int deleteCommand(String sql) {
		System.out.println(sql);

		return updateCommand(sql);
	}
	
	protected int checkSQLSelectStatement(String sql) {
		System.out.println(sql);

		Statement state = null;
		Connection conn = null;
		ResultSet rs = null;
		int k = 0;
		try {
			conn = getDataSource().getConnection();
			state = conn.createStatement();
			rs = state.executeQuery(sql); 
			
			if(rs.next()) {
				System.out.println("RS has File");
				k = 1;
			}
		
			System.out.println("K Value : "+k);
		} catch (SQLException e) {
			System.out.println("In Check Condition K : "+k);
			e.printStackTrace();
			return 0;
		}finally {
			try {
				if(rs != null) rs.close();
				if(state != null) state.close();
			if(conn != null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return k;
	
	}

}
