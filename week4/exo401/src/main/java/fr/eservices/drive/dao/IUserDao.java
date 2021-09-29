package fr.eservices.drive.dao;

import fr.eservices.drive.model.User;

public interface IUserDao {
	
	public User find( String login );
	
	public void save( User user );
	
	public boolean tryLogin( String login, String password );
	
	public void setPassword( String login, String password );

}
