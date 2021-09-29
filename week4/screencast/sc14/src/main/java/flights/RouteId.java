package flights;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Embeddable 
public class RouteId implements Serializable {
	private static final long serialVersionUID = -5005548492059390682L;
	
	public RouteId() {
	}
	
	public RouteId(Airline airline, Airport source, Airport destination) {
		this.airline = airline;
		this.source = source;
		this.destination = destination;
	}

	/**
	 * Airline ID 	Unique OpenFlights identifier for airline (see Airline).
	 */
	@ManyToOne
	@Id
	private Airline airline;
	
	/**
	 * Source airport ID 	Unique OpenFlights identifier for source airport (see Airport)
	 */
	@ManyToOne
	@Id
	private Airport source;
	
	/**
	 * Destination airport ID 	Unique OpenFlights identifier for destination airport (see Airport)
	 */
	@ManyToOne
	@Id
	private Airport destination;
	
	@Override
	public int hashCode() {
		return Objects.hash( airline, source, destination );
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( !(obj instanceof RouteId) ) return false;
		RouteId id = (RouteId) obj;
		return Objects.equals(id.airline, airline)
				&& Objects.equals(id.destination, destination)
				&& Objects.equals(id.source, source);
	}
}