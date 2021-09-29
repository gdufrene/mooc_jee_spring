package spring;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.ui.ExtendedModelMap;

import fr.eservices.drive.dao.CatalogDao;
import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.dao.StatusHistory;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Category;
import fr.eservices.drive.model.Perishable;
import fr.eservices.drive.web.CatalogController;
import fr.eservices.drive.web.HistorySource;
import fr.eservices.drive.web.RestHistoryController;

public class ControllerBehaviorTest {
	
	ConfigurableApplicationContext ctx;
	List<Article> articles = Arrays.asList( );
	List<Category> categories = Arrays.asList( );
	List<StatusHistory> statusHistories = Arrays.asList( );
	
	
	public class CatalogDaoMock implements CatalogDao {
		public CatalogDaoMock() {}
		@Override public List<Perishable> getPerished(Date day) throws DataException { return null; }
		@Override public List<Article> getCategoryContent(int categoryId) throws DataException { return articles; }
		@Override public List<Category> getCategories() throws DataException { return categories; }
		@Override public List<Category> getArticleCategories(int articleId) throws DataException { return categories; }
	}
	
	private int hit_addHistoryStatus = 0;
	public class HistorySourceMock implements HistorySource {
		public HistorySourceMock() {}
		@Override public List<StatusHistory> orderHistory(int orderId) { return statusHistories; }
		@Override public void addHistoryStatus(int orderId, StatusHistory statusHistory) throws DataException {
			hit_addHistoryStatus++;
			if ( orderId == 666 ) throw new DataException("Excepted");
		}
	}
	
	@Before
	public void setupSpringContext() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ConfigurableListableBeanFactory factory = ctx.getBeanFactory(); 
		factory.registerSingleton("daoCatalog", new CatalogDaoMock());
		ctx.register(CatalogController.class);
		factory.registerSingleton("historySource", new HistorySourceMock());
		ctx.register(RestHistoryController.class);
		ctx.refresh();
		
		this.ctx = ctx;
		
		hit_addHistoryStatus = 0;
	}
	
	@After
	public void closeContext() {
		ctx.close();
	}
	
	@Test
	public void testCatalogController() throws Exception {
		CatalogController ctrl = ctx.getBean(CatalogController.class);
		
		{
			ExtendedModelMap m = new ExtendedModelMap();
			String view = ctrl.categoryContent(m, 1);
			assertThat( m.values(), hasItems(articles));
			assertEquals("category", view);
		}

		{
			ExtendedModelMap m = new ExtendedModelMap();
			String view = ctrl.list(m);
			assertThat( m.values(), hasItems(categories));
			assertEquals("categories", view);
		}
	}
	
	@Test
	public void testRestHistoryController() throws Exception {
		RestHistoryController ctrl = ctx.getBean(RestHistoryController.class);
		
		{
			List<StatusHistory> lst = ctrl.getHistory(1);
			assertEquals(statusHistories, lst);
		}

		{
			hit_addHistoryStatus = 0;
			String result = ctrl.addStatus(1, null);
			assertEquals(1, hit_addHistoryStatus);
			assertEquals("Ok", result);
		}
		
		{
			hit_addHistoryStatus = 0;
			String result = ctrl.addStatus(666, null);
			assertEquals(1, hit_addHistoryStatus);
			assertEquals("Error", result);
		}
	}

}
