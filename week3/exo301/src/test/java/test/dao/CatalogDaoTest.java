package test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipFile;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.eservices.drive.dao.CatalogDao;
import fr.eservices.drive.dao.CatalogDaoJPAImpl;
import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Category;
import fr.eservices.drive.model.Perishable;


public class CatalogDaoTest {
	
	CatalogDao dao;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	static EntityManagerFactory emf;
	
	@BeforeClass
	public static void initEmf() throws IOException {
		ZipFile zipFile = new ZipFile(new File("./test_db.zip"));
		InputStream is = zipFile.getInputStream(zipFile.getEntry("test_db.mv.db"));
		FileOutputStream out = new FileOutputStream(new File("test_db.mv.db"));
		byte[] buffer = new byte[64000];
		int read;
		while ( (read = is.read(buffer)) > 0 ) {
			out.write(buffer, 0, read);
		}
		out.close();
		is.close();
		zipFile.close();
		Map<String, String> params = new HashMap<>();
		params.put("javax.persistence.jdbc.url", "jdbc:h2:./test_db");
		emf = Persistence.createEntityManagerFactory("myApp", params);
	}
	
	@AfterClass
	public static void closeEmf() {
		emf.close();
	}
	
	@Before
	public void setupDao() {
		dao = new CatalogDaoJPAImpl(emf.createEntityManager());
	}

	@Test
	public void test_getCategories() throws Exception {
		List<Category> cats = dao.getCategories();
		assertNotNull( cats );
		assertEquals(5, cats.size());
		int previousIdx = Integer.MIN_VALUE;
		for ( Category c : cats ) {
			assertTrue( previousIdx <= c.getOrderIdx() );
			previousIdx = c.getOrderIdx();
		}
	}
	
	@Test
	public void test_getArticleCategories_match() throws Exception {
		List<Category> cats = dao.getArticleCategories(123043);
		assertNotNull( cats );
		assertEquals(3, cats.size());
		List<String> names = new ArrayList<>();
		for ( Category c : cats ) names.add(c.getName());
		assertTrue( names.contains("Marché") );
		assertTrue( names.contains("Boissons") );
		assertTrue( names.contains("Maison") );
	}
	
	@Test
	public void test_getArticleCategories_none() throws Exception {
		List<Category> cats = dao.getArticleCategories(130321);
		assertNotNull( cats );
		assertEquals(0, cats.size());
	}
	
	@Test(expected=DataException.class)
	public void test_getArticleCategories_null() throws Exception {
		dao.getArticleCategories( Integer.MAX_VALUE );
	}
	
	@Test
	public void test_getCategoryContent_match() throws Exception {
		List<Article> articles = dao.getCategoryContent(5);
		assertNotNull( articles );
		assertEquals(5, articles.size());
		List<String> names = new ArrayList<>();
		for ( Article art : articles ) names.add(art.getName());
		assertTrue( names.contains("Balladeur MP3 8 Go BWZ-NWE394L bleu") );
		assertTrue( names.contains("Radio numérique blanche") );
		assertTrue( names.contains("Casque arceau noir MDRZX110") );
		assertTrue( names.contains("Radio CD Cars") );
		assertTrue( names.contains("Réveil Dark Vador") );
	}
	
	@Test
	public void test_getCategoryContent_none() throws Exception {
		List<Article> articles = dao.getCategoryContent(4);
		assertNotNull( articles );
		assertEquals(0, articles.size());
	}
	
	@Test(expected=DataException.class)
	public void test_getCategoryContent_null() throws Exception {
		dao.getCategoryContent( Integer.MAX_VALUE );
	}
	
	@Test
	public void test_getPerished_some() throws Exception {
		List<Perishable> list = dao.getPerished( sdf.parse("2017-12-20") );
		assertNotNull(list);
		assertEquals(112, list.size());
		List<String> names = new ArrayList<>();
		for ( Article art : list ) names.add(art.getName());
		assertTrue( names.contains("Potage de carottes et panais bio") );
		assertTrue( names.contains("Raisin Blanc variété Ideal cat 1") );
		assertTrue( names.contains("Lait bio demi-écrémé") );
		assertTrue( names.contains("GrandLait - Lait demi-écrémé") );
	}
		
	@Test
	public void test_getPerished_none() throws Exception {
		List<Perishable> list = dao.getPerished( sdf.parse("2017-09-01") );
		assertNotNull(list);
		assertEquals(0, list.size());
	}
	
	@Test(expected=DataException.class)
	public void test_getPerished_tooMany() throws Exception {
		dao.getPerished( sdf.parse("2018-07-01") );
	}
	
}
