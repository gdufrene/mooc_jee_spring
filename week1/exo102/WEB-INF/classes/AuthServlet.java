import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

	// TODO : only handle POST request for authentication
	// @Override
	protected void doXXX(HttpServletRequest req, HttpServletResponse resp) 
	throws ServletException, IOException {
		// TODO : get login / password from request parameters
		String login = null;
		String password = null;
		
		if ( login == null || password == null ) throw new ServletException("no login/password");
		boolean succeed = "admin".equals(login) && "admin".equals(password);
		
		// TODO : if auth is OK, 
		  // add something in session for next calls, 
		  // then redirect to "welcome.jsp"

		// TODO : if auth KO
		  // set an "errorMessage" in request attribute
		  // forward to auth.jsp with request dispatcher

	}
	
	// TODO : allow to disconnect with a GET to /auth with any parameter "logout" value
	// @Override
	protected void doYYY(HttpServletRequest req, HttpServletResponse resp) 
	throws ServletException, IOException {
	  // TODO : check for "logout" parameter
	  //   if so : disconnect and show auth.jsp
	  //   if not : Error 500
	}

}