package game.io;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;

/**
 * @author Teun
 */
public class PropertyWriter {

    private JSONObject jsonObject;

    public PropertyWriter(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void writeJSONObject() throws FileAlreadyExistsException {
        writeJSONObject("config.json");
    }

    public void writeJSONObject(String filepath) throws FileAlreadyExistsException {
        writeJSONObject(filepath, false);
    }

    public void writeJSONObject(String filepath, boolean overwrite) throws FileAlreadyExistsException {
        ClassLoader loader = PropertyReader.class.getClassLoader();
        File file = new File(loader.getResource(filepath).getFile());

        if (file.exists() && !overwrite)
            throw new FileAlreadyExistsException(file.getAbsolutePath(), null, "Overwrite was set to false, but the file already exists");

        try {
            FileWriter fileWriter = new FileWriter(file);
            jsonObject.write(fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
