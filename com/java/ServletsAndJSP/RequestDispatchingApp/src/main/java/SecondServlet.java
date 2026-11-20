

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class SecondServlet
 */
@WebServlet("/SecondServlet")
public class SecondServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    response.setContentType("text/html");
	    
	    HttpSession session = request.getSession(false);
	    
	    String name = (String) session.getAttribute("name");
	    String city = (String) session.getAttribute("city");
	    
	    

		PrintWriter writer = response.getWriter();
		// writer.println("<h1> Response from Second Servlet </h1>");
	       writer.println("<h1>Response from Second Servlet</h1> <h1>" + name + " from " + city + "</h1>");
	       
// 	       writer.println("<h2>First Servlet does not send any response to anyone. <br><br> First Servlet calls Second Servlet (without response), and Second Servlet sends response directly to Client </h2>");
		
		writer.close();
	}

}
