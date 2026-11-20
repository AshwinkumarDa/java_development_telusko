

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class ServeletLife
 */
@WebServlet("/ServeletLife")
public class ServeletLife extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static {
		System.out.println("1. Servlet is Loaded...");
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServeletLife() {
        super();
        System.out.println("2. Servlet Object is Created...");
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("3. Servlet Initialized..."); 
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		System.out.println("9. destroy....");
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("4. Service method to handle http request and to response back");
	}

}
