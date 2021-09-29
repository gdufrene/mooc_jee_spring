package sample2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.eservices.sample2.api.Greeter;
import fr.eservices.sample2.api.Printer;
import fr.eservices.sample2.api.Welcome;
import fr.eservices.sample2.appl.Application;
import fr.eservices.sample2.impl.ConsolePrinter;
import fr.eservices.sample2.impl.FrenchGreeter;
import fr.eservices.sample2.impl.SwingWelcome;

public class XmlContextTest extends AppContextCommon {

	@Before
	public void initContext() {
		context = new ClassPathXmlApplicationContext("classpath:/application-context.xml");
	}
	
	@After
	public void stopContext() {
		((ClassPathXmlApplicationContext) context).stop();
	}
	
	@Test
	public void testXmlContext() {
		hasComponents(Application.class, 1);
		
		hasComponents(Greeter.class, 1);
		hasComponents(Welcome.class, 1);
		hasComponents(Printer.class, 1);
		
		hasComponents(SwingWelcome.class, 1);
		hasComponents(FrenchGreeter.class, 1);
		hasComponents(ConsolePrinter.class, 1);
	}
}
