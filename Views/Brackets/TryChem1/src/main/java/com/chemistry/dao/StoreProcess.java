package com.chemistry.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
//@Scope(value="prototype")
	public class StoreProcess {
		
		public static final String driver = "org.postgresql.Driver";
		public static final String url = "jdbc:postgresql://localhost:5432/chproject";
		public static final String user = "postgres";
		public static final String password = "root";
		public static Connection connection = null;
		public Statement statement = null;
		public ResultSet resultSet = null;
		public PreparedStatement preparedStatement = null;
		
		static {
			try	{
				Class.forName(driver);
				System.out.println("Driver Loaded");
				connection = DriverManager.getConnection(url, user, password);
				System.out.println("Connection ESTD");
			}
			catch(ClassNotFoundException e)	{
				e.getMessage();
				e.printStackTrace();
			}
			catch(SQLException e){
				e.getMessage();
			}
		}
}
