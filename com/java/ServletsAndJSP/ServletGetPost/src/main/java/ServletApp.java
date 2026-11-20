

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class ServletApp
 */
@WebServlet("/ServletApp")
public class ServletApp extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("uname");
		String city = request.getParameter("ucity");
		
		
		if(name.equals("Rohan") && city.equals("Pune")) {
			System.out.println("Success! He is the right Rohan Logged in...");
		} else {
			System.out.println("Different Person lgged in...");
		}
			
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("uname");
		String city = request.getParameter("ucity");
		
		
		if(name.equals("Rohan") && city.equals("Pune")) {
			System.out.println("Success! He is the right Rohan Logged in...");
		} else {
			System.out.println("Different Person lgged in...");
		}
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	

}
