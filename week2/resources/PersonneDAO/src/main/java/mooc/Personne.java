package mooc;

/**
 * Classe définissant les données associées à une personne sous la forme d'un
 * bean avec les méthodes accesseurs (getters) et modificateurs (setters)
 * correspondantes.
 * 
 * @author Guillaume Dufrêne <Guillaume.Dufrene@univ-lille1.fr>
 * @author Lionel Seinturier <Lionel.Seinturier@univ-lille1.fr>
 */
public class Personne {

	private int id;
	private String nom;
	private int age;
	
	public Personne( int id, String nom, int age ) {
		this.id = id;
		this.nom = nom;
		this.age = age;
	}
	
	public int getId() { return id; }
	
	public String getNom() { return nom; }
	public void setNom( String nom ) { this.nom = nom; }
	
	public int getAge() { return age; }
	public void setAge( int age ) { this.age = age; }
}
