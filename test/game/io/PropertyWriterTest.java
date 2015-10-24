package game.io;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.FileAlreadyExistsException;

/**
 * @author Teun
 */
public class PropertyWriterTest {

    private PropertyWriter propertyWriter;

    @Before
    public void setUp() throws Exception {
        JSONObject testObject = new JSONObject();
        testObject.put("testString", "testString123");
        testObject.put("testInt", 42);
        testObject.put("testdouble", 420.0);
        testObject.put("testfloat", 1337f);
        propertyWriter = new PropertyWriter(testObject);
        propertyWriter.setJsonObject(testObject);
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void testWriteJSONObject() throws Exception {
        propertyWriter.writeJSONObject();
    }

    @Test
    public void testWriteJSONObject1() throws Exception {
        propertyWriter.writeJSONObject("config.json", true);
    }
}