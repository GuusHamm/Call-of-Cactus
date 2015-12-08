package callofcactus.multiplayer;

import callofcactus.Administration;
import callofcactus.GameTexture;
import callofcactus.MultiPlayerGame;
import callofcactus.entities.NotMovingEntity;
import com.badlogic.gdx.math.Vector2;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Wouter Vanmulken on 7-12-2015.
 */
public class CommunicationTest {

    ServerS serverS;
    ClientS clientS;
    ClientSideServer clientSideServer;
    MultiPlayerGame multiPlayerGame;
    Administration administration;

    @Before
    public void setUp(){

        List<String> ips = new ArrayList<>();
        ips.add("127.0.0.1");
        serverS = new ServerS(new MultiPlayerGame(), ips);
        clientS = new ClientS();
        clientSideServer = new ClientSideServer();
        multiPlayerGame = new MultiPlayerGame();

        administration = Administration.getInstance();
    }

    @Test
    public void testSendCommand() {

        new NotMovingEntity(multiPlayerGame, new Vector2(0, 0), true, 10, false, GameTexture.texturesEnum.wallTexture, 10, 10);

        clientS.sendMessageAndReturn(new Command(Command.methods.GET, null, Command.objectEnum.Entity));

        System.out.println("serverside : " + multiPlayerGame.getAllEntities().size() + "; ClientSide : " + administration.getAllEntities().size());

        Assert.assertEquals(administration.getAllEntities(), multiPlayerGame.getAllEntities() );



//        clientS.sendMessageAndReturn(new Command(Command.methods.POST,new Object[]{
//                new Bullet(multiPlayerGame,new Vector2(10,10),multiPlayerGame.getPlayer(),1,1, GameTexture.texturesEnum.bulletTexture,10,10,10)
//        }));
//        clientS.sendMessageAndReturn(new Command(Command.methods.CHANGE,));

    }


}
