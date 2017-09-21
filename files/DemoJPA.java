package jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import api.model.Author;
import api.model.BoardGame;

public class DemoJPA {
	
	EntityManager em;
	EntityManagerFactory emf;
	EntityTransaction tx;
	
	
	public DemoJPA() {
		emf = Persistence.createEntityManagerFactory("myapp");
		em = emf.createEntityManager();
		tx = em.getTransaction();		
	}
	
	public void close() {
		em.close();
		emf.close();
	}
	
	
	long createGame( String gameName, int minPlayer, int maxPlayer, Author... authors ) {
		tx.begin();
		BoardGame board = new BoardGame();
		board.name = gameName;
		board.minPlayer = minPlayer;
		board.maxPlayer = maxPlayer;
		for( Author author : authors ) {
			if ( author.id == 0 ) em.persist( author );
			board.authors.add( author );
		}
		em.persist( board );
		tx.commit();
		
		return board.id;
	}
	
	BoardGame getGame( long id ) {
		return em.find(BoardGame.class, id);
	}
	
	void delete(BoardGame game) {
		tx.begin();
		em.remove( game );
		tx.commit();
	}
	
	List<BoardGame> gameFor_Query( int nbPlayers ) {
		TypedQuery<BoardGame> q = em.createQuery("SELECT g FROM BoardGame g WHERE minPlayer <= :nb AND maxPlayer >= :nb", BoardGame.class);
		q.setParameter("nb", nbPlayers);
		return q.getResultList();
	}
	
	List<BoardGame> gameFor_Criteria( int nbPlayers ) {
		Root<BoardGame> _game;
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BoardGame> criteria = cb.createQuery(BoardGame.class);
		ParameterExpression<Integer> param = cb.parameter(Integer.class);
		
		criteria
			.select( 
				_game = criteria.from(BoardGame.class) 
			)
			.where(
				cb.and(
					cb.le(_game.get("minPlayer"), param),
					cb.ge(_game.get("maxPlayer"), param)
				)
			);
		
		TypedQuery<BoardGame> q = em.createQuery( criteria );
		q.setParameter(param, nbPlayers);
		return q.getResultList();
	}
	


	public static void main(String[] args) {
		DemoJPA jpa = new DemoJPA();

		// persist an object
		long id = jpa.createGame( "Five Tribes", 3, 6, Author.create("Bruno", "Cathala") );
		
		// select ONE object by its ID
		BoardGame game = jpa.getGame( id );
		System.out.println( game );
		
		jpa.createGame( "Carcassone", 2, 5, Author.create("Klaus-Jürgen", "Wrede") );
		jpa.createGame( "Agricola",   1, 6, Author.create("Uwe", "Rosenberg") );
		jpa.createGame( "PowerGrid",  3, 6, Author.create("Friedemann", "Friese") );
		
		// select games with a Query
		List<BoardGame> gamesFor2 = jpa.gameFor_Query( 2 );
		System.out.println("*** Games for 2 players ***");
		for ( BoardGame g : gamesFor2 ) {
			System.out.println( g );
		}
		
		List<BoardGame> gamesFor6 = jpa.gameFor_Criteria( 6 );
		System.out.println("*** Games for 6 players ***");
		for ( BoardGame g : gamesFor6 ) {
			System.out.println( g );
		}
		
		// delete an object 
		jpa.delete( game );
		
		jpa.close();
	}


}
