package game.io;

import account.Account;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Teun
 */
public class ServiceRetrieverTest {

    private ServiceRetriever serviceRetriever;

    @Before
    public void setUp() throws Exception {
        serviceRetriever = new ServiceRetriever();
    }

    @Test
    public void testRetrieveAccount() throws Exception {
        Account account = serviceRetriever.retrieveAccount("TEUN");
    }

    @Test(expected = NullPointerException.class)
    public void testRetrieveAccountNotExists() throws Exception {
        Account account = serviceRetriever.retrieveAccount("ASFDJASKFASJDFKADSJFDKLFJDAS");
    }
}