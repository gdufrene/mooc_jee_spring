package web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import static web.HtmlUtils.*;

public class WebTest {
	
	WebTool wt;
	
	@Before
	public void init() {
		String p = System.getProperty("usePort");
		if ( p == null ) p = "8080";
		wt = new WebTool("localhost:"+p, "/exo402");
		System.out.println("Use base url: http://localhost:"+p+"/exo402");
	}
	
	@Test
	public void testRunTomcat() throws Exception {
		assertTrue(
			WebTool.toString(
				wt.get("/test.txt")
			).contains("xai2Aire")
		);
	}
	
	private String getTagWithClass(String content, String tagName, String className) {
		String tmp = content;
		do {
			tmp = toNextTag(tmp, tagName);
			if ( tmp == null ) break;
			String c = getTagAttribute(tmp, tagName, "class");
			if ( c == null ) continue;
			for ( String cn : c.split(" ") ) 
				if  ( cn.equals(className) ) 
					return getTagContent(tmp, tagName);
		} while( tmp != null );
		
		return null;
	}
	
	@Test
	public void testCategories() throws Exception {
		String content = 
			WebTool.toString(
				wt.get("/catalog/categories.html")
			);
		String cats = getTagWithClass(content, "ul", "categories");
		
		List<String> categories = new ArrayList<>(Arrays.asList(
			"Marché", "Boissons", "Maison", "Papeterie & fournitures", "Petit électro & multimédia"
		));
		
		while ( (cats = toNextTag(cats, "li")) != null ) {
			String link = getTagAttribute(cats, "a", "href");
			assertTrue( link.contains("category/") );
			assertTrue( link.endsWith(".html") );
			String img = getTagAttribute(cats, "img", "src");
			assertTrue( img.contains("/img/") );
			assertTrue( img.endsWith(".jpg") );
			String remove = null;
			for ( String catName : categories )
				if ( getTagContent(cats, "li").contains(catName) )
					remove = catName;
			categories.remove(remove);
		}
		
		assertTrue( categories.isEmpty() );
	}
	
	@Test
	public void testCategory() throws Exception {
		
		{
			String content = WebTool.toString(
				wt.get("/catalog/category/1.html")
			);
			String arts = getTagWithClass(content, "ul", "articles");
			assertTrue( findProduct(arts, "Chou romanesco pièce", "2,69", "0P_123578.gif" ) );
			assertTrue( findProduct(arts, "Carotte bio Cat 2", "1,99", "0P_102166.gif" ) );
			assertTrue( findProduct(arts, "Echalote longue traditionnelle", "0,89", "0P_120124.gif" ) );
		}
		
		{
			String content = WebTool.toString(
				wt.get("/catalog/category/2.html")
			);
			String arts = getTagWithClass(content, "ul", "articles");
			assertTrue( findProduct(arts, "Lait demi-écrémé stérilisé UHT", "5,94", "0P_323179.gif" ) );
			assertTrue( findProduct(arts, "Saveurs du soir - Infusion grand sud", "0,90", "0P_16547.gif" ) );
			assertTrue( findProduct(arts, "Popayan - Café moulu de Colombie", "2,99", "0P_16018.gif" ) );
		}
		
		{
			String content = WebTool.toString(
				wt.get("/catalog/category/5.html")
			);
			String arts = getTagWithClass(content, "ul", "articles");
			assertTrue( findProduct(arts, "Réveil Superman", "44,99", "0P_327835.gif" ) );
			assertTrue( findProduct(arts, "Radio réveil AJ1060/12", "34,99", "0P_327814.gif" ) );
			assertTrue( findProduct(arts, "Casque arceau Monitor BT", "249,00", "0P_339662.gif" ) );
		}
		
	}
	
	private boolean findProduct(String content, String name, String price, String img) {
		boolean found = false;
		while ( !found && (content = toNextTag(content, "li")) != null ) {
			String li = getTagContent(content, "li");
			found =
				getTagWithClass(li, "span", "price").contains(price)
				&& getTagAttribute(li, "img", "src").contains(img)
				&& li.contains(name);
			
		}
		return found;
	}


}
