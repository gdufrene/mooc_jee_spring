package fr.eservices.drive.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eservices.drive.dao.UserDao;
import fr.eservices.drive.model.User;

public class UserJDBCDao extends UserDao {
	
	Connection conn;
	
	@Override
	public User find(String login) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("Select * from User where login = ?");
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			boolean found = rs.next();
			if ( !found )
				return null;
			User u = new User();
			u.setLogin( rs.getString("login") );
			u.setPassword( rs.getString("password") );
			u.setFirstname( rs.getString("firstname") );
			u.setLastname( rs.getString("lastname") );
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch(SQLException e) {}
		}
		return null;
	}
	
	@Override
	public void save(User user) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("insert into User(login, password, firstname, lastname) values(?, ?, ?, ?)");
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstname());
			ps.setString(4, user.getLastname());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch(SQLException e) {}
		}
	}
	
	@Override
	protected void doChangePassword(String login, String password) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("update user set password = ? where login = ?");
			ps.setString(1, password);
			ps.setString(2, login);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch(SQLException e) {}
		}
	}
	
	public static void main(String[] args) throws Exception {
		Connection conn = DriverManager.getConnection("jdbc:h2:./db");
		UserJDBCDao dao = new UserJDBCDao();
		dao.conn = conn;

		User u = new User();
		u.setFirstname("Guillaume");
		u.setLastname("Dufrêne");
		u.setLogin("dufrene");
		u.setPassword("eservices");
		dao.save(u);
		
		conn.close();
	}

}
