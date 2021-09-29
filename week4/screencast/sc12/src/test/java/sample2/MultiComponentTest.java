package sample2;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.eservices.sample2.api.Greeter;
import fr.eservices.sample2.api.Printer;
import fr.eservices.sample2.api.Welcome;
import fr.eservices.sample2.impl.ConsolePrinter;
import fr.eservices.sample2.impl.ConsoleWelcome;
import fr.eservices.sample2.impl.EnglishGreeter;

public class MultiComponentTest extends AppContextCommon {

	@Before
	public void initContext() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext( );
		context.scan( "fr.eservices.sample2.impl" );
		context.refresh();
		context.start();
		
		this.context = context;
	}
	
	@After
	public void stopContext() {
		((AnnotationConfigApplicationContext) context).stop();
	}
	

	@Test
	public void hasBasicComponents() throws Exception {
		hasComponent( EnglishGreeter.class );
		hasComponent( ConsoleWelcome.class );
		hasComponent( ConsolePrinter.class );
	}
	
	@Test
	public void hasManyComponents() throws Exception {
		hasComponents( Greeter.class, 2 );
		hasComponents( Printer.class, 2 );
		hasComponents( Welcome.class, 2 );
	}
	
	@Test
	public void hasProperQualifier() throws Exception {
		hasComponentQualified("swing", Welcome.class);
		hasComponentQualified("console", Welcome.class);
		
		hasComponentQualified("english", Greeter.class);
		hasComponentQualified("french", Greeter.class);
		
		hasComponentQualified("swing", Printer.class);
		hasComponentQualified("console", Printer.class);
	}
	
}
