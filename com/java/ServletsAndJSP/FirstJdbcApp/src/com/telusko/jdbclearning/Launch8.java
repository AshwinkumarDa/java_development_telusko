package com.telusko.jdbclearning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Launch8 {

	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		final String SQL_DELETE = "DELETE FROM studentInfo where id=?";
		
		
		try {
			connection = JdbcUtil.getConnection();
			
			preparedStatement = connection.prepareStatement(SQL_DELETE);
			
			Scanner scanner = new Scanner(System.in);
			
			System.out.println("Please enter info that needs to be deleted-");
			System.out.print("ID: ");
			Integer id = scanner.nextInt();
			
			preparedStatement.setInt(1, id);
			
			int rowsAffected = preparedStatement.executeUpdate();
			
			if(rowsAffected == 0) {
				System.out.println("Unable to delete the data...");
			} else {
				System.out.println("Data deleted successfully: "+ rowsAffected + " rows affected.");
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
