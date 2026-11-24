import java.sql.*;

public class JdbcUtil {
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Failed to register driver: " + e.getLocalizedMessage());
		} 
		
		System.out.println("Driver is registered...");
	}
	
	private static String getValueForKeyFromOS(String key) {
		return System.getenv(key);
	}
	
	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/telusko_db";
		String userName = JdbcUtil.getValueForKeyFromOS("DB_USER_TELUSKO");
		String password = JdbcUtil.getValueForKeyFromOS("DB_PASSWORD_TELUSKO");
		
		System.out.println("userName = " + userName + ", password = " + password);
		
		return DriverManager.getConnection(url, userName, password);
	}
	
	public static void closeConnection(Connection connection, Statement stmt) throws SQLException {
		if(stmt!= null)
			stmt.close();
		
		if(connection!= null) 
			connection.close();

	}
}
