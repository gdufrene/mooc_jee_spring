package fr.eservices.sample2.impl;

import fr.eservices.sample2.api.Printer;

public class ConsolePrinter implements Printer {
	
	@Override
	public void print(String message) {
		new fr.eservices.sample1.Printer().print(message);
	}

}
