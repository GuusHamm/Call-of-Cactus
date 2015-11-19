package callofcactus.io;

import account.Account;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by Teun on 17-11-2015.
 */
public class ServiceSenderTest {

    private ServiceSender serviceSender;

    @Before
    public void setUp() throws Exception {
        serviceSender = new ServiceSender();
    }

    @Test
    public void testCreateAccount() throws Exception {
        Account a = new Account("TEUN");
        assertFalse("Account shouldn't be created", serviceSender.createAccount(a));
    }
}