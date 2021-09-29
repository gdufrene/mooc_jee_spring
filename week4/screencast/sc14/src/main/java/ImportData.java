import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flights.Airline;
import flights.Airport;
import flights.Route;
import flights.RouteId;

public class ImportData {
	
	private static Logger log = LoggerFactory.getLogger(ImportData.class);
	
	private interface DataReader {
		Iterable<String[]> importData(String url);
	}
	
	
	private static DataReader streamDataReader = new DataReader() {
		@Override
		public Iterable<String[]> importData(String url) {
			BufferedReader in;
			try {
				URL u = new URL(url);
				in = new BufferedReader(
					new InputStreamReader(u.openStream(), "UTF-8")
				);
				log.debug("Stream opened to [{}]", url);
			} catch (IOException e) {
				log.error("Unable to open stream from '"+url+"'", e);
				throw new Error("Failed");
			}
			return new Iterable<String[]>() {
				@Override
				public Iterator<String[]> iterator() {
					return new Iterator<String[]>() {
						boolean hasNext = true;
						String line;
						{
							readNext();
						}
						private void readNext() {
							try {
								line = in.readLine();
								log.trace("Read [{}]", line);
								if ( line == null ) {
									in.close();
									log.debug("Stream closed : [{}]", url);
								}
							} catch (IOException e) {
								log.error("Unable to read more data from '"+url+"'", e);
							}
							hasNext = line != null;
						}
						@Override
						public boolean hasNext() {
							return hasNext;
						}
						private String[] type = new String[0];
						@Override
						public String[] next() {
							String res = line;
							readNext();
							if ( res == null ) return null;
							return CSVUtils.parseLine(res).toArray(type);
						}
					};
				}
			};
		}
	};
	
	private interface DataParser<T> {
		public T parse(String str) throws NumberFormatException;
	}
	
	public static String nopParse(String str) throws NumberFormatException {
		return str;
	}
	
	public static Boolean booleanParse(String str) throws NumberFormatException {
		if (str == null) return null;
		if ( str.equals("Y") ) return true;
		if ( str.equals("N") ) return false;
		return null;
	}
	
	public static <T> T parseNull(String str, DataParser<T> parser) throws NumberFormatException {
		if ( str == null || "\\N".equals(str) ) return null;
		return parser.parse(str);
	};
	
	private interface DataMapper<T> {
		public T map(String[] arr);
	}
	
	private static HashSet<Integer> existingAirport = new HashSet<>();
	
	private static Airport mapAirport(String[] data) {
		Airport airport = new Airport();
		
		int i = 0;
		airport.setId( parseNull(data[i++], Integer::parseInt) );
		airport.setName( parseNull(data[i++], ImportData::nopParse) );
		airport.setCity(  parseNull(data[i++], ImportData::nopParse) );
		airport.setCountry(  parseNull(data[i++], ImportData::nopParse) );
		airport.setIata(  parseNull(data[i++], ImportData::nopParse) );
		airport.setIcao(  parseNull(data[i++], ImportData::nopParse) );
		airport.setLatitude(  parseNull(data[i++], Double::parseDouble) );
		airport.setLongitude(  parseNull(data[i++], Double::parseDouble) );
		airport.setAltitude(  parseNull(data[i++], Integer::parseInt) );
		airport.setTimezone(  parseNull(data[i++], Float::parseFloat) );
		airport.setTzName(  parseNull(data[i++], ImportData::nopParse) );
		airport.setSource(  parseNull(data[i++], ImportData::nopParse) );
		
		existingAirport.add(airport.getId());
		
		return airport;
	}
	
	private static HashSet<Integer> existingAirline = new HashSet<>();
	
	private static Airline mapAirline(String[] data) {
		Airline airline = new Airline();
		
		int i = 0;
		airline.setId( parseNull(data[i++], Integer::parseInt) );
		airline.setName( parseNull(data[i++], ImportData::nopParse) );
		airline.setAlias( parseNull(data[i++], ImportData::nopParse) );
		airline.setIata( parseNull(data[i++], ImportData::nopParse) );
		airline.setIcao( parseNull(data[i++], ImportData::nopParse) );
		airline.setCallsign( parseNull(data[i++], ImportData::nopParse) );
		airline.setCountry( parseNull(data[i++], ImportData::nopParse) );
		airline.setActive( parseNull(data[i++], ImportData::booleanParse) );
		
		existingAirline.add(airline.getId());
		
		return airline;
	}
	
	private static Route mapRoute(String[] data) {
		Route route = new Route();
		
		int i = 0;
		
		route.setAirlineCode( parseNull(data[i++], ImportData::nopParse) );
		Airline airline = new Airline();
		Integer airlineId = parseNull(data[i++], Integer::parseInt);
		if ( airlineId == null ) return null;
		if ( !existingAirline.contains(airlineId) ) return null;
		airline.setId( airlineId );
		
		route.setSourceCode(parseNull(data[i++], ImportData::nopParse));
		Airport source = new Airport();
		Integer sourceId = parseNull(data[i++], Integer::parseInt);
		if ( sourceId == null ) return null;
		if ( !existingAirport.contains(sourceId) ) return null;
		source.setId( sourceId );
		
		route.setDestinationCode(parseNull(data[i++], ImportData::nopParse));
		Airport dest = new Airport();
		Integer destId = parseNull(data[i++], Integer::parseInt);
		if ( destId == null ) return null;
		if ( !existingAirport.contains(destId) ) return null;
		dest.setId( destId );
		
		route.setRouteId( new RouteId(airline, source, dest) );
		
		route.setCodeshare( parseNull(data[i++], ImportData::booleanParse) );
		route.setStops(parseNull(data[i++], Short::parseShort)  );
		route.setEquipment(parseNull(data[i++], ImportData::nopParse));
		
		return route;
	}
	
	private static <T> void importFromSource( EntityManager em, DataMapper<T> mapper, String url ) {
		int inserted = 0;
		EntityTransaction transaction = null;
		for( String[] data : streamDataReader.importData(url) ) {
			T obj = mapper.map(data);
			if ( obj == null ) continue;
			if ( transaction == null ) {
				transaction = em.getTransaction();
				transaction.begin();
			}
			em.persist(obj);
			inserted++;
			if ( inserted % 100 == 0 ) {
				log.trace("{} inserts. Flush em, commit tx", inserted);
				em.flush();
				em.clear();
				transaction.commit();
				transaction = null;
			}
		}
		if ( transaction != null ) transaction.commit();
	}
	
	public static void main(String[] args) {
		
		// quickest way to delete tables and data ...
		new File("./db.mv.db").delete();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myApp");
		EntityManager em = emf.createEntityManager();
		
		importFromSource(em, 
			ImportData::mapAirport, 
			"https://raw.githubusercontent.com/jpatokal/openflights/master/data/airports.dat"
		);
		
		importFromSource(em, 
			ImportData::mapAirline, 
			"https://raw.githubusercontent.com/jpatokal/openflights/master/data/airlines.dat"
		);
		
		importFromSource(em, 
			ImportData::mapRoute, 
			"https://raw.githubusercontent.com/jpatokal/openflights/master/data/routes.dat"
		);

		em.close();
		emf.close();
	}

	
	
}
