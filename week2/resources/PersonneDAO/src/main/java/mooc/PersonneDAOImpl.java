package mooc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe DAO mettant en oeuvre les méthodes CRUD pour la gestion des personnes.
 * 
 * @author Guillaume Dufrêne <Guillaume.Dufrene@univ-lille1.fr>
 * @author Lionel Seinturier <Lionel.Seinturier@univ-lille1.fr>
 */
public class PersonneDAOImpl implements PersonneDAO {

	private Map<Integer,Personne> personnes = new HashMap<>();
	
	@Override
	public void add(Personne p) {
		personnes.put( p.getId(), p );
	}

	@Override
	public Personne find(int id) {
		return personnes.get(id);
	}

	@Override
	public List<Personne> findOlderThan(int age) {
		List<Personne> l =
			personnes.values().stream().
			filter( p -> p.getAge() >= age ).
			collect(Collectors.toList());
		return l;
	}

	@Override
	public List<Personne> findAll() {
		List<Personne> l =
			personnes.values().stream().
			collect(Collectors.toList());
		return l;
	}

	@Override
	public boolean update(Personne p) {
		if( ! personnes.containsKey(p.getId())) {
			return false;
		}
		add(p);
		return true;
	}

	@Override
	public void delete(int id) {
		personnes.remove(id);
	}
}
