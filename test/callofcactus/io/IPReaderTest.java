package callofcactus.io;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Teun on 8-12-2015.
 */
public class IPReaderTest {

    @Test
    public void testReadIP() throws Exception {
        System.out.println("Test your ip yourself");
        System.out.println(new IPReader().readIP().getIp());
}
}