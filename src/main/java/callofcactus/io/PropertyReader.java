package callofcactus.io;

import org.json.JSONObject;

import java.io.*;

/**
 * @author Teun
 */
public class PropertyReader {
	public static final String PLAYER_HEALTH = "playerBaseHealth";
	public static final String PLAYER_DAMAGE = "playerBaseDamage";
	public static final String PLAYER_SPEED = "playerBaseSpeed";
	public static final String PLAYER_FIRERATE = "playerBaseFireRate";
	public static final String BULLET_SPEED = "bulletBaseSpeed";
	public static final String SPAWN_RADIUS = "baseSpawnRadius";
	public static final String PICKUP_PER_WAVE = "pickupPerWave";


	private JSONObject jsonObject;

	/**
	 * Reads the default config file, needs to be present in the resources folder
	 *
	 * @throws IOException Thrown when file couldn't be loaded
	 */
	public PropertyReader() throws IOException {
		ClassLoader loader = PropertyReader.class.getClassLoader();
		InputStream inputStream = loader.getResourceAsStream("config.json");
		read(inputStream);
	}

	/**
	 * Reads a file as JSON, the filename is references from the PropertyReader class
	 *
	 * @param filename The file to target
	 * @throws IOException Thrown when file couldn't be loaded
	 */
	public PropertyReader(String filename) throws IOException {
		ClassLoader loader = PropertyReader.class.getClassLoader();
		InputStream inputStream = loader.getResourceAsStream(filename);
		read(inputStream);
	}

	/**
	 * @param inputStream The stream that will be read
	 * @throws IOException Thrown when file couldn't be loaded
	 */
	private void read(InputStream inputStream) throws IOException {
		if (inputStream == null)
			throw new FileNotFoundException("File could not be found, did you mark the resources folder?");

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder stringBuilder = new StringBuilder();

		String line;
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
		}

		jsonObject = new JSONObject(stringBuilder.toString());
	}

	/**
	 * @return the current jsonObject
	 */
	public JSONObject getJsonObject() {
		return jsonObject;
	}
}
