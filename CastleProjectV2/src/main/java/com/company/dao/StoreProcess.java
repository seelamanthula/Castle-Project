package com.company.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("session")
public class StoreProcess {
		
	public static final String driver = "com.mysql.jdbc.Driver";
	public static final String url = "jdbc:mysql://localhost:3306/safe";

	public static final String user = "root";
	public static final String password1 = "Ad*min24";
	public static final String password2 = "!Ad*min";
	
	public static Connection connection = null;
		public Statement statement = null;
		public ResultSet resultSet = null;
		public PreparedStatement preparedStatement = null;
		
		static {
			try	{
				Class.forName(driver);
				System.out.println("Driver Loaded");
				connection = DriverManager.getConnection(url, user, password2);
				System.out.println("Connection ESTD 1");
			}
			catch(ClassNotFoundException e)	{
				e.printStackTrace();
			}
			catch(SQLException e){
				e.getMessage();		
				try {
					connection = DriverManager.getConnection(url, user, password1);
					System.out.println("Connection ESTD 2");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		public static Connection getConnection() throws SQLException {
			if(connection == null) {
				try {
				connection = DriverManager.getConnection(url, user, password2);
				}
				catch(SQLException e) {
					connection = DriverManager.getConnection(url, user, password1);					
				}
			}
			
			return connection;
		}		
}
