package spring;

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import fr.eservices.drive.web.AppConfig;
import fr.eservices.drive.web.CatalogController;
import fr.eservices.drive.web.RestHistoryController;

public class SpringContextAnnotationTest {
	
	@Test
	public void testAppConfig() {
		Class<?> k = AppConfig.class;
		assertNotNull(k.getAnnotation(Configuration.class));
		assertNotNull(k.getAnnotation(EnableWebMvc.class));
		assertNotNull(k.getAnnotation(ComponentScan.class));
		
		ComponentScan scan = k.getAnnotation(ComponentScan.class);
		assertThat(asList(scan.basePackages()), hasItems("fr.eservices.drive.dao"));
		assertThat(asList(scan.basePackages()), hasItems("fr.eservices.drive.web"));
		
		boolean found = false;
		for ( String type : getMethodTypeAnnotated(k, Bean.class) ) {
			if ( type.contains("ViewResolver") ) found = true;
		}
		assertTrue("Your AppConfig should contains a ViewResolver", found);
	}
	
	@Test
	public void testCatalogController() {
		Class<?> k = CatalogController.class;
		assertNotNull(k.getAnnotation(Controller.class));
		assertNotNull(k.getAnnotation(RequestMapping.class));
		
		RequestMapping mapping = k.getAnnotation(RequestMapping.class);
		assertThat( asList(mapping.path()), hasItems("/catalog") );
		
		Method listMethod = getMethod(k,"list");
		mapping = listMethod.getAnnotation(RequestMapping.class);
		assertThat( asList(mapping.path()), hasItems("/categories.html") );
		
		Method categoryContentMethod = getMethod(k, "categoryContent");
		mapping = categoryContentMethod.getAnnotation(RequestMapping.class);
		String urlMapping = asList(mapping.path()).get(0);
		assertTrue( urlMapping.startsWith("/category/") && urlMapping.endsWith(".html") );
		PathVariable pv = null;
		String typePv = null;
		for ( Parameter param : categoryContentMethod.getParameters() ) {
			pv = param.getAnnotation(PathVariable.class);
			if ( pv!= null ) {
				typePv = param.getType().getTypeName();
				break;
			}
		}
		assertNotNull(pv);
		assertEquals("int", typePv);
		
		boolean found = false;
		for ( String type : getFieldTypeAnnotated(k, Autowired.class) ) {
			if ( type.contains("CatalogDao") ) found = true;
		}
		assertTrue("You have to Inject CatalogDao in CatalogController", found);
	}
	
	@Test
	public void testRestHistoryController() {
		Class<?> k = RestHistoryController.class;
		assertNotNull(k.getAnnotation(RestController.class));
		assertNotNull(k.getAnnotation(RequestMapping.class));
		
		String urlMapping;
		{
			Method m = getMethod(k, "getHistory");
			assertNotNull(m.getAnnotation(GetMapping.class));
			urlMapping = m.getAnnotation(GetMapping.class).path()[0];
		}
		
		{
			Method m = getMethod(k, "addStatus");
			assertNotNull(m.getAnnotation(PostMapping.class));
			assertEquals(
				"Both getHistory() and addStatus() must have same URL mapping",
				urlMapping, 
				m.getAnnotation(PostMapping.class).path()[0]
			);
		}
		
		boolean found = false;
		for ( String type : getFieldTypeAnnotated(k, Autowired.class) ) {
			if ( type.contains("HistorySource") ) found = true;
		}
		assertTrue("You have to Inject HistorySource in RestHistoryController", found);
	}

	private List<String> getFieldTypeAnnotated(Class<?> k, Class<? extends Annotation> ann) {
		List<String> list = new ArrayList<>();
		for ( Field f : k.getDeclaredFields() ) 
			if ( f.getAnnotation( ann ) != null )
				list.add( f.getType().getSimpleName() );
		return list;
	}
	
	private List<String> getMethodTypeAnnotated(Class<?> k, Class<? extends Annotation> ann) {
		List<String> list = new ArrayList<>();
		for ( Method m : k.getDeclaredMethods() ) 
			if ( m.getAnnotation( ann ) != null )
				list.add( m.getReturnType().getSimpleName() );
		return list;
	}
	
	private Method getMethod(Class<?> k, String name) {
		for (Method m : k.getDeclaredMethods()) {
			if (m.getName().equals(name)) return m;
		}
		return null;
	}
	
}
