package com.company.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.company.dao.StoreProcess;

@Repository
@Scope("session")
public class Store1 {

	private int insertQuery(String sql) throws SQLException {
		PreparedStatement ps = StoreProcess.getConnection().prepareStatement(sql);
		return ps.executeUpdate();
	}
	
	public int storeFile(byte[] file) {
		
		String sql = "insert into files(content) values(?)";		
		try {
		
			//System.out.println("COnn : "+connection);
			PreparedStatement ps = StoreProcess.getConnection().prepareStatement(sql);
			ps.setBytes(1, file);
			int k = ps.executeUpdate();
			System.out.println("Inserted : "+k);
			return k;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return 0;
	}
	
	public int storeFileWithString(String file) {
		String sql = "insert into files2(content) values(?)";		
		try {
		
			//System.out.println("COnn : "+connection);
			PreparedStatement ps = StoreProcess.getConnection().prepareStatement(sql);
			ps.setString(1, file);
			int k = ps.executeUpdate();
			System.out.println("Inserted : "+k);
			return k;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return 0;
	}
	
	public String getFileWithString(int id) {
		String sql = "select content from files2 where id="+id;		
		try {
		
			PreparedStatement ps = StoreProcess.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("In Next");
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public byte[] retrieveFile(int id) {
		
		String sql = "select content from files where id="+id;		
		try {
		
			//System.out.println("COnn : "+connection);
			PreparedStatement ps = StoreProcess.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			byte[] b;
			if(rs.next()) {
				b=rs.getBytes(1);
				System.out.println("retrieved size : "+b.length);
				return b;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
}
