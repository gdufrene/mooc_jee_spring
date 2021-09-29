import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import data.AirlineCoverage;
import flights.Airline;
import flights.Airport;
import flights.Route;

public class AppCriteria {
	private static Logger log = LoggerFactory.getLogger( AppCriteria.class );
	
	public static void main(String argv[]) {
		System.out.println("Running App ...");
		
		log.debug("Create persistence manager");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myApp");
		EntityManager em = emf.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		log.debug("Search for French Airlines");
		List<Airline> airlines;
		{
			CriteriaQuery<Airline> cq = cb.createQuery(Airline.class);
			Root<Airline> al = cq.from(Airline.class);
			cq.where( cb.equal(al.get("country"), "France" ) );
			
			airlines = em.createQuery(cq)
			.getResultList();
		}
		
		showAirlines( airlines );
		

		log.debug("Search for French Airlines going to Miami");
		{
			CriteriaQuery<Airline> cq = cb.createQuery(Airline.class);
			Root<Route> rt = cq.from(Route.class);
			Join<Route, Airline> al = rt.join("airline");
			Join<Route, Airport> ap = rt.join("destination");
			
			
			cq
			  .select( rt.get("airline") ).distinct(true)
			  .where(
				cb.and(
					cb.equal(al.get("country"), "France" ),
					cb.equal(ap.get("city"), "Miami")
				)
			);
			
			airlines = em.createQuery(cq)
			.getResultList();
		}
		
		showAirlines(airlines);

		
		log.debug("Search Number of airlines from CDG");
		{
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<Route> rt = cq.from(Route.class);
			Join<Route, Airport> ap = rt.join("source");
			
			
			cq
			  .select( cb.countDistinct(rt.get("airline")) )
			  .where(
				cb.equal(ap.get("iata"), "CDG")
			  );
			
			long count = em.createQuery(cq)
					.getSingleResult();
			System.out.println(count + " airlines starts at CDG");
		}


		log.debug("Search Best airlines with number of dest airports to USA from CDG, ordered by 'coverage'");
		List<AirlineCoverage> res;
		{
			CriteriaQuery<AirlineCoverage> cq = cb.createQuery(AirlineCoverage.class);
			
			Root<Route> rt = cq.from(Route.class);
			Join<Route, Airport> apD = rt.join("destination");
			Join<Route, Airport> apS = rt.join("source");
			Join<Route, Airline> al = rt.join("airline");
			
			Expression<Long> cnt = cb.count(rt.get("destination"));
			
			cq.select( cb.construct(AirlineCoverage.class, 
					cnt,
					apD.get("country"),
					al.get("name")
				))
				.where( cb.and(
					cb.equal(apD.get("country"), "United States"),
					cb.equal(apS.get("iata"), "CDG")
				))
				.groupBy(
					apD.get("country"),
					al.get("name")
				)
				.orderBy( cb.desc(cnt) );
				
			res = em.createQuery(cq)
					.getResultList();
		}
		
		for ( AirlineCoverage coverage : res ) {
			System.out.println(
				String.format("%3d | %-20s | %-30s", coverage.getNbRoutes(), coverage.getCountry(), coverage.getAirlineName() )
			);
		}
		
		log.debug("Close Entity Manager");
		em.close();
		emf.close();

	}

	private static void showAirlines(List<Airline> airlines) {
		// TODO Auto-generated method stub
		System.out.println(
				String.format("%5s | %3s | %-30s ", 
					"Id",
					"ICAO",
					"Name"
				)
			);
			for ( Airline airline : airlines ) {
				System.out.println(
					String.format("%5d | %3s | %-30s ", 
						airline.getId(),
						airline.getIcao(),
						airline.getName()
					)
				);
			}
	}
}