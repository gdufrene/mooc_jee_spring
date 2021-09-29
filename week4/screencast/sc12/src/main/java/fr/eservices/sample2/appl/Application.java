package fr.eservices.sample2.appl;

import fr.eservices.sample2.api.Greeter;
import fr.eservices.sample2.api.Printer;
import fr.eservices.sample2.api.Welcome;

//TODO Add annotation if required.
public class Application {
	
	Welcome welcome;
	Greeter greeter;
	Printer printer;
	

	public Application() {
	}
	
	public void run() {
		String name = welcome.askName();
		String response = greeter.hello(name);
		printer.print( response );
	}
	
	public static void main(String[] args) {
		// TODO Create a spring context
		// TODO Get Application From context
		// TODO Call run
	}
}
