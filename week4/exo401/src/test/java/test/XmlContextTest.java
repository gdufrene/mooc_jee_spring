package test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlContextTest {
	
	static Document doc;
	static Map<String, String> typePerId = new HashMap<>();
	
	
	@BeforeClass
	public static void loadDocument() throws Exception {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(new File("src/main/resources/application-context.xml"));
		
		NodeList l = doc.getChildNodes();
		assertNotNull(l);
		assertTrue( l.getLength() > 0 );
		assertEquals("beans", l.item(0).getNodeName());
		l = l.item(0).getChildNodes();
		for( int i = 0; i < l.getLength(); i++ ) {
			Node n = l.item(i);
			if ( "bean".equals(n.getNodeName()) ) {
				typePerId.put(
						n.getAttributes().getNamedItem("id").getNodeValue(),
						n.getAttributes().getNamedItem("class").getNodeValue()
				);
			}
		}
		
	}
	
	@Test
	public void testComponents() {
		List<String> components = new ArrayList<>( typePerId.values() ); 
		assertTrue( components.contains("fr.eservices.drive.app.ChangePwdApp") );
		assertTrue( components.contains("fr.eservices.drive.util.MD5Checker") );
		assertTrue( components.contains("fr.eservices.drive.dao.impl.UserJDBCDao") );
		assertTrue( components.contains("java.sql.DriverManager") );
	}
	
	private Node getNodePerType(String componentType) {
		NodeList l = doc.getChildNodes();
		l = l.item(0).getChildNodes();
		for( int i = 0; i < l.getLength(); i++ ) {
			Node n = l.item(i);
			NamedNodeMap m = n.getAttributes();
			if ( m == null ) continue;
			Node attr = m.getNamedItem("class");
			if ( attr == null ) continue;
			if ( componentType.equals(attr.getNodeValue()) ) return n;
		}
		return null;
	}
	
	private List<String> injectedTypes( String componentType ) {
		List<String> list = new ArrayList<>( );
		Node n = getNodePerType( componentType );
		NodeList l = n.getChildNodes();
		for( int j = 0; j < l.getLength(); j++ ) {
			Node p = l.item(j);
			if ( !"property".equals(p.getNodeName()) ) continue;
			String type = typePerId.get(
					p.getAttributes().getNamedItem("ref").getNodeValue()
			);
			list.add( type );
		}
		return list;
	}
	
	@Test
	public void testInjections() {
		{
			List<String> injections = injectedTypes("fr.eservices.drive.app.ChangePwdApp");
			assertTrue( injections.contains("fr.eservices.drive.dao.impl.UserJDBCDao") );
		}
		{
			List<String> injections = injectedTypes("fr.eservices.drive.dao.impl.UserJDBCDao");
			assertTrue( injections.contains("java.sql.DriverManager") );
			assertTrue( injections.contains("fr.eservices.drive.util.MD5Checker") );
		}
	}
	
	@Test
	public void testFactoryAndDestroy() {
		Node n = getNodePerType("java.sql.DriverManager");
		assertEquals("getConnection", n.getAttributes().getNamedItem("factory-method").getNodeValue() );
		
		n = getNodePerType("fr.eservices.drive.dao.impl.UserJDBCDao");
		assertNotNull( n.getAttributes().getNamedItem("destroy-method") );
	}
	

}
