package data;

public class AirlineCoverage {

	long nbRoutes;
	String country;
	String airlineName;
	
	
	
	public AirlineCoverage(long nbRoutes, String country, String airlineName) {
		super();
		this.nbRoutes = nbRoutes;
		this.country = country;
		this.airlineName = airlineName;
	}
	
	public long getNbRoutes() {
		return nbRoutes;
	}
	public void setNbRoutes(long nbRoutes) {
		this.nbRoutes = nbRoutes;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	
	
}
