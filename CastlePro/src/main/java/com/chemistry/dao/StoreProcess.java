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
@Scope("session")
	public class StoreProcess {
		
//		public static final String driver = "org.postgresql.Driver";
//		public static final String url = "jdbc:postgresql://localhost:5432/chproject";
//		public static final String user = "postgres";
//		public static final String password = "root";
	public static final String driver = "com.mysql.jdbc.Driver";
	public static final String url1 = "jdbc:mysql://localhost/chproject";
	public static final String url2 = "jdbc:mysql://169.226.48.166/chproject";

	public static final String user = "root";
//	public static final String password = "051633b$";
	public static final String password = "root";

	public static Connection connection = null;
		public Statement statement = null;
		public ResultSet resultSet = null;
		public PreparedStatement preparedStatement = null;
		
		static {
			try	{
				Class.forName(driver);
				System.out.println("Driver Loaded");
				connection = DriverManager.getConnection(url2, user, password);
				System.out.println("Connection ESTD");
			}
			catch(ClassNotFoundException e)	{
				e.printStackTrace();
			}
			catch(SQLException e){
				try {
					connection = DriverManager.getConnection(url1, user, password);
					System.out.println("Connection ESTD");

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.getMessage();
			}
		}
}
