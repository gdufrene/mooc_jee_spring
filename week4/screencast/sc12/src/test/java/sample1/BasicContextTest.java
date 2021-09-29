package sample1;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.eservices.sample1.Application;
import fr.eservices.sample1.Greeter;
import fr.eservices.sample1.Printer;
import fr.eservices.sample1.Welcome;

public class BasicContextTest {
	
	AnnotationConfigApplicationContext context;
	
	@Before
	public void initContext() {
		context = new AnnotationConfigApplicationContext( );
	}
	
	@After
	public void stopContext() {
		context.stop();
	}
	
	@Test
	public void testContext() throws Exception {
		context.scan( "fr.eservices.sample1" );
		context.refresh();
		context.start();
		
		Application app = context.getBean( Application.class );
		Greeter greeter = context.getBean( Greeter.class );
		Printer printer = context.getBean( Printer.class );
		Welcome welcome = context.getBean( Welcome.class );
		
		HashMap<String, Object> components = new HashMap<>();
		components.put("app", app);
		components.put("greeter", greeter);
		components.put("printer", printer);
		components.put("welcome", welcome);
		
		for ( String componentName : components.keySet() ) {
			assertNotNull( "Component " + componentName + " is null", components.get(componentName) );			
		}
		
		for ( String s : new String[]{"greeter", "printer", "welcome"} ) {
			Field f = Application.class.getDeclaredField(s);
			f.setAccessible(true);
			Object ref = f.get(app);
			assertNotNull( "Application Field '" + s + "' is null", ref );
			assertEquals( "Application Field '"+s+"' should be a component from context", components.get(s), ref );
		}
	}

}
