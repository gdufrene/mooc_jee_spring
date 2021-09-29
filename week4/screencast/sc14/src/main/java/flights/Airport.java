package flights;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Airport {

	/**
	 * Airport ID 	Unique OpenFlights identifier for this airport.
	 */
	@Id
	private int id;
	
	/**
	* Name 	Name of airport. May or may not contain the City name.
	*/
	private String name;
	
	/**
	 * City 	Main city served by airport. May be spelled differently from Name.
	 */
	private String city;
	
	/**
	 * Country 	Country or territory where airport is located. See countries.dat to cross-reference to ISO 3166-1 codes.
	 */
	private String country;
	
	/**
	 * IATA 	3-letter IATA code. Null if not assigned/unknown.
	 */
	private String iata;
	
	/**
	 * ICAO 	4-letter ICAO code. Null if not assigned.
	 */
	private String icao;
	
	/**
	 * Latitude 	Decimal degrees, usually to six significant digits. Negative is South, positive is North.
	 */
	private Double latitude;

	/**
	 * Longitude 	Decimal degrees, usually to six significant digits. Negative is West, positive is East.
	 */
	private Double longitude;
	
	/**
	 * Altitude 	In feet.
	 */
	private int altitude;

	/**
	 * Timezone 	Hours offset from UTC. Fractional hours are expressed as decimals, eg. India is 5.5.
	 */
	private Float timezone;
	
	/**
	 * DST 	Daylight savings time. One of E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown). See also: Help: Time
	 */
	private Character daylight;
	
	/**
	 * Tz database time zone 	Timezone in "tz" (Olson) format, eg. "America/Los_Angeles".
	 */
	private String tzName;
	
	
	// Type 	Type of the airport. Value "airport" for air terminals, "station" for train stations, "port" for ferry terminals and "unknown" if not known. In airports.csv, only type=airport is included.
	// Type not mapped.
	

	/**
	 * Source 	Source of this data. "OurAirports" for data sourced from OurAirports, "Legacy" for old data not matched to OurAirports (mostly DAFIF), "User" for unverified user contributions. In airports.csv, only source=OurAirports is included.
	 */
	private String source;


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


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
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


	public Double getLatitude() {
		return latitude;
	}


	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public Double getLongitude() {
		return longitude;
	}


	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}


	public int getAltitude() {
		return altitude;
	}


	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}


	public Float getTimezone() {
		return timezone;
	}


	public void setTimezone(Float timezone) {
		this.timezone = timezone;
	}


	public Character getDaylight() {
		return daylight;
	}


	public void setDaylight(Character daylight) {
		this.daylight = daylight;
	}


	public String getTzName() {
		return tzName;
	}


	public void setTzName(String tzName) {
		this.tzName = tzName;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	
}
