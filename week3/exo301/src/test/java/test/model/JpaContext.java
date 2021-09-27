package test.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.BeforeClass;
import org.junit.Test;

public class JpaContext {
	
	private static Class<?> 
		persistanceClass;
		
	@BeforeClass
	public static void setJpaClasses() throws ClassNotFoundException {
		persistanceClass = Class.forName("javax.persistence.Persistence");
	}

	@Test
	public void getPersistanceContext() throws Exception {
		Object emf = callStatic( persistanceClass, "createEntityManagerFactory", new Object[]{"myApp"} );
		Object em = call(emf, "createEntityManager", null);
		Object tx = call(em, "getTransaction", null);
		assertNotNull( tx );
	}

	private Object callStatic(Class<?> k, String methodName, Object[] args) throws Exception {
		Method method = null;
		Class<?>[] argTypes = new Class<?>[ args.length ];
		for (int i = 0; i < args.length; i++) {
			argTypes[i] = args[i].getClass();
		}
		method = k.getMethod(methodName, argTypes);
		if ( method == null ) fail("No such method "+ methodName + " on " + k.getName() );
		try {
			return method.invoke(null, args);
		} catch( InvocationTargetException e ) {
			throw (Exception) e.getTargetException();
		} catch( IllegalAccessException iae ) {
			fail( "IllegalAccessException " + k.getName() + "." + methodName + "()" );
			return null;
		}
	}
	
	private Object call(Object o, String methodName, Object[] args) throws Exception {
		Method method = null;
		Class<?>[] argTypes;
		Class<?> k = o.getClass();
		if ( args == null ) {
			argTypes = new Class<?>[0];
		} else { 
			argTypes = new Class<?>[ args.length ];
			for (int i = 0; i < args.length; i++) {
				argTypes[i] = args[i].getClass();
			}
		}
		method = k.getMethod(methodName, argTypes);
		if ( method == null ) fail("No such method "+ methodName + " on " + k.getName() );
		
		try {
			return method.invoke(o, (Object[]) null);
		} catch( InvocationTargetException e ) {
			throw (Exception) e.getTargetException();
		} catch( IllegalAccessException iae ) {
			fail( "IllegalAccessException " + k.getName() + "." + methodName + "()" );
			return null;
		}
	}
	
	
}
