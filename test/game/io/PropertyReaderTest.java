package game.io;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Teun
 */
public class PropertyReaderTest
{
    private PropertyReader propertyReader;

    @Before
    public void setUp() throws Exception
    {
        propertyReader = new PropertyReader();
    }

    @Test(expected = FileNotFoundException.class)
    public void testConstructor() throws Exception {
        new PropertyReader("eenfilemeteenhelelangenaamdienietbestaat.json");
    }

    @Test
    public void testGetJsonObject() throws Exception
    {
        JSONObject jsonObject = propertyReader.getJsonObject();
        JSONObject testSettings =jsonObject.getJSONObject("testSettings");
        assertNotNull(testSettings.getJSONArray("testArray"));
        assertEquals("testString123", testSettings.getString("testString"));
    }
}