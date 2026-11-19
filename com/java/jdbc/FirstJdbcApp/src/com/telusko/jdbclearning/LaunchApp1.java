package com.telusko.jdbclearning;

import java.sql.*;

public class LaunchApp1 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// Load and Register the Driver
		// instantiate/load Driver class.
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// Establish the connection
		String url = "jdbc:mysql://localhost:3306/jdbclearning";
		String user = "root";
		String password = "ashwin@123";
		Connection connection = DriverManager.getConnection(url, user, password);
		
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
