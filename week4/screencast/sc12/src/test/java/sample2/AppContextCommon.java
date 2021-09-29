package sample2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

public class AppContextCommon {
	
	ApplicationContext context;
	
	protected void hasComponents( Class<?> klass, int nb ) {
		assertEquals(
			"Context should contain " + nb + " beans of type " + klass.getName(),
			nb, context.getBeansOfType( klass ).values().size() 
		);
	}
	
	protected void hasComponentQualified( String name, Class<?> klass ) {
		for ( Object o : context.getBeansOfType( klass ).values() ) {
			if ( name.equals( o.getClass().getAnnotation(Qualifier.class).value() ) ) return;
		}
		fail( "A component " + klass.getName() + " should be qualified " + name );
	}
	
	protected void hasComponent( Class<?> klass ) {
		hasComponents(klass, 1);
	}

}
