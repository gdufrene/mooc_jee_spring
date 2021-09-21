package user;

import java.sql.*;

public class UserDaoSqlite implements UserDao {
	
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			throw new Error(e);
		}
	}
	
	protected Connection conn;
	public UserDaoSqlite( String userFilePath ) throws SQLException {
		
		String jdbcUrl = "jdbc:sqlite:";
		// TODO : complete JDBC URL and initialize a connection.
		this.conn = null;
		throw new RuntimeException("not yet implemented");
	}
	
	@Override
	public void add(User user, String password) {
		// TODO : create a user
		throw new RuntimeException("not yet implemented");
	}
	
	@Override
	public void update(User user, String password) {
		// TODO : update user information in DB
		throw new RuntimeException("not yet implemented");
	}
	
	@Override
	public User find(long id) {
		// TODO : get user data by its ID and map to User object 
		throw new RuntimeException("not yet implemented");
	}
	
	@Override
	public User findByEmail(String email) {
		// TODO : get user data by its ID and map to User object 
		throw new RuntimeException("not yet implemented");
	}
	
	@Override
	public long checkPassword(String email, String password) {
		// TODO : get user id that match, return -1 if none
		throw new RuntimeException("not yet implemented");
	}
	
	@Override
	public void delete(long id) {
		// TODO : delete a user that match this ID
		throw new RuntimeException("not yet implemented");
	}
	
	@Override
	public long exists(String email) {
		// TODO : check if user with that mail exists
		throw new RuntimeException("not yet implemented");
	}
	
	

}
