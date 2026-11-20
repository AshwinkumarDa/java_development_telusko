package com.telusko.jdbclearning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Launch9 {

	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		
		String SQL_SELECT = "SELECT id, sname, sage, scity FROM studentInfo WHERE id=?";
		
		try {
			connection = JdbcUtil.getConnection();
			
			preparedStatement = connection.prepareStatement(SQL_SELECT);
			
			
			Scanner scanner = new Scanner(System.in);
			
			System.out.println("Kindly enter your id for which Data to be Retrieved: ");
			
			Integer id = scanner.nextInt();
			
			preparedStatement.setInt(1, id);
			
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				Integer sid = resultSet.getInt(1);
				String sname = resultSet.getString(2);
				Integer sage = resultSet.getInt(3);
				String scity = resultSet.getString(4);
				
				System.out.println(sid + " | " + sname + " | " + sage + " | " + scity);
				
			} else {
				System.out.println("There is no record with id " + id);
			}
			
			scanner.close();
			
		} catch (SQLException e) {
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
