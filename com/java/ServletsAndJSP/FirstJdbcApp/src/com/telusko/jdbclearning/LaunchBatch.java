package com.telusko.jdbclearning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class LaunchBatch {
	public static void main(String[] args) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		final String SQL_UPDATE_AGE = "UPDATE studentInfo SET sage=? WHERE id=?";
		
		
		try {
			connection = JdbcUtil.getConnection();
			
			preparedStatement = connection.prepareStatement(SQL_UPDATE_AGE);
			
			
			preparedStatement.setInt(1, 20);
			preparedStatement.setInt(2, 1);
			preparedStatement.addBatch();
			
			preparedStatement.setInt(1, 22);
			preparedStatement.setInt(2, 5);
			preparedStatement.addBatch();
			
			preparedStatement.setInt(1, 24);
			preparedStatement.setInt(2, 44);
			preparedStatement.addBatch();
			
			int[] list = preparedStatement.executeBatch();
			
			if(list.length == 0) {
				System.out.println("Unable to update the data...");
			} else {
				System.out.println("Data updated successfully:\n"+ list.length + " rows affected.");
				for(int i: list)
					System.out.println("i: "+ i);
			}
			
			
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
