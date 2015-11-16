import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Guus and Wouter on 2015-09-01.
 */
public class TestTemplate {
	//Deze tests als eerste en tijdens ontwikkeling van de klasse
	//1 : Een testClasses die goed moet gaan
	@Test
	public void test1() {
		assertEquals(1, 1);

	}

	//Deze tests als de klasse is gemaakt
	//2 : Een testClasses die exception handeling opvangt

	@Test //(expected = Exception.class)
	public void test2() {
		assertEquals(1, 1);
		assertNull(null);
		assertSame(null, null); //testClasses of lijsten worden gekloond of niet
	}
	//3 : Een testClasses die foute input geeft

	//4 : Een testClasses die mogelijke return waardes testClasses

	//5 : Een testClasses die een NULL-reference als input geeft

}
