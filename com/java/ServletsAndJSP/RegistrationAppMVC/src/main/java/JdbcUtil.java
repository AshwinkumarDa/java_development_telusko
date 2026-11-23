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
	
	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/telusko_db";
		String userName = "root";
		String password = "ashwin@123";
		return DriverManager.getConnection(url, userName, password);
	}
	
	public static void closeConnection(Connection connection, Statement stmt) throws SQLException {
		if(connection!= null) 
			connection.close();
		
		if(stmt!= null)
			stmt.close();
	}
}
