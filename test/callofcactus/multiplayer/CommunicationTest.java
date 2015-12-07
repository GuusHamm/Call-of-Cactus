package callofcactus.multiplayer;

import callofcactus.Administration;
import callofcactus.MultiPlayerGame;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Wouter Vanmulken on 7-12-2015.
 */
public class CommunicationTest {

    ServerS serverS;
    ClientS clientS;
    ClientSideServer clientSideServer;
    MultiPlayerGame multiPlayerGame;
    Administration administration;

    @BeforeClass
    public void setUp(){

        serverS = new ServerS(new MultiPlayerGame());
        clientS = new ClientS();
        clientSideServer = new ClientSideServer();
        multiPlayerGame = new MultiPlayerGame();
        multiPlayerGame.addSinglePlayerHumanCharacter();
        administration = Administration.getInstance();
    }

    @Test
    public void testSendCommand() {


        clientS.sendMessageAndReturn(new Command(Command.methods.GET, null, Command.objectEnum.Entity));
        Assert.assertEquals(administration.getAllEntities(), multiPlayerGame.getAllEntities() );



//        clientS.sendMessageAndReturn(new Command(Command.methods.POST,new Object[]{
//                new Bullet(multiPlayerGame,new Vector2(10,10),multiPlayerGame.getPlayer(),1,1, GameTexture.texturesEnum.bulletTexture,10,10,10)
//        }));
//        clientS.sendMessageAndReturn(new Command(Command.methods.CHANGE,));

    }


}
