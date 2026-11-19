package com.telusko.jdbclearning;

import java.sql.*;

public class LaunchApp2 {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
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
