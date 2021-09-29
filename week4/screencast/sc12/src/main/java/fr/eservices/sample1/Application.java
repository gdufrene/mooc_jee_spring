package fr.eservices.sample1;

public class Application {
	
	Greeter greeter;
	Printer printer;
	Welcome welcome;
	
	public Application() {
		
		// TODO: First. Initialize properties. 
	}
	
	public void run() {
		String name = welcome.askName();
		String response = greeter.hello(name);
		printer.print( response );
	}
	
	public static void main(String[] args) {
		new Application().run();
	}

}
