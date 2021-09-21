import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import user.UserDao;
import user.User;

public class TestUserJDBC {

	UserDao dao;
	
	User testUser;
	
	@Before
	public void init() throws Exception {
		dao = TestHelper.createUserDao();
		testUser = createUser("test1", "test2");
	}
	
	@Before
	@After
	public void clear() throws Exception {
		TestHelper.updateDb("delete from users where email like '%@test.com'");
	}
	
	private User createUser(String firstname, String lastname) {
		User u = new User();
		u.setEmail(firstname+"."+lastname+"@test.com");
		u.setFirstname(firstname);
		u.setLastname(lastname);
		return u;
	}
	
	private boolean oneLineExists(String query) throws SQLException {
		ResultSet rs = TestHelper
				.getConnection()
				.createStatement()
				.executeQuery(query);
		boolean res = rs.next();
		rs.close();
		return res;
	}
	
	private String oneCol(String query) throws SQLException {
		ResultSet rs = TestHelper
				.getConnection()
				.createStatement()
				.executeQuery(query);
		boolean next = rs.next();
		if ( !next ) {
			rs.close();
			return null;
		}
		String res = rs.getString(1); 
		rs.close();
		return res;
	}
	
	@Test
	public void create_Nominal() throws SQLException {
		assertFalse(
			oneLineExists("select 1 from users where email = 'A.B@test.com'")
		);
		dao.add( createUser("A", "B"), "C" );
		assertTrue(
			oneLineExists("select 1 from users where email = 'A.B@test.com'")
		);
	}
	
	@Test
	public void update_Nominal() throws SQLException {
		TestHelper.updateDb("insert into users(email, password) values('test1.test2@test.com', 'testPwd')");
		int id = Integer.parseInt(
				oneCol( "select id from users where email = 'test1.test2@test.com'" )
			);
		testUser.setId(id);
		testUser.setFirstname("newPrenom");
		testUser.setLastname("newNom");
		dao.update(testUser, "newPassword");
		assertTrue(
			oneLineExists("select 1 from users where email = 'test1.test2@test.com' and firstname = 'newPrenom'")
		);
		assertTrue(
			oneLineExists("select 1 from users where email = 'test1.test2@test.com' and lastname = 'newNom'")
		);
		assertTrue(
			oneLineExists("select 1 from users where email = 'test1.test2@test.com' and password = 'newPassword'")
		);
	}
	
	@Test
	public void find_Nominal() {
		User user = dao.findByEmail("mark.zuckerberg@facebook.com");
		assertEquals("Mark", user.getFirstname());
		assertEquals("Zuckerberg", user.getLastname());
		assertEquals("mark.zuckerberg@facebook.com", user.getEmail());
	}
	
	
	@Test
	public void delete_Nominal() throws SQLException {
		TestHelper.updateDb("insert into users(email) values('test1.test2@test.com')");
		int id = Integer.parseInt(
			oneCol( "select id from users where email = 'test1.test2@test.com'" )
		);
		dao.delete(id);
		assertFalse(
			oneLineExists("select 1 from users where email = 'test1.test2@test.com'")
		);
	}
	
	@Test
	public void checkPassword_Nominal() throws SQLException {
		TestHelper.updateDb("insert into users(email, password) values('test1.test2@test.com', 'testPwd')");
 		assertTrue( dao.checkPassword("test1.test2@test.com", "testPwd") > 0 );
 		assertFalse( dao.checkPassword("test1.test2@test.com", "__testPwd__") > 0 );
	}
	
	@Test
	public void checkPassword_Inject1() throws SQLException {
		TestHelper.updateDb("insert into users(email, password) values('test1.test2@test.com', 'testPwd')");
 		assertFalse( dao.checkPassword("test1.test2@test.com", "' OR '1' = '1") > 0 );
	}
	
	@Test
	public void checkPassword_Inject2() throws SQLException {
		TestHelper.updateDb("insert into users(email, password) values('test1.test2@test.com', 'testPwd')");
 		assertFalse( dao.checkPassword("noMatch' UNION SELECT 'anypassword', 'anypassword', 'anypassword', 'anypassword", "anypassword") > 0 );
	}
	
	
}
