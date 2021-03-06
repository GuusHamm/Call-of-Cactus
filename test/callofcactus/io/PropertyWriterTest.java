package callofcactus.io;

import callofcactus.BaseTest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.FileAlreadyExistsException;

/**
 * @author Teun
 */
public class PropertyWriterTest extends BaseTest {

	private PropertyWriter propertyWriter;

	@Before
	public void setUp() throws Exception {
		JSONObject testObject = new JSONObject();

		JSONObject testSettings = new JSONObject();
		testSettings.put("testInt", 42);
		testSettings.put("testString", "testString123");
		testSettings.put("testDouble", 42.42);
		testSettings.put("testArray", new int[]{42, 42});

		testObject.put("testSettings", testSettings);
		propertyWriter = new PropertyWriter(testObject);
		propertyWriter.setJsonObject(testObject);
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void testWriteJSONObject() throws Exception {
		try{

			propertyWriter.writeJSONObject();
			fail();
		}
		catch(FileAlreadyExistsException e){

		}
	}

	@Test
	public void testWriteJSONObject1() throws Exception {
		propertyWriter.writeJSONObject("config.json", true, true);
	}
}