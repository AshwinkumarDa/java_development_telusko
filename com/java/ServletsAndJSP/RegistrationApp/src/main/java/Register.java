

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	private static String getValueForKeyFromOS(String key) {
		return System.getenv(key);
	}
	
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String piName = request.getParameter("pi_name");
		String piEmail = request.getParameter("pi_email");
		String piPassword = request.getParameter("pi_password");
		String piCity = request.getParameter("pi_city");
		
		// /usr/local/tomcat11/bin/shutdown.sh
		
		Connection connect = null;
		PreparedStatement prepStmt = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Establish the connection
			String url = "jdbc:mysql://localhost:3306/telusko_db";
			String userName = getValueForKeyFromOS("DB_USER_TELUSKO");
			String password = getValueForKeyFromOS("DB_PASSWORD_TELUSKO");
			
			connect = DriverManager.getConnection(url, userName, password);
						
			prepStmt = connect.prepareStatement("INSERT INTO personal_info (pi_name, pi_email, pi_password, pi_city) VALUES (?, ?, ?, ?)");
			
			prepStmt.setString(1, piName);
			prepStmt.setString(2, piEmail);
			prepStmt.setString(3, piPassword);
			prepStmt.setString(4, piCity);
			
			int rowsAffected = prepStmt.executeUpdate();
			
			PrintWriter writer = response.getWriter();
		
			writer.println("<html><body>");
			if(rowsAffected != 0) {
				writer.println("<h1>Registration Success!</h1>");
			} else {
				writer.println("<h1>Registration Failed </h1>");
			}
			
			writer.println("<body></html>");
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				prepStmt.close();
				connect.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
