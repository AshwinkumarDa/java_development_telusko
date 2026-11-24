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

	private static String getValueForKeyFromOS(String key) {
		return System.getenv(key);
	}
	
	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/telusko_db";
		String userName = JdbcUtil.getValueForKeyFromOS("DB_USER_TELUSKO");
		String password = JdbcUtil.getValueForKeyFromOS("DB_PASSWORD_TELUSKO");
				
		return DriverManager.getConnection(url, userName, password);
	}
	
	
	public static void closeConnection(Connection connection, Statement statement) throws SQLException {
		statement.close();
		connection.close();
	}

}
