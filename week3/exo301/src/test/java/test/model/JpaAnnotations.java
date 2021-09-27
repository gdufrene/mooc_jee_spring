package test.model;

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Cart;
import fr.eservices.drive.model.Category;
import fr.eservices.drive.model.Customer;
import fr.eservices.drive.model.Order;
import fr.eservices.drive.model.Perishable;
import fr.eservices.drive.model.Product;
import fr.eservices.drive.model.StatusHistory;


public class JpaAnnotations {

	private static Class<? extends Annotation> 
		idAnnotation,
		entityAnnotation,
		manyToOneAnnotation,
		manyToManyAnnotation,
		oneToManyAnnotation,
		joinColumnAnnotation,
		inheritanceAnnotation,
		temporalAnnotation,
		oneToOneAnnotation;
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void getJpaClasses() throws ClassNotFoundException {
		idAnnotation          = (Class<? extends Annotation>) Class.forName("javax.persistence.Id");
		entityAnnotation      = (Class<? extends Annotation>) Class.forName("javax.persistence.Entity");
		manyToOneAnnotation   = (Class<? extends Annotation>) Class.forName("javax.persistence.ManyToOne");
		manyToManyAnnotation  = (Class<? extends Annotation>) Class.forName("javax.persistence.ManyToMany");
		oneToManyAnnotation   = (Class<? extends Annotation>) Class.forName("javax.persistence.OneToMany");
		joinColumnAnnotation  = (Class<? extends Annotation>) Class.forName("javax.persistence.JoinColumn");
		inheritanceAnnotation = (Class<? extends Annotation>) Class.forName("javax.persistence.Inheritance");
		temporalAnnotation    = (Class<? extends Annotation>) Class.forName("javax.persistence.Temporal");
		oneToOneAnnotation    = (Class<? extends Annotation>) Class.forName("javax.persistence.OneToOne");
	}
	
	@Test public void testCategory() {
		Class<?> k = Category.class;
		assertHasAnnotation( k, entityAnnotation );
		assertHasNbAnnotation( k, 1, idAnnotation );
	}	
	
	@Test public void testArticle() {
		Class<?> k = Article.class;
		assertHasAnnotation( k, entityAnnotation );
		assertHasAnnotation( k, inheritanceAnnotation );
		assertHasNbAnnotation( k, 1, idAnnotation );
		assertHasAssociation( k, List.class, manyToManyAnnotation );
	}	

	@Test public void testPerishable() {
		Class<?> k = Perishable.class;
		assertHasAnnotation( k, entityAnnotation );
		
		assertHasNbAnnotation( k, 0, idAnnotation );
		assertHasNbAnnotation( k, 1, temporalAnnotation );
	}
	
	@Test public void testProduct() {
		Class<?> k = Product.class;
		assertHasAnnotation( k, entityAnnotation );
		assertHasNbAnnotation( k, 0, idAnnotation );
		assertHasNbAnnotation( k, 0, temporalAnnotation );
	}
	
	@Test public void testCart() {
		Class<?> k = Cart.class;
		assertHasAnnotation( k, entityAnnotation );
		assertHasNbAnnotation( k, 1, idAnnotation );
		assertHasNbAnnotation( k, 1, temporalAnnotation );
		
		assertHasAssociation( k, Customer.class, manyToOneAnnotation );
		assertHasAssociation( k, List.class, manyToManyAnnotation );
	}
	
	@Test public void testCustomer() {
		Class<?> k = Customer.class;
		assertHasAnnotation( k, entityAnnotation );
		assertHasNbAnnotation( k, 1, idAnnotation );
		assertHasAssociation( k, Cart.class, oneToOneAnnotation );
	}
	
	@Test public void testOrder() {
		Class<?> k = Order.class;
		assertHasAnnotation( k, entityAnnotation );
		assertHasNbAnnotation( k, 1, idAnnotation );
		assertHasNbAnnotation( k, 2, temporalAnnotation );
		
		assertHasAssociation( k, List.class, manyToManyAnnotation );
		assertHasAssociation( k, Customer.class, manyToOneAnnotation );
		assertHasAssociation( k, List.class, oneToManyAnnotation );
	}
	
	@Test public void testStatusHistory() {
		Class<?> k = StatusHistory.class;
		assertHasAnnotation( k, entityAnnotation );
		assertHasNbAnnotation( k, 1, idAnnotation );
		assertHasNbAnnotation( k, 1, temporalAnnotation );
	}

	private void assertHasAnnotation(Class<?> k, Class<?> annotation) {
		for ( Annotation ann : k.getAnnotations() ) {
			if ( ann.annotationType() == annotation ) return;
		}
		fail( "Class " + k.getName() + " must have annotation @" + annotation.getName() );
	}
	
	private void assertHasNbAnnotation(Class<?> k, int i, Class<?> annotation) {
		int nb = 0;
		for ( Field f : k.getDeclaredFields() ) {
			for ( Annotation ann : f.getAnnotations() ) {
				if ( ann.annotationType() == annotation ) nb++;
			}
		}
		assertEquals("Class " + k.getName() + " must have " + i + " fiels annotated with @" + annotation.getName(), i, nb );
	}
	
	private void assertHasAssociation(Class<?> k, Class<?> type, Class<? extends Annotation> annotation) {
		for ( Field f : k.getDeclaredFields() ) {
			if ( f.getType() != type ) continue;
			if ( f.getAnnotation(annotation) != null ) return;
		}
		fail( "Class " + k.getName() + " must have association @" + annotation.getName() + " to " + type.getName() );
	}
}
