package com.company.dao.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.company.dao.StoreHead;
import com.company.model.account.registration.AccountLogin;
import com.company.model.account.registration.StudentRegistration;
import com.company.model.registration.StudentProfile;
import com.company.model.registration.TADetails;
import com.company.model.registration.TAMainDetails;

@Repository
@Scope("session")
public class AuthenticationDAO  extends StoreHead{
	
	public int validateStudent(AccountLogin login) {
		
		String sql = "select * from user_account "
				+ "where netId = '"+login.getNetId()+"' and "
						+ "pswd = '"+login.getPassword()+"' and "
								+ "accounttype='"+login.getAccountType()+"'";
		
		System.out.println(sql);
		try {
			ResultSet rs = selectCommand(sql);
			if(rs.next()) {
				System.out.println("Account Exists");
				//rs.close();
				return 1;
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 return 0;
	}

	private int checkUser(String netId) {
		String sql = "select * from user_account "
				+ "where netId = '"+netId+"'";
		
		System.out.println(sql);
		try {
			ResultSet rs = selectCommand(sql);
			if(rs.next()) {
				System.out.println("Account Exists");
				return 2;
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return 0;
	}
	public int storeStudentAccount(StudentRegistration student, String classno) {
		
		String sql = "insert into user_account values('"+student.getLogin().getNetId()+"',"
				+ "'"+student.getLogin().getPassword()+"',"
				+ "'"+student.getLogin().getAccountType()+"',"
				+ "'"+student.getFirstName()+"','"+student.getLastName()+"','"+student.getEmail()+"','"+classno+"')";
		
		
		int k = checkUser(student.getLogin().getNetId());
		if(k == 2) return 2;
		
		System.out.println(sql);
		
		return insertCommand(sql);
	}
	
	
	public StudentProfile getStudentPersonalInformation(AccountLogin login) {
		
		String sql = "select * from user_account as a, registration_slot as b "
				+ "where a.slot = b.classno and "
				+ "a.netid='"+login.getNetId()+"' and a.pswd='"+login.getPassword()+"'";
		
		System.out.println(sql);
		
		ResultSet rs = selectCommand(sql);
		
		StudentProfile profile = new StudentProfile();
		int k = 0;
		try {
			while(rs.next()) {
				
				StudentRegistration register = new StudentRegistration();		
				register.setEmail(rs.getString("email"));
				register.setFirstName(rs.getString("firstname"));
				register.setLastName(rs.getString("lastname"));
				register.setLogin(login);
				
				TADetails tDetails = new TADetails();
				tDetails.setClassNo(rs.getString("classno"));
				tDetails.setRoom(rs.getString("room"));
				tDetails.setTaName(rs.getString("taname"));
				tDetails.setTaNetId(rs.getString("tanetid"));

				TAMainDetails mainDetails = new TAMainDetails();
				mainDetails.setDay(rs.getString("weekday"));
				mainDetails.setSemester(rs.getString("semester"));
				mainDetails.setTime(rs.getString("daytime"));
				mainDetails.setYear(rs.getString("semesterYear"));
				
				mainDetails.setTaDetails(tDetails);
				
				profile.setClassInformation(mainDetails);
				profile.setStudentDetails(register);
			
				k = 1;
			}
		
			if(k == 0) return null;
			
			return profile;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public ArrayList<StudentProfile> getAllStudentsPersonalInformation() {
		
		String sql = "select * from user_account as a, registration_slot as b "
				+ "where a.slot = b.classno";
		
		System.out.println(sql);
		
		ResultSet rs = selectCommand(sql);
		ArrayList<StudentProfile> list = new ArrayList<StudentProfile>();
		
		int k = 0;
		try {
			while(rs.next()) {
				StudentProfile profile = new StudentProfile();

				AccountLogin login = new AccountLogin();
				login.setNetId(rs.getString("netid"));
				StudentRegistration register = new StudentRegistration();		
				register.setEmail(rs.getString("email"));
				register.setFirstName(rs.getString("firstname"));
				register.setLastName(rs.getString("lastname"));
				register.setLogin(login);
				
				TADetails tDetails = new TADetails();
				tDetails.setClassNo(rs.getString("classno"));
				tDetails.setRoom(rs.getString("room"));
				tDetails.setTaName(rs.getString("taname"));
				tDetails.setTaNetId(rs.getString("tanetid"));

				TAMainDetails mainDetails = new TAMainDetails();
				mainDetails.setDay(rs.getString("weekday"));
				mainDetails.setSemester(rs.getString("semester"));
				mainDetails.setTime(rs.getString("daytime"));
				mainDetails.setYear(rs.getString("semesterYear"));
				
				mainDetails.setTaDetails(tDetails);
				
				profile.setClassInformation(mainDetails);
				profile.setStudentDetails(register);

				list.add(profile);

				k = 1;
			}
		
			if(k == 0) return null;
			
			return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}
	
	
	
	public String getStudentRegistrationInfo() {
		
		String sql = "select modified from registration_slot_json where setPoint = 1";
		
		System.out.println(sql);
		
		try {
			ResultSet rs = selectCommand(sql);
			String s = "";
			if(rs.next()) s = rs.getString(1);
//			closeConnection();
			return s;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return null;
	}
}
