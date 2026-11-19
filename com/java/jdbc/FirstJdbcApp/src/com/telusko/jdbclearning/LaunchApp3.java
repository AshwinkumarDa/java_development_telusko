package com.telusko.jdbclearning;

import java.sql.*;

public class LaunchApp3 {

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
