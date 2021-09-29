package dao;

import javax.persistence.EntityManager;

import flights.Airline;

// TODO: set as component
public class AirlineDao {

	// TODO : inject entity manager
	EntityManager em;
	
	public Airline find(int id) {
		return em.find(Airline.class, id);
	}
	
	
}
