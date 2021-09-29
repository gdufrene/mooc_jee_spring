package test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import fr.eservices.drive.app.ChangePwdApp;
import fr.eservices.drive.app.SpringConfig;
import fr.eservices.drive.dao.UserDao;
import fr.eservices.drive.dao.impl.UserJPADao;
import fr.eservices.drive.util.HmacChecker;
import fr.eservices.drive.util.MD5Checker;

public class AnnotationContextTest {
	
	@Test
	public void testComponentAnnotations() {
		
		for (Class<?> k : Arrays.asList(ChangePwdApp.class, UserJPADao.class, HmacChecker.class, MD5Checker.class) )
			Assert.assertNotNull(
				"Class " + k.getName() + " should be a Component", 
				k.getAnnotation( Component.class ) );
	}
	
	@Test
	public void testSpringConfig() {
		Class<?> k = SpringConfig.class;
		Assert.assertNotNull(k.getAnnotation(Configuration.class));
		Assert.assertNotNull(k.getAnnotation(ComponentScan.class));
		List<String> scan = Arrays.asList(k.getAnnotation(ComponentScan.class).basePackages());
		Assert.assertTrue(scan.contains("fr.eservices.drive.dao.impl"));
		Assert.assertTrue(scan.contains("fr.eservices.drive.util"));
		Assert.assertTrue(scan.contains("fr.eservices.drive.app"));
		
		List<String> exposedBean = new ArrayList<>();
		for ( Method m : k.getDeclaredMethods() ) 
			if ( m.getAnnotation( Bean.class ) != null )
				exposedBean.add( m.getReturnType().getSimpleName() );
		Assert.assertTrue(exposedBean.contains("EntityManager"));
	}
	
	@Test
	public void testDistinctComponents() {
		Qualifier q1 = HmacChecker.class.getAnnotation(Qualifier.class);
		Qualifier q2 = MD5Checker.class.getAnnotation(Qualifier.class);
		Assert.assertNotNull(q1);
		Assert.assertNotNull(q2);
		Assert.assertNotEquals(q1, q2);
	}
	
	private List<String> getFieldTypeAnnotated(Class<?> k, Class<? extends Annotation> ann) {
		List<String> list = new ArrayList<>();
		for ( Field f : k.getDeclaredFields() ) 
			if ( f.getAnnotation( ann ) != null )
				list.add( f.getType().getSimpleName() );
		return list;
	}
	
	@Test
	public void testInjections() {
		Assert.assertTrue(
			getFieldTypeAnnotated(ChangePwdApp.class, Autowired.class).contains("IUserDao")
		);
		Assert.assertTrue(
			getFieldTypeAnnotated(UserJPADao.class, Autowired.class).contains("EntityManager")
		);
		Assert.assertTrue(
			getFieldTypeAnnotated(UserDao.class, Autowired.class).contains("PasswordChecker")
		);
	}

}
