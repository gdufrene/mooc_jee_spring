package flights;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Route {
	
	@Id
	private RouteId routeId;

	/**
	 * Airline 	2-letter (IATA) or 3-letter (ICAO) code of the airline.
	 */
	private String airlineCode;

	
	/**
	 * Source airport 	3-letter (IATA) or 4-letter (ICAO) code of the source airport.
	 */
	private String sourceCode;
	

	
	/**
	 * Destination airport 	3-letter (IATA) or 4-letter (ICAO) code of the destination airport.
	 */
	private String destinationCode;
	


	/**
	 * Codeshare 	"Y" if this flight is a codeshare (that is, not operated by Airline, but another carrier), empty otherwise.
	 */
	private Boolean codeshare;
	
	/**
	 * Stops 	Number of stops on this flight ("0" for direct)
	 */
	private Short stops;
	
	/**
	 * Equipment 	3-letter codes for plane type(s) generally used on this flight, separated by spaces
	 */
	private String equipment;

	
	public RouteId getRouteId() {
		return routeId;
	}
	
	public void setRouteId(RouteId routeId) {
		this.routeId = routeId;
	}
	
	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	public Boolean getCodeshare() {
		return codeshare;
	}

	public void setCodeshare(Boolean codeshare) {
		this.codeshare = codeshare;
	}

	public Short getStops() {
		return stops;
	}

	public void setStops(Short stops) {
		this.stops = stops;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	
	
}
