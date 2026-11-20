package com.telusko.jdbclearning;

import java.sql.*;
import java.util.Scanner;

public class LaunchApp6 {

	public static void main(String[] args) {
		
		final String SQL_INSERT = "INSERT INTO studentInfo(id, sname, sage, scity) VALUES (?, ?, ?, ?)";
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {			

			// Load and Register the Driver
			// instantiate/load Driver class.
			connection = JdbcUtil.getConnection();
			
			// Insert
			preparedStatement = connection.prepareStatement(SQL_INSERT);
			
			System.out.println("Please enter following details to be stored in DB");
			
			Scanner scanner = new Scanner(System.in);
			
			System.out.println("Enter ID: ");
			Integer id = scanner.nextInt();
			
			System.out.println("Enter name: ");
			String name = scanner.next();
			
			System.out.println("Enter Age: ");
			Integer age = scanner.nextInt();
			
			System.out.println("Enter City: ");
			String city = scanner.next();
			
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setInt(3, age);
			preparedStatement.setString(4, city);
			
			
			// creating statement
			int rowAffected = preparedStatement.executeUpdate();
			
			if(rowAffected == 0) {
				System.out.println("Unable to insert the data");
			} else {
				System.out.println("Data Inserted Successfully!");
			}

			scanner.close();

		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close the resources
			System.out.println("In finally block");
			
			try {
				JdbcUtil.closeConnection(connection, preparedStatement);
				System.out.println("connection closed");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			

		}
	}

}
