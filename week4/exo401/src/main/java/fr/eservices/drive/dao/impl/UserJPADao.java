package fr.eservices.drive.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.eservices.drive.dao.UserDao;
import fr.eservices.drive.model.User;

public class UserJPADao extends UserDao {
	
	EntityManager em;
	
	@Override
	public User find(String login) {
		return em.find(User.class, login);
	}
	
	@Override
	public void save(User user) {
		em.persist(user);
	}
	
	@Override
	public void doChangePassword(String login, String password) {
		em.createQuery("update User set password = :PWD where login = :LOGIN")
			.setParameter("LOGIN", login)	
			.setParameter("PWD", password)
			.executeUpdate();
	}
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myApp");
		UserJPADao dao = new UserJPADao();
		dao.em = emf.createEntityManager();
		
		User u = new User();
		u.setFirstname("Guillaume");
		u.setLastname("Dufrêne");
		u.setLogin("dufrene");
		u.setPassword("eservices");
		dao.save(u);
		
		emf.close();
	}

}
