package game.io;

import account.Account;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Teun
 */
public class ServiceRetriever {

    private static final String url = "https://teunwillems.nl/services/callofcactus/";
    private OkHttpClient okHttpClient;

    /**
     * Creates a reader for you to use
     */
    public ServiceRetriever() {
        okHttpClient = new OkHttpClient();
    }

    /**
     * Gets an account from the server
     * @param username Username to get from database
     * @return Account creates from username data
     * @throws IOException When networking isn't available
     * @throws NullPointerException When the account hasn't been found, this will be thrown
     */
    public Account retrieveAccount(String username) throws IOException, NullPointerException {
        String parameter = addParameterToUrl(url + "account.php", "username", username);

        Request request = new Request.Builder()
                .url(parameter)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        String json = response.body().string();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        }catch (JSONException e) {
            throw new NullPointerException("Account is not found");
        }
        // TODO Add account

        return null;
    }

    /**
     * Adds a get parameter to the url
     * @param url URL to add data to
     * @param key Key to add
     * @param value Value to add
     * @return URL with key and value added
     */
    private String addParameterToUrl(String url, String key, String value) {
        url += (!url.endsWith("?") && !url.contains("?")) ? "?" : "&";

        url += key + "=" +  value;
        return url;
    }

}
