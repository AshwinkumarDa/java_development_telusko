package com.telusko.jdbclearning;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {
	static {
		// Load and Register the Driver
		// instantiate/load Driver class.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		// Establish the connection
		String url = "jdbc:mysql://localhost:3306/jdbclearning";
		String user = "root";
		String password = "ashwin@123";
		return DriverManager.getConnection(url, user, password);
	}
	
	public static void closeConnection(Connection connection, Statement statement) throws SQLException {
		statement.close();
		connection.close();
	}

}
