package fr.eservices.sample2.impl;

import fr.eservices.sample2.api.Greeter;

public class FrenchGreeter implements Greeter {
	
	@Override
	public String hello(String name) {
		if ( name == null || name.isEmpty() ) name = " tout le monde !";
		return "Bonjour " + name;
	}

}
