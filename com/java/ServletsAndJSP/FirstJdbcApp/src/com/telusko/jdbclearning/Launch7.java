package com.telusko.jdbclearning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Launch7 {
	
	

	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		final String SQL_UPDATE_AGE = "UPDATE studentInfo SET sage=? WHERE id=?";
		
		
		try {
			connection = JdbcUtil.getConnection();
			
			preparedStatement = connection.prepareStatement(SQL_UPDATE_AGE);
			
			Scanner scanner = new Scanner(System.in);
			
			System.out.println("Please enter info that needs to be updated-");
			System.out.print("ID: ");
			Integer id = scanner.nextInt();
			
			System.out.print("new age: ");
			Integer age = scanner.nextInt();
			
			preparedStatement.setInt(1, age);
			preparedStatement.setInt(2, id);
			
			int rowsAffected = preparedStatement.executeUpdate();
			
			if(rowsAffected == 0) {
				System.out.println("Unable to update the data...");
			} else {
				System.out.println("Data updated successfully: "+ rowsAffected + " rows affected.");
			}
			
			scanner.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
