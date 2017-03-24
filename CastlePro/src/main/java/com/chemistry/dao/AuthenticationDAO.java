package com.chemistry.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chemistry.model.User;

@Service
@Scope("session")
public class AuthenticationDAO extends StoreProcess{

	private ResultSet getResultSetofSQLStatement(String sql) throws SQLException {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();			
			return rs;		
	}
	
	public int createNewUserDetails(User user) {
		String sql = "insert into userdetails(netid, firstName, lastName) values(?,?,?)";

		System.out.println("Came to New User");
		try {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getNetId());
			ps.setString(2, user.getFirstName());
			ps.setString(3, user.getLastName());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
				return 1;
				
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return 0;
	}
	
	public boolean authenticateUser(User user) {

		String sql = "select * from userdetails "
				+ "where netid = '"+user.getNetId()+"' and firstname = '"+user.getFirstName()+"'";
		
		System.out.println(sql);
		
		try {
			ResultSet rs = getResultSetofSQLStatement(sql);
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int updateUserNetIdInUserAssignment(User user) {
		String sql = "insert into userassignment(netid, experimentid) values(?, ?)";
		System.out.println("Came to New UserAssignment");

		try {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getNetId());
			ps.setInt(2, 1);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
	//			System.out.println("Updated in Assignment");
			}
			return 1;			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		System.out.println("Not Updated");
		
		return 0;
	}
	
	public int deleteNewUser(User user) {
		String sql = "delete from userassignment where netid = '"+user.getNetId()+"'";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.executeUpdate();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return 0;
	}
		
}
