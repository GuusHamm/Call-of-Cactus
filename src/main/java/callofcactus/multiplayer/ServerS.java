package callofcactus.multiplayer;

import callofcactus.MultiPlayerGame;
import callofcactus.entities.Entity;
import callofcactus.entities.HumanCharacter;
import callofcactus.entities.MovingEntity;
import callofcactus.entities.Player;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Wouter Vanmulken on 9-11-2015.
 */
public class ServerS {

    private MultiPlayerGame game;
    private Serializer serializer = new Serializer();

    /**
     * This is the Constructor and runs a constant procces on the server
     * This will eventually become multithreaded but for now it runs one action at a time.
     * @param g
     */
    public ServerS(MultiPlayerGame g, List<String> ips ) {
        for(String ip : ips){

            try {
                players.add(new Socket(ip, 8009));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        game = g;
        new Thread(new Runnable() {

            int count = 0;

            @Override
            public void run() {

                ServerSocket serverSocket = null;
                Socket clientSocket = null;

                try {
                    if (serverSocket == null) {
                        System.out.println("Server is being initialized");
                        serverSocket = new ServerSocket(8008);
                    } else
                        System.out.println("Server was already initailized : Error -------------------------------------------------");

                    while (true) {
                        System.out.println("Will now accept input");
                        clientSocket = serverSocket.accept();
                        System.out.println("---new input---");

                        BufferedReader buffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter out =
                                new PrintWriter(clientSocket.getOutputStream(), true);

                        String input = buffer.readLine();
                        System.out.println("server :" + input);

                        //handles the input and returns the wanted data.
                        Command c = Command.fromString(input);
                        String s = handleInput(c);
                        System.out.println("dit stuurt ie weg :" +s);
                        out.println(s);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start(); // And, start the thread running

        //update the server
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
//                game.spawnAI();
                List<Entity> k = game.getAllEntities();

                game.setAllEntities(k);
                System.out.println("woop woop");
                System.out.println(game.getAllEntities().size());
                //for(Ball b :k){b.update(1000);}

            }
        }, 1000, 1000);
    }



    /**
     * Gets a command and takes the corresponding action for wich method is requested
     * @param command command to set wich action to take.
     * @return
     */
    private String handleInput(Command command) {

        Command returnValue = null;

        switch (command.getMethod()) {
            case GET:
                returnValue = handleInputGET(command);
                break;
            case POST:
                returnValue = handleInputPOST(command);
                break;
            case CHANGE:
                returnValue = handleInputCHANGE(command);
                break;
        }

        return returnValue.toString();
    }

    /**
     * Takes the corresponding action within the GET command
     * @param command
     * @return
     */
    private Command handleInputGET(Command command) {
        //TODO handle differen gets
        Command c = new Command(Command.methods.GET, game.getAllEntities().toArray(), command.getObjectToChange());
        return c;
    }
    /**
     * Takes the corresponding action within the POST command
     * @param command
     * @return
     */
    private Command handleInputPOST(Command command) {

        try {

            Entity[] entities = (Entity[]) command.getObjects();
            for (Entity e : entities) {
                game.addEntityToGame(e);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new Command(Command.methods.FAIL, null, Command.objectEnum.Fail);
        }
        return new Command(Command.methods.SUCCES, null, Command.objectEnum.Succes);


    }
    /**
     * Takes the corresponding action within the POST command
     * @param command
     * @return
     */
    private Command handleInputCHANGE(Command command) {

        try {
            Entity entityFromCommand = (Entity) command.getObjects()[0];
            switch (command.getFieldToChange()) {
                case "location":
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == entityFromCommand.getID()) {
                            //First set lastLocation
                            e.setLastLocation(e.getLocation());
                            //Now for the actual location
                            Vector2 loc = null;
                            String pos = (String) command.getNewValue();

                            int startInd = pos.indexOf("X:") + 2;
                            String aXString = pos.substring(startInd, pos.indexOf(" Y") - startInd);
                            float aXPosition = Float.parseFloat(aXString);
                            startInd = pos.indexOf("Y:") + 2;
                            String aYString = pos.substring(startInd, pos.indexOf("}") - startInd);
                            float aYPosition = Float.parseFloat(aYString);
                            loc = new Vector2(aXPosition, aYPosition);
                            e.setLocation(loc);

                        }
                    }
                    break;
                case "angle":
                    ((Player[]) command.getObjects())[0].setAngle((Integer) command.getNewValue());
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == entityFromCommand.getID()) {
                            Player p = (Player) e;
                            p.setAngle((Integer) command.getNewValue());
                        }
                    }
                    break;

                case "health":
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == entityFromCommand.getID()) {
                            e.setHealth((Integer) command.getNewValue());
                        }
                    }
                    break;

                case "score":
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == entityFromCommand.getID()) {
                            HumanCharacter h = (HumanCharacter) e;
                            h.setScore((Integer) command.getNewValue());
                        }
                    }
                    break;

                case "deathCount":
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == entityFromCommand.getID()) {
                            HumanCharacter h = (HumanCharacter) e;
                            h.setDeathCount((Integer) command.getNewValue());
                        }
                    }
                    break;

                case "killCount":
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == entityFromCommand.getID()) {
                            HumanCharacter h = (HumanCharacter) e;
                            h.setKillCount((Integer) command.getNewValue());
                        }
                    }
                    break;

                case "speed":
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == entityFromCommand.getID()) {
                            MovingEntity me = (MovingEntity) e;
                            me.setSpeed((Integer) command.getNewValue());
                        }
                    }
                    break;

                case "damage":
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == entityFromCommand.getID()) {
                            MovingEntity me = (MovingEntity) e;
                            me.setDamage((Integer) command.getNewValue());
                        }
                    }
                    break;

            }

        }catch (Exception e){

            e.printStackTrace();
            return new Command(Command.methods.FAIL, null, Command.objectEnum.Fail);

        }
        return new Command(Command.methods.SUCCES, null, Command.objectEnum.Succes);
    }



    Socket socket;
    PrintWriter out;
    BufferedReader in;
    List<Socket> players = new ArrayList<>();
    /**
     * Sends a Command to the server and gets a result
     * Return value can be null!!!
     * @param message
     */
    public void sendMessageAndReturnPush(Command message) {

        for( Socket socket: players) {

            String feedback = null;
            try {

                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //Sending message
                out.println(message.toString());
                System.out.println("message sent");

                //Getting the feedback
                feedback = in.readLine();

                while (feedback == "") {
                    System.out.println("test");
                    feedback = in.readLine();
                }
                System.out.println("dit ontvangt de client " + feedback);


            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("client :" + feedback);

//        List<Entity> o = new ArrayList<Entity>();
            Command c = Command.fromString(feedback);
//        for(Object e : c.getObjects()){
//            o.add((Entity) e);
//        }
            System.out.println("we have liftoff!!!");

            handleInputPush(c);
        }
    }

    public void handleInputPush(Command command){
        switch (command.getMethod()){
            case SUCCES:
                break;
            case FAIL:
                break;

        }
    }
}
