package auth;

import static org.junit.Assert.*;
import static utils.HtmlUtils.asString;
import static utils.HtmlUtils.getTagAttribute;
import static utils.HtmlUtils.toNextTag;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import utils.HtmlUtils;
import utils.WebTool;

public class SigninCheck {
	
	WebTool web;
	
	@Before
	public void initWebTool() {
		String port = System.getProperty("usePort");
		int iPort = ( port == null ? 8080 : Integer.parseInt(port) );
		web = new WebTool("localhost:"+iPort, "/exo201/");
	}

	@Test
	public void checkRegisterPage() throws IOException {
		// 4. There is a register.jsp page,
		boolean okForm = false;
		boolean pageReachable = false;
		boolean contains4Fields = false;
		List<String> expectedFields = Arrays.asList("firstname", "lastname", "email", "password", "confirmPassword");
			//  - it is reachable
		try {
			String content = asString( web.get("register.jsp") );
			pageReachable = content.contains("html") && web.code() == 200 ;
			
			String action = getTagAttribute( content, "form", "action" );
			if ( action != null && action.contains("register") ) okForm = true;
			
			String nextInput = content;
			ArrayList<String> notFound = new ArrayList<>( expectedFields );
			while ( (nextInput = toNextTag(nextInput, "input")) != null ) {
				String inputName = getTagAttribute(nextInput, "input", "name");
				//System.out.println("input name="+inputName);
				if ( inputName != null && expectedFields.contains(inputName) ) notFound.remove(inputName); 
			}
			contains4Fields = notFound.isEmpty();
		} catch (IOException ioe ) { }
		assertTrue( "register page should be reachable", pageReachable );
			//  - it contains a form
		assertTrue( "register action form is register servlet", okForm );
			//  - it contains 4 fields with firstname, lastname, email, password
		assertTrue( "register contains correct fieldnames", contains4Fields );
	}
		
	@Test
	public void checkSigninServlet() throws IOException {
		// 5. There is a "/register" servlet
		String content = asString( web.get("register.jsp") );
		assertTrue( content.contains("html") && web.code() == 200 );
		
		String userMail2 = "u_" + (Math.random()*10000) + "@test.com";
		HashMap<String, String> data = new HashMap<>(); 
		data.put("firstname", "Test");
		data.put("lastname", "Test");
		data.put("email", userMail2);
		data.put("password", "test");
		data.put("confirmPassword", "test");
		InputStream is = web.post("register", WebTool.encodeMap(data));
		is.close();
		assertEquals( 
			"POST /register should accept new user and redirect to a page with HTTP code 302",
			302, web.code()
		);
	}
	
	private interface MissingFieldHandler {
		public void check(String field, int code, String res, Exception err);
	}
	
	private void doMissingFieldsRequests(MissingFieldHandler cb) {
		List<String> expectedFields = Arrays.asList("firstname", "lastname", "email", "password");

		String content;
		for ( String field : expectedFields ) {
			content = "";
			try {				
				String userMail2 = "u_" + (Math.random()*10000) + "@test.com";
				HashMap<String, String> data = new HashMap<>(); 
				data.put("firstname", "Test");
				data.put("lastname", "Test");
				data.put("email", userMail2);
				data.put("password", "test");
				data.put("confirmPassword", "test");
				data.remove(field);
				
				InputStream is = web.post("register", WebTool.encodeMap(data));
				content = asString( is );
				cb.check(field, web.code(), content, null);
				
			} catch(IOException e) {
				cb.check(field, web.code(), content, e);
			}
		}
		
	}
	
	private String getDivErrorMessage(String content) {
		boolean found = false;
		String tmp = content;
		do {
			tmp = HtmlUtils.toNextTag(tmp, "div");
			if ( tmp == null ) break;
			String role = HtmlUtils.getTagAttribute(tmp, "div", "role");
			found = "alert".equals(role);
		} while ( !found );
		
		if ( !found ) return null;
		
		int i = tmp.indexOf('>');
		int j = tmp.indexOf("</div");
		assertTrue( "Missing </div> ?", j > 0 );
		String msg = tmp.substring(i+1, j);
		
		return msg;
	}
	
	@Test
	public void checkSigninServlet_checkMissingParam() throws IOException {
		//  - it refuses any attempt with empty or null field
		doMissingFieldsRequests( (field, code, content, err) -> {
			System.out.println("Check " + field);
			
			assertEquals("No redirect when field in error",
				200, code
			);
			
			String msg = getDivErrorMessage(content);
			assertNotNull( "No div with role=alert found when error occured", msg );
			msg = msg.toLowerCase();
			
			boolean errorMessage = false;
			switch ( field ) {
			case "firstname":
				errorMessage = msg.contains("firstname") || msg.contains("prénom");
				break;
			case "lastname":
				errorMessage = msg.contains("lastname") || msg.contains("nom");
				break;
			case "email":
				errorMessage = msg.contains("mail");
				break;
			case "password":
				errorMessage = msg.contains("password") || msg.contains("mot de passe");
				break;
			}
			
			if ( !errorMessage ) System.out.println( "\t-- error message was :\n" + msg );
			assertTrue( "Error message do not indicate field '"+field+"' in error", errorMessage );
		} );
		
		String userMail2 = "u_" + (Math.random()*10000) + "@test.com";
		HashMap<String, String> data = new HashMap<>(); 
		data.put("firstname", "Test");
		data.put("lastname", "Test");
		data.put("email", userMail2);
		data.put("password", "test");
		data.put("confirmPassword", "testError");
		
		InputStream is = web.post("register", WebTool.encodeMap(data));
		String content = asString( is );
		is.close();
		boolean ok = web.code() == 200;
		assertTrue( "Register should refuse when password don't match confirmation", ok );
		
		String msg = getDivErrorMessage(content);
		assertNotNull( "No div with role=alert found when error occured", msg );
		msg = msg.toLowerCase();
		
		boolean errorMessage = msg.contains("confirm");
		assertTrue( "Error message do not indicate erroneous password confirmation", errorMessage );
		
	}
	
	@Test
	public void checkSigninServlet_showErrorParam() throws IOException {
	//  - it shows error on empty or null field
		doMissingFieldsRequests( (field, code, content, err) -> {
			assertEquals("Redirect detected on missing param " + field, 200, code);
			int i = content.indexOf("name=\""+field+"\"");
			if ( i > 0 ) {
				int j = 0;
				int found = 0;
				while ( j >= 0 && j < i ) {
					j = content.indexOf("<div", j+1);
					if ( j < 0 ) break;
					
					String klass = getTagAttribute(content.substring(j), "div", "class");
					if ( klass != null && klass.contains("form-group") )
						if ( j < i ) found = j;
				}
				int k = found >= 0 ? content.indexOf('>', found+1) : -1;
				String divString = "";
				if ( k > 0 ) divString = content.substring(found, k);
				if ( !divString.contains("has-error") ) {
					fail( "Field '" + field + "' should be in error when empty");
				}
			}
		} );
	}
	
	@Test
	public void checkSigninServlet_ErrorOnExistingUser() throws IOException {
		//  - it refuses existing user (mail)
		boolean okCheckExists = false;
			
		HashMap<String, String> data = new HashMap<>(); 
		data.put("firstname", "Test");
		data.put("lastname", "Test");
		data.put("email", "mark.zuckerberg@facebook.com");
		data.put("password", "test");
		
		InputStream is = web.post("register", WebTool.encodeMap(data));
		is.close();
		okCheckExists = web.code() == 200;
		assertTrue("register refuses existing user", okCheckExists);
	}
}
