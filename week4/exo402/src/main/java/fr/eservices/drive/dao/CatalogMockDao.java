package fr.eservices.drive.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Category;
import fr.eservices.drive.model.Perishable;

@Component
@Qualifier("mock")
public class CatalogMockDao implements CatalogDao {
	
	private static String 
		CATEGORY_FILE = "/data/category.csv",
		ARTICLE_FILE = "/data/article.csv";
	
	List<Category> categories;
	List<Article> articles;
	
	private static <T> List<T> load(Class<T> type, String dataFile) {
		try {
			CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
			CsvMapper mapper = new CsvMapper();
			File file = new ClassPathResource(dataFile).getFile();
			MappingIterator<T> readValues = 
					mapper.readerFor(type)
					.with(bootstrapSchema)
					.readValues( new InputStreamReader(new FileInputStream(file),"UTF-8") );
			return readValues.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error occurred while loading category list from file " + CATEGORY_FILE);
			return Collections.emptyList();
		}
	}
	
	public CatalogMockDao() {
		categories = load(Category.class, CATEGORY_FILE);
		articles = load(Article.class, ARTICLE_FILE);
		
		// Fix article category
		int i = 0;
		for (Article art : articles) {
			i++;
			if ( i < 100 ) art.setCat_id("1");
			else if ( i < 498 ) art.setCat_id("2");
			else if ( i < 598 ) art.setCat_id("3");
			else if ( i < 691 ) art.setCat_id("4");
			else art.setCat_id("5");
		}
	}
	
	@Override
	public List<Category> getArticleCategories(int articleId) throws DataException {
		for ( Article art : articles )
			if ( art.getId().equals(""+articleId) ) 
				return Arrays.asList( findCategory(art.getCat_id()) );
		return Collections.emptyList();
	}
	
	private Category findCategory(String catId) {
		for ( Category cat : categories ) 
			if ( catId.equals(cat.getId())) 
				return cat;
		return null;
	}

	@Override
	public List<Category> getCategories() throws DataException {
		return categories;
	}
	
	@Override
	public List<Article> getCategoryContent(int categoryId) throws DataException {
		List<Article> res = new ArrayList<>();
		for ( Article art : articles )
			if ( art.getCat_id().equals(""+categoryId) ) res.add(art);
		return res;
	}
	
	@Override
	public List<Perishable> getPerished(Date day) throws DataException {
		throw new DataException("Not implemented in this mock");
	}

	public static void main(String[] args) throws Exception {
		CatalogMockDao dao = new CatalogMockDao();
		for ( Category cat : dao.getCategories() ) {
			System.out.println( cat.getId() + "\t" + cat.getName() );
			for ( Article art : dao.getCategoryContent( Integer.parseInt(cat.getId())) )
				System.out.println( "\t#"+art.getId() + "\t" + art.getName() );
		}
	}
}
