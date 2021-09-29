package fr.eservices.drive.app;

import javax.persistence.EntityManager;


// use this class as a configuration class for spring context
// set a scan package to get JPA DAO and Hmac password checker
public class SpringConfig {

	// expose this as a bean for spring context
	// expose an entity manager for DAO using JPA
	EntityManager entityManager() {
		return null;
	}
	

}
