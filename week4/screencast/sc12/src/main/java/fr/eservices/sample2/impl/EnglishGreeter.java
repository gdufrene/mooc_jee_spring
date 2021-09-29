package fr.eservices.sample2.impl;

import fr.eservices.sample2.api.Greeter;

public class EnglishGreeter implements Greeter {
	
	@Override
	public String hello(String name) {
		return new fr.eservices.sample1.Greeter().hello(name);
	}

}
