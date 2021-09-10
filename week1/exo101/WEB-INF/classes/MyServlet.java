import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/dist")
public class MyServlet extends HttpServlet {

	// @Override
	protected void doXXX(HttpServletRequest req, HttpServletResponse resp) 
	throws ServletException, IOException {
		// TODO : show a HTML form with 4 input text (2 for each point)
		//        your input must be named 'p1lat', 'p1lng', 'p2lat', 'p2lng'

		Writer out = resp.getWriter();
		out.write("Show form");
	}
	
	// @Override
	protected void doYYY(HttpServletRequest req, HttpServletResponse resp) 
	throws ServletException, IOException {
		// TODO : get first point latitude / longitude
		// TODO : get second point latitude / longitude
		// TODO : compute distance between two points
		// TODO : display distance, in kilometer with 1 decimal

		Writer out = resp.getWriter();
		out.write("Display result");
	}

}