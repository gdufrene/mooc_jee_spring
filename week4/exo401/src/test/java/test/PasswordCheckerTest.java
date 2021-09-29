package test;

import org.junit.Assert;
import org.junit.Test;

import fr.eservices.drive.util.HmacChecker;
import fr.eservices.drive.util.MD5Checker;
import fr.eservices.drive.util.PasswordChecker;

public class PasswordCheckerTest {
	
	@Test
	public void testMD5Checker() {
		PasswordChecker checker = new MD5Checker();
		String encoded = checker.encode("dufrene", "eservices");
		
		Assert.assertEquals("jIl4RROUPnEjMNyndVFLEg==", encoded);
	}
	
	@Test
	public void testHmacChecker() {
		PasswordChecker checker = new HmacChecker();
		String encoded = checker.encode("dufrene", "eservices");
		
		Assert.assertEquals("rs5jfYR3vJdNH5mX4X9APEuP1QM=", encoded);
	}

}
