package user;

public interface UserDao {
	
	/**
	 * Ajoute les informations d'un utilisateur.
	 * L'identifiant est positionné au moment de l'insertion en base,
	 * il ne devrait pas être fourni.
	 * Le mot de passe est persisté tel qu'il est fourni,
	 * un encodage devrait être réalisé en amont.
	 *  
	 * @param user l'utilisateur à ajouter/modifier
	 * @param password le mot de passe encodé
	 */
	void add( User user, String password );
	
	/**
	 * Met à jour les information d'un utilisateur.
	 * Si le mot de passe est null il n'est pas mis à jour.
	 *  
	 * @param user l'utilisateur à ajouter/modifier
	 * @param password nouveau mot de passe encodé si il est fourni.
	 */
	void update( User user, String password );
	
	/**
	 * Recherche un utilisateur par son identifiant.
	 * 
	 * @param id
	 * @return utilisateur, null si non trouvé
	 */
	User find( long id );
	
	/**
	 * Recherche un utilisateur par son email.
	 * 
	 * @param email
	 * @return utilisateur, null si non trouvé
	 */
	public User findByEmail(String email);
	
	/**
	 * Supprime un utilisateur
	 * @param id identifiant de l'utilisateur
	 */
	void delete( long id );
	
	/**
	 * Récupère un utilisateur en effectuant une recherche par email et mot de passe.
	 * @param email adresse mail de l'uditisateur (login)
	 * @param password mot de passe encodé de l'utilisateur.
	 * @return user identifiant de l'utilisateur correspondant (< 0 si aucun ne correspond)
	 */
	long checkPassword( String email, String password );
	
	/**
	 * Recherche un utilisateur par son email.
	 * 
	 * @param email
	 * @return identifiant de l'utilisateur, négatif si non trouvé
	 */
	long exists( String email );

}
