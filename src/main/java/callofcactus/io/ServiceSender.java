package callofcactus.io;

import account.Account;
import com.squareup.okhttp.OkHttpClient;

/**
 * @author Teun
 */
public class ServiceSender {

    private OkHttpClient okHttpClient;

    /**
     * Creates a object which you use to send data to the server
     */
    public ServiceSender() {
        okHttpClient = new OkHttpClient();
    }

    /**
     * Creates an account, only if that account doesn't exist yet
     * @param account Account to create, filled with password
     * @return True if succesful, false if otherwise
     */
    public boolean createAccount(Account account) {

        return false;
    }

}
