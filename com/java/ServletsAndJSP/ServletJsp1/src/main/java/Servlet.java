

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("uname");
		String ucity = request.getParameter("ucity");
		
		PrintWriter writer = response.getWriter();
		
		writer.println("<html><head><title>Second Servlet App</title></head>");
		writer.println("<body bgcolor = 'cyan'> <h1><marquee>Welcome to our dynamic app</marquee></h1>");
		writer.println("<table>");
		writer.println("<h1>Hello changes in servlet, to see need to restart Apache Tomcat Server.</h1>");
		writer.println("<tr> <th> NAME</th> <th> CITY </th> </tr>");
		writer.println("<tr> <td>" + name + "</td> <td> " + ucity + "</td> </tr>");
		writer.println("</table></body>");
		writer.println("</html>");
		writer.close();
	}

}
