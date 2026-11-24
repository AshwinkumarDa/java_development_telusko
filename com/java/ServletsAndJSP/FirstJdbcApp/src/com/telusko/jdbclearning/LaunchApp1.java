package com.telusko.jdbclearning;

import java.sql.*;

public class LaunchApp1 {

	private static String getValueForKeyFromOS(String key) {
		return System.getenv(key);
	}

	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// Load and Register the Driver
		// instantiate/load Driver class.
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		
		// Establish the connection
		String url = "jdbc:mysql://localhost:3306/telusko_db";
		String userName = LaunchApp1.getValueForKeyFromOS("DB_USER_TELUSKO");
		String password = LaunchApp1.getValueForKeyFromOS("DB_PASSWORD_TELUSKO");
		
		Connection connection = DriverManager.getConnection(url, userName, password);
		
		// creating statement
		Statement statement = connection.createStatement();
		
		// execute query
		String sql = "INSERT INTO studentInfo(id, sname, sage, scity) VALUES (1, 'Rohan', 17, 'Bengaluru')";
		int numberOfRowsEffected = statement.executeUpdate(sql);
		
		// process the result
		if(numberOfRowsEffected == 0) {
			System.out.println("Unable to inser the data...");
		} else {
			System.out.println("Data Inserted Successfully...");
		}
		
		// close the resources
		statement.close();
		connection.close();
		
	}
}
