package callofcactus.multiplayer;

import callofcactus.entities.Entity;
import callofcactus.entities.HumanCharacter;
import callofcactus.entities.MovingEntity;
import callofcactus.entities.NotMovingEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * Created by woute on 23-11-2015.
 */
public class ClientS {

    Socket socket;
    PrintWriter out;
    BufferedReader in;

    public ClientS(){

        try {
            socket = new Socket("127.0.0.1", 8008);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ClientS cs2 = new ClientS();
        cs2.sendMessage("kkkk");
    }

    public void sendMessage(String message){
        try{

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader( new InputStreamReader(socket.getInputStream()) );
        out.println(message);

        }catch (Exception e){e.printStackTrace();}

    }
    public void sendMessageAndReturn(String message){
        try{

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader( new InputStreamReader(socket.getInputStream()) );
            out.println(message);

        }catch (Exception e){e.printStackTrace();}

    }
    public List<Entity> getAllEntities()
    {

        return null;
    }
    public List<HumanCharacter> getLatestUpdatesPlayers(List<HumanCharacter> entitiesToUpdate)
    {


        return entitiesToUpdate;
    }
    public List<MovingEntity> getLatestUpdatesMovingEntities(List<MovingEntity> entitiesToUpdate)
    {

        return entitiesToUpdate;
    }
    public List<NotMovingEntity> getLatestUpdatesNotMovingEntities(List<NotMovingEntity> entitiesToUpdate)
    {

        return entitiesToUpdate;
    }

}
