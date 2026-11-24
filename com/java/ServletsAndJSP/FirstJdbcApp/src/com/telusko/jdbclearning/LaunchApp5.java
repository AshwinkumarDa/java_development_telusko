package com.telusko.jdbclearning;

import java.sql.*;

public class LaunchApp5 {

	private static String getValueForKeyFromOS(String key) {
		return System.getenv(key);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		final String SQL_UPDATE = "UPDATE studentInfo set sage = 16 where id = 1";
		// final String SQL_SELECT = "SELECT * FROM studentInfo";

		// Load and Register the Driver
		// instantiate/load Driver class.
		Class.forName("com.mysql.cj.jdbc.Driver");

		// Establish the connection
		String url = "jdbc:mysql://localhost:3306/telusko_db";
		String userName = getValueForKeyFromOS("DB_USER_TELUSKO");
		String password = getValueForKeyFromOS("DB_PASSWORD_TELUSKO");
		
		Connection connection = DriverManager.getConnection(url, userName, password);


		// creating statement
		Statement statement = connection.createStatement();

		String sql = SQL_UPDATE;
		boolean status = statement.execute(sql);
		
		if(status) {
			System.out.println("status = true");
			//  true for SELECT statements
			ResultSet rs = statement.getResultSet();
			while(rs.next()) {
				System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getInt(3) + " | " + rs.getString(4));
			}
			
		} else {
			System.out.println("status = false");
			
			// false for INSERT, UPDATE, DELETE statements
			int rowsAffected = statement.getUpdateCount();
			if (rowsAffected == 0) {
				System.out.println("Operation failed...");
			} else {
				System.out.println("Operation Successfull: " + rowsAffected + "rows affected.");
			}
		}

		// close the resources
		statement.close();
		connection.close();

	
	}

}
