package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import flights.Route;

// TODO : expose as component
public class RouteDao {

	// TODO : inject EntityManager
	private EntityManager em;
	
	public List<Route> findRoutesByCountries(String fromCountry, String toCountry) {
		List<Route> res = new ArrayList<>();
		
		// TODO : build a request an return routes

		
		return res;
	}

}
