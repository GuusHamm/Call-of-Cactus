package game.io;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;

/**
 * @author Teun
 */
public class PropertyWriter {

	private JSONObject jsonObject;

	public PropertyWriter(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	/**
	 * Write the jsonobject to the file
	 *
	 * @throws FileAlreadyExistsException Thrown when file already exists, use other constructor for this
	 */
	public void writeJSONObject() throws FileAlreadyExistsException {
		writeJSONObject("config.json");
	}

	/**
	 * Write the jsonobject to the file
	 *
	 * @param filepath : File to write to, loaded with ClassLoader Resources
	 * @throws FileAlreadyExistsException Thrown when file already exists, use other constructor for this
	 */
	public void writeJSONObject(String filepath) throws FileAlreadyExistsException {
		writeJSONObject(filepath, false, true);
	}

	/**
	 * Write the jsonobject to the file
	 *
	 * @param filepath  : File to write to, loaded with ClassLoader Resources
	 * @param overwrite : Should it write when a file already exists?
	 * @param append    : Option to add jsonobject to file if it exists
	 * @throws FileAlreadyExistsException Thrown when file already found and overwrite is false
	 */
	public void writeJSONObject(String filepath, boolean overwrite, boolean append) throws FileAlreadyExistsException {
		File file = null;
		try {
			file = new File(PropertyWriter.class.getClassLoader().getResource(filepath).toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		if (file.exists() && !overwrite)
			throw new FileAlreadyExistsException(file.getAbsolutePath(), null, "Overwrite was set to false, but the file already exists");

		// Appends two json files together
		if (append) {
			try {
				PropertyReader propertyReader = new PropertyReader(filepath);
				for (String key : JSONObject.getNames(propertyReader.getJsonObject())) {
					jsonObject.put(key, propertyReader.getJsonObject().get(key));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(jsonObject.toString().getBytes());
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
}
