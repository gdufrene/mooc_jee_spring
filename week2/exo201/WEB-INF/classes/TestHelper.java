import java.sql.Connection;
import java.sql.SQLException;

import user.UserDao;
import user.UserDaoSqlite;

public class TestHelper {
	
	private static Connection connection;
	
	private static class TestUserDao extends UserDaoSqlite {
		TestUserDao(String str) throws SQLException {
			super(str);
			TestHelper.connection = this.conn;
		}
	}
	
	
	private static TestUserDao dao = null;
	
	public static UserDao createUserDao() throws SQLException {
		if ( dao != null ) return dao;
		
		dao = new TestUserDao( "../users.db" );
		return dao;
	}
	
	public static int updateDb(String sql) throws SQLException {
		
		if ( connection == null ) createUserDao();
		
		return connection
			.createStatement()
			.executeUpdate(sql);
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
}
