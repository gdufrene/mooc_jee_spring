package test.model;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import org.junit.Test;

import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Cart;
import fr.eservices.drive.model.Category;
import fr.eservices.drive.model.Customer;
import fr.eservices.drive.model.Order;
import fr.eservices.drive.model.Perishable;
import fr.eservices.drive.model.Product;
import fr.eservices.drive.model.StatusHistory;

public class ModelAttributes {
	
	@Test public void testCategory() {
		Class<?> k = Category.class;
		assertHasField( k, "name" );
		assertHasField( k, "orderIdx" );
	}
	
	@Test public void testArticle() {
		Class<?> k = Article.class;
		assertHasField( k, "ean" );
		assertHasField( k, "price" );
		assertHasField( k, "vat" );
		assertHasField( k, "name" );
		assertHasField( k, "categories" );
	}
	
	@Test public void testPerishable() {
		Class<?> k = Perishable.class;
		assertHasField( k, "bestBefore" );
	}
	
	@Test public void testProduct() {
		Class<?> k = Product.class;
	}
	
	@Test public void testCart() {
		Class<?> k = Cart.class;
		assertHasField( k, "createdOn" );
		assertHasField( k, "articles" );
		assertHasField( k, "customer" );
	}
	
	@Test public void testCustomer() {
		Class<?> k = Customer.class;
		assertHasField( k, "login" );
		assertHasField( k, "firstName" );
		assertHasField( k, "lastName" );
		assertHasField( k, "email" );
		assertHasField( k, "password" );
		assertHasField( k, "activeCart" );
	}
	
	@Test public void testOrder() {
		Class<?> k = Order.class;
		assertHasField( k, "createdOn" );
		assertHasField( k, "deliveryDate" );
		assertHasField( k, "amount" );
		assertHasField( k, "articles" );
		assertHasField( k, "history" );
		assertHasField( k, "customer" );
		assertHasField( k, "currentStatus" );
	}
	
	@Test public void testStatusHistory() {
		Class<?> k = StatusHistory.class;
		assertHasField( k, "statusDate" );
		assertHasField( k, "status" );
	}

	private void assertHasField(Class<?> k, String name) {
		for( Field f : k.getDeclaredFields() ) {
			if ( f.getName().equals(name) ) return;
		}
		fail( "Class " + k.getName() + " must have field " + name );
	}
	

}
