package callofcactus.io;

import callofcactus.BaseTest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

/**
 * @author Teun
 */
public class PropertyReaderTest extends BaseTest {
	private PropertyReader propertyReader;

	@Before
	public void setUp() throws Exception {
		propertyReader = new PropertyReader();
	}

	@Test(expected = FileNotFoundException.class)
	public void testConstructor() throws Exception {
		try{

			new PropertyReader("eenfilemeteenhelelangenaamdienietbestaat.json");
			fail();
		}
		catch(FileNotFoundException e){

		}
	}

	@Test
	public void testGetJsonObject() throws Exception {
		new PropertyReader("config.json");
		JSONObject jsonObject = propertyReader.getJsonObject();
		JSONObject testSettings = jsonObject.getJSONObject("testSettings");
		assertNotNull(testSettings.getJSONArray("testArray"));
		assertEquals("testString123", testSettings.getString("testString"));
	}
}