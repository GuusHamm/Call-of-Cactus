package callofcactus.multiplayer;

import callofcactus.MultiPlayerGame;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Wouter Vanmulken on 7-12-2015.
 */
public class CommunicationTest {

    ServerS serverS;
    ClientS clientS;
    ClientSideServer clientSideServer;

    @BeforeClass
    public void setUp() throws Exception {
        serverS = new ServerS(new MultiPlayerGame());
        clientS = new ClientS();
        clientSideServer = new ClientSideServer();
    }

    @Test
    public void testSendCommand() {
        new Command(Command.methods.GET,null);
    }


}
