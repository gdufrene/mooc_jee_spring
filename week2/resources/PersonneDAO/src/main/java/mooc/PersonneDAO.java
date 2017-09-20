package mooc;

import java.util.List;

/**
 * Interface DAO définissant les méthodes CRUD pour la gestion des personnes.
 * 
 * @author Guillaume Dufrêne <Guillaume.Dufrene@univ-lille1.fr>
 * @author Lionel Seinturier <Lionel.Seinturier@univ-lille1.fr>
 */
public interface PersonneDAO {

	/**
	 * Ajout d'une personne. Si une personne avec le même identifiant existe, la
	 * nouvelle personne remplace l'ancienne.
	 * 
	 * @param p  la personne à ajouter
	 */
	void add( Personne p );
	
	/**
	 * Recherche d'une personne à partir de son identifiant.
	 * 
	 * @param id  l'identifiant de la personne à rechercher
	 * @retun     la personne ou <code>null</code> si l'identifiant n'existe pas
	 */
	Personne find( int id );
	
	/**
	 * Retourne la liste des personnes dont l'âge est supérieur ou égal à la
	 * valeur fournie.
	 * 
	 * @param age  la limite d'âge pour les personnes que l'on recherche
	 * @return     la liste des personnes dont l'âge est supérieur ou égal
	 */
	List<Personne> findOlderThan( int age );
	
	/**
	 * Retourne toutes les personnes.
	 * 
	 * @return  la liste de toutes les personnes.
	 */
	List<Personne> findAll();
	
	/**
	 * Met à jour les informations relatives à une personne. Retourne
	 * <code>false</code> et ne fait pas de mise à jour si la personne n'existe
	 * pas. Retourne <code>true</code> sinon.
	 * 
	 * @param p  les informations de la personne
	 * @return   <code>false</code> si la personne n'existe pas,
	 * 			 <code>true</code> sinon
	 */
	boolean update( Personne p );
	
	/**
	 * Supprime la personne dont l'identifiant est fourni.
	 * 
	 * @param id  l'identifiant de la personne à supprimer.
	 */
	void delete( int id );
}
