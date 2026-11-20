

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class FirstServlet
 */
@WebServlet("/FirstServlet")
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Control in First Servlet");
		
		String name = request.getParameter("uname");
		String city = request.getParameter("ucity");
		
		
		
		response.setContentType("text/html");
		
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/SecondServlet");
		
		
		/// for below step 1, 2, load URL: http://localhost:8080/RequestDispatchingApp/FirstServlet
		/// 
		// 1. forward API call will forward request to SecondServlet without processing it. and SecondServlet server will send response to client.
		// requestDispatcher.forward(request, response);
		
		
		// 2. include API call will process request by FirstServlet and then sent to SecondServlet for further processing.
		//    Second Servlet response sends back to FirstServlet and FirstServlet sends response to Client.
		// requestDispatcher.include(request, response);
		
		
		// 3. forward data to SecondServlet
		HttpSession session = request.getSession();
		session.setAttribute("name", name);
		session.setAttribute("city", city);
		requestDispatcher.forward(request, response);
		
		
		
		PrintWriter writer = response.getWriter();
		writer.println("<h1>Response from Servlet One </h1>");
		
		writer.close();
		
		System.out.println("Control again in First Servlet");
	}

}
