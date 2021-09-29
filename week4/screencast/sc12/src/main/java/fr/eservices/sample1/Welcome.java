package fr.eservices.sample1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Welcome {
	
	public Welcome() {}
	
	public String askName() {
		System.out.println("Hello !");
		System.out.print("What is your name ? ");
		try {
			return new BufferedReader( new InputStreamReader(System.in) ).readLine();
		} catch( IOException ioe ) {
			System.out.println( "[IOException] - " + ioe.getMessage() );
			return null;
		}
	}
	

}
