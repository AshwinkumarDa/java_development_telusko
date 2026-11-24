package com.telusko.jdbclearning;

import java.sql.*;

public class LaunchApp2 {
	
	private static String getValueForKeyFromOS(String key) {
		return System.getenv(key);
	}
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// Load and Register the Driver
		// instantiate/load Driver class.
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// Establish the connection
		
		// Establish the connection
		String url = "jdbc:mysql://localhost:3306/telusko_db";
		String userName = LaunchApp2.getValueForKeyFromOS("DB_USER_TELUSKO");
		String password = LaunchApp2.getValueForKeyFromOS("DB_PASSWORD_TELUSKO");
		
		Connection connection = DriverManager.getConnection(url, userName, password);
		
		
		// creating statement
		Statement statement = connection.createStatement();
		
		// execute query
		String sql = "UPDATE studentInfo set sage = 24 where id = 2";
		int numberOfRowsEffected = statement.executeUpdate(sql);
		
		// process the result
		if(numberOfRowsEffected == 0) {
			System.out.println("Updation failed...");
		} else {
			System.out.println("Update Successfull...");
		}
		
		// close the resources
		statement.close();
		connection.close();
	}

}
