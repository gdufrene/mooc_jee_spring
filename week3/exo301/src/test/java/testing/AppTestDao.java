package testing;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.eservices.drive.dao.CatalogDao;
import fr.eservices.drive.dao.CatalogDaoJPAImpl;
import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Category;

public class AppTestDao {
	
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myApp");
		EntityManager em = emf.createEntityManager();
		
		Thread.setDefaultUncaughtExceptionHandler( new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				emf.close();
			}
		});
		
		CatalogDao dao = new CatalogDaoJPAImpl(em);
		
		System.out.println("List of categories");
		
		List<Category> categories;
		try {
			categories = dao.getCategories();
			for ( Category cat : categories ) {
				System.out.println( cat );
			}
		} catch( DataException e ) {
			e.printStackTrace();
		}
		
		emf.close();
	}

}
