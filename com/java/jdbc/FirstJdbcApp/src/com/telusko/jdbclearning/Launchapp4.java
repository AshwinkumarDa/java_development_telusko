package com.telusko.jdbclearning;

import java.sql.*;

public class Launchapp4 {

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

		String sql = "DELETE FROM studentInfo where id = 2";
		int rowsAffected = statement.executeUpdate(sql);
		if(rowsAffected == 0) {
			System.out.println("Failed to delete the record");
		} else {
			System.out.println(rowsAffected + " records deleted successfully.");
		}

		// close the resources
		statement.close();
		connection.close();

	}

}
