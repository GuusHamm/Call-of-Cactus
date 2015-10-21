package game.io;

import org.json.JSONObject;

import java.io.*;

/**
 * @author Teun
 */
public class PropertyReader
{
    private JSONObject jsonObject;

    /**
     * Reads the default config file, needs to be present in the resources folder
     * @throws IOException
     */
    public PropertyReader() throws IOException {
        ClassLoader loader = PropertyReader.class.getClassLoader();
        InputStream inputStream = loader.getResourceAsStream("config.json");
        if (inputStream == null) {
            throw new FileNotFoundException("File config.json not found in the resources folder!");
        }else{
            read(inputStream);
        }

    }

    /**
     * Reads a file as JSON, the filename is references from the PropertyReader class
     * @param filename The file to target
     * @throws IOException
     */
    public PropertyReader(String filename) throws IOException
    {
        ClassLoader loader = PropertyReader.class.getClassLoader();
        InputStream inputStream = loader.getResourceAsStream(filename);
        if (inputStream == null) {
            throw new FileNotFoundException("File " + filename + " not found in the resources folder!");
        }else{
            read(inputStream);
        }
    }

    private void read(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        jsonObject = new JSONObject(stringBuilder.toString());
    }

    public JSONObject getJsonObject()
    {
        return jsonObject;
    }
}
