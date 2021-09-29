package fr.eservices.sample1;

public class Greeter {
	
	public Greeter() {}

	public String hello( String name ) {
		if ( name == null || name.isEmpty() ) name = "world";
		return "Hello " + name;
	}
	
}
