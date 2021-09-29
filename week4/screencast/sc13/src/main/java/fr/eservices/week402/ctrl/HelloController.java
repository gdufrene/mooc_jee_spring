package fr.eservices.week402.ctrl;

// TODO: Annotation to expose this controller
public class HelloController {
	
	// TODO: Annotaiton to map this method to /hello !
	public String sayHello(
			String name
	) {
		String message = "Hello " + name + " !";
		// TODO: pass message to view ...
		return "/sample.jsp";
	}

}
