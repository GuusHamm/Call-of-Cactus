package callofcactus.multiplayer;

import callofcactus.Administration;
import callofcactus.entities.*;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by o on 7-12-2015.
 */
public class ClientSideServer {

    private Administration administration = Administration.getInstance();
    private Serializer serializer = new Serializer();
//    private List<String> ipAdresses;

    /**
     * This is the Constructor and runs a constant procces on the server
     * This will eventually become multithreaded but for now it runs one action at a time.
     *
     */
    public ClientSideServer() {
        System.out.println("ClientSideServer has been innitialized");
        new Thread(new Runnable() {

            int count = 0;

            @Override
            public void run() {

                ServerSocket serverSocket = null;
                Socket clientSocket = null;

                try {
                    if (serverSocket == null) {
                        System.out.println("Server is being initialized");
                        serverSocket = new ServerSocket(8009);
                    } else {
                        System.out.println("Server was already initailized : Error -------------------------------------------------");
                    }

                    while (true) {
                        System.out.println("Will now accept input");
                        clientSocket = serverSocket.accept();
                        System.out.println("\n---new input---");

                        BufferedReader buffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter out =
                                new PrintWriter(clientSocket.getOutputStream(), true);

                        String input = buffer.readLine();
                        System.out.println("server :" + input);

                        Command c = Command.fromString(input);
                        new Thread(() -> { handleInput(c); }).start();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        serverSocket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    new ClientSideServer();
                }

            }
        }).start(); // And, start the thread running
    }


    /**
     * Gets a command and takes the corresponding action for wich method is requested
     *
     * @param command command to set wich action to take.
     * @return
     */
    private void handleInput(Command command) {

        switch (command.getMethod()) {
            //this should never get a GET command
            case POST:
                handleInputPOST(command);
                break;
            case CHANGE:
                handleInputCHANGE(command);
                break;
        }
    }
     /**
     * Takes the corresponding action within the POST command
     *
     * @param command
     * @return
     */
    private Command handleInputPOST(Command command) {

        try {
            Entity[] entities = (Entity[]) command.getObjects();
            for (Entity e : entities) {
                administration.addEntity(e);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * Takes the corresponding action within the POST command
     *
     * @param command
     * @return
     */
    private void handleInputCHANGE(Command command) {

        int ID = command.getID();
        try {
            switch (command.getFieldToChange()) {
                case "location":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            //First set lastLocation
                            e.setLastLocation(e.getLocation());
                            //Now for the actual location
                            String position = (String) command.getNewValue();
                            String[] pos = position.split(";");

                            e.setLocation(new Vector2(Float.parseFloat(pos[0]), Float.parseFloat(pos[1])));
//                            administration.replaceMovingeEntity((MovingEntity) ID                       System.out.println("new location :"+ e.getLocation());
                        }
                    }
                    break;
                case "angle":
//                    System.out.println("This should be players :"+ ((MovingEntity)command.getObjects()[0]).getClass());
//                    ((Player) command.getObjects()[0]).setAngle(Integer.parseInt( command.getNewValue().toString() ));
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            Player p = (Player) e;
                            p.setAngle(Integer.parseInt(command.getNewValue().toString()));
                        }
                    }
                    break;

                case "health":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            e.setHealth((Integer) command.getNewValue());
                        }
                    }
                    break;

                case "score":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            HumanCharacter h = (HumanCharacter) e;
                            h.setScore((Integer) command.getNewValue());
                        }
                    }
                    break;

                case "deathCount":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            HumanCharacter h = (HumanCharacter) e;
                            h.setDeathCount((Integer) command.getNewValue());
                        }
                    }
                    break;

                case "killCount":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            HumanCharacter h = (HumanCharacter) e;
                            h.setKillCount((Integer) command.getNewValue());
                        }
                    }
                    break;

                case "speed":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            MovingEntity me = (MovingEntity) e;
                            me.setSpeed((Integer) command.getNewValue());
                        }
                    }
                    break;

                case "damage":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            MovingEntity me = (MovingEntity) e;
                            me.setDamage((Integer) command.getNewValue());
                        }
                    }
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
