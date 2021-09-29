package fr.eservices.sample2.impl;

import fr.eservices.sample2.api.Welcome;

public class ConsoleWelcome implements Welcome {

	@Override
	public String askName() {
		return new fr.eservices.sample1.Welcome().askName();
	}
	
}
