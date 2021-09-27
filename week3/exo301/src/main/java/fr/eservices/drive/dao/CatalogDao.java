package fr.eservices.drive.dao;

import java.util.Date;
import java.util.List;

import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Category;
import fr.eservices.drive.model.Perishable;

public interface CatalogDao {
	
	/**
	 * List categories, ordered by order_idx
	 * 
	 * @return list of all categories
	 */
	List<Category> getCategories( ) throws DataException;
	
	/**
	 * List categories associated to an article
	 * 
	 * @return categories of article, can be empty
	 * @throws DataException if article does not exist
	 */
	List<Category> getArticleCategories( int articleId ) throws DataException;
	
	/**
	 * List all articles in a category
	 * 
	 * @return articles, can be empty
	 * @throws DataException if category does not exist
	 */
	List<Article> getCategoryContent( int categoryId ) throws DataException;
	
	/**
	 * List perished article considering a defined day.
	 * 
	 * @param day for perishable date to compare with
	 * @return perished article, can be empty
	 * @throws DataException if more than 200 perishable exits
	 */
	List<Perishable> getPerished( Date day ) throws DataException;
	

}
