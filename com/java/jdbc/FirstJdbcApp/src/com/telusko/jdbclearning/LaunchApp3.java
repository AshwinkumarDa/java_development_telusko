package com.telusko.jdbclearning;

import java.sql.*;

public class LaunchApp3 {

	private static String getValueForKeyFromOS(String key) {
		return System.getenv(key);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// Load and Register the Driver
				// instantiate/load Driver class.
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				// Establish the connection
				String url = "jdbc:mysql://localhost:3306/telusko_db";
				String userName = LaunchApp3.getValueForKeyFromOS("DB_USER_TELUSKO");
				String password = LaunchApp3.getValueForKeyFromOS("DB_PASSWORD_TELUSKO");
				
				Connection connection = DriverManager.getConnection(url, userName, password);
				
				
				// creating statement
				Statement statement = connection.createStatement();
				
				// execute query
				String sql = "SELECT * FROM studentInfo";
				ResultSet rs = statement.executeQuery(sql);
				
				// boolean isFound = false;
				
				while(rs.next()) {
				
					// isFound = true;
					// fetch record details
					//int id = rs.getInt(1);
					//String snmae = rs.getString(2);
					//int sage = rs.getInt(3);
					//String scity = rs.getString(4);
					
					
					// System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getInt(3) + " | " + rs.getString(4));
					System.out.println(rs.getInt("id") + " " + rs.getString("sname") + " " + rs.getInt("sage") + " " + rs.getString("scity"));
					
					
				}
				
				
				// close the resources
				statement.close();
				connection.close();

	}

}
