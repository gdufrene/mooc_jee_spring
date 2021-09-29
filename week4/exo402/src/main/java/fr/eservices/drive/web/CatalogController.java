package fr.eservices.drive.web;

import java.util.List;

import org.springframework.ui.Model;

import fr.eservices.drive.dao.CatalogDao;
import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Category;


// set as a Controller
// map to an url starting with "/catalog"
public class CatalogController {
	
	// Inject this with spring
	CatalogDao dao;
	
	// Map this method to "categories.html"
	public String list(Model model) throws DataException {
		List<Category> categories = dao.getCategories();
		// add categories to model 
		// return the view name
		throw new RuntimeException("Not yet implemented");
	}
	
	// Map this method to "category/ID_CATEGORY.html"
	public String categoryContent( Model model,
			// Inject this parameter from url variable
			int id ) 
	throws DataException {
		List<Article> articles = dao.getCategoryContent(id);
		// add articles to model 
		// return the view name
		throw new RuntimeException("Not yet implemented");
	}

}
