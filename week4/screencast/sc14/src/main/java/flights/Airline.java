package flights;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Airline {
	
	@Id
	/**
	 * Airline ID 	Unique OpenFlights identifier for this airline.
	 */
	private int id;
	
	
	/**
	 * Name 	Name of the airline.
	 */
	private String name;
	
	/**
	 * Alias 	Alias of the airline. 
	 * For example, All Nippon Airways is commonly known as "ANA".
	 */
	private String alias;
	
	/**
	 * IATA 	2-letter IATA code, if available.
	 */
	@Column(scale=2)
	private String iata;
	
	/**
	 * ICAO 	3-letter ICAO code, if available.
	 */
	private String icao;
	
	/**
	 * Callsign 	Airline callsign.
	 */
	private String callsign;
	
	/**
	 * Country 	Country or territory where airline is incorporated.
	 */
	private String country;
	
	/**
	 * Active 	"Y" if the airline is or has until recently been operational
	 */
	private Boolean active;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public String getIcao() {
		return icao;
	}

	public void setIcao(String icao) {
		this.icao = icao;
	}

	public String getCallsign() {
		return callsign;
	}

	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	

}
