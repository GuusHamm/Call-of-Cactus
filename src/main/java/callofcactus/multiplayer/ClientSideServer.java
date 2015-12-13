package callofcactus.multiplayer;

import callofcactus.Administration;
import callofcactus.entities.*;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by o on 7-12-2015.
 */
public class ClientSideServer {

    private Administration administration = Administration.getInstance();
    private Serializer serializer = new Serializer();
    private CommandQueue commandQueue;
//    private List<String> ipAdresses;

    /**
     * This is the Constructor and runs a constant procces on the server
     * This will eventually become multithreaded but for now it runs one action at a time.
     *
     */
    public ClientSideServer() {
        System.out.println("ClientSideServer has been innitialized");
        this.commandQueue = new CommandQueue();
        new Thread(new Runnable() {

            int count = 0;

            @Override
            public void run() {

                ServerSocket serverSocket = null;
                Socket clientSocket = null;
                try {
                    if (serverSocket == null) {
//                        System.out.println("ClientSideServer is being initialized");
                        serverSocket = new ServerSocket(8009);
                    } else {
                        System.out.println("ClientSideServer was already initailized : Error -------------------------------------------------");
                    }

                    while (true) {
                        System.out.println("Will now accept input (ClientSideServer)");
                        clientSocket = serverSocket.accept();
                        try {
                            System.out.println("\n---new input---(ClientSideServer)");

                            BufferedReader buffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            String input = buffer.readLine();
                            buffer.close();

                            System.out.println("ClientSideServer :" + input);
                            Command c = Command.fromString(input);
                            commandQueue.addCommand(c);
                        }catch (Exception e){e.printStackTrace();}
                        finally {
//                            clientSocket.close();
                        }

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

        new Thread(() -> {
            for (;;) {
                Command c;
                while ((c = commandQueue.getNext()) != null) {
                    handleInput(c);
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
    int counter=0;
    /**
     * Takes the corresponding action within the POST command
     *
     * @param command
     * @return
     */
    private void handleInputCHANGE(Command command) {

        int ID = command.getID();
        try {
            counter++;
            System.out.println("counter:" + counter);
            switch (command.getFieldToChange()) {
                case "location":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            //First set lastLocation
                            e.setLastLocation(e.getLocation());
                            //Now for the actual location
                            String position = (String) command.getNewValue();
                            String[] pos = position.split(";");
                            e.setLocation(new Vector2(Float.parseFloat(pos[0]), Float.parseFloat(pos[1])), true);


                            System.out.println();


//                            administration.replaceMovingeEntity((MovingEntity) ID                       System.out.println("new location :"+ e.getLocation());
                        }
                    }
                    break;
                case "angle":
//                    System.out.println("This should be players :"+ ((MovingEntity)command.getObjects()[0]).getClass());
//                    ((Player) command.getObjects()[0]).setAngle(Integer.parseInt( command.getNewValue().toString() ));
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            if(e instanceof Bullet)break;
                            Player p = (Player) e;
                            p.setAngle(Integer.parseInt(command.getNewValue().toString()),true);
                        }
                    }
                    break;

                case "health":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            e.setHealth(Integer.parseInt(command.getNewValue().toString()));
                        }
                    }
                    break;

                case "score":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            HumanCharacter h = (HumanCharacter) e;
                            h.setScore(Integer.parseInt(command.getNewValue().toString()));
                        }
                    }
                    break;

                case "deathCount":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            HumanCharacter h = (HumanCharacter) e;
                            h.setDeathCount(Integer.parseInt(command.getNewValue().toString()));
                        }
                    }
                    break;

                case "killCount":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            HumanCharacter h = (HumanCharacter) e;
                            h.setKillCount(Integer.parseInt(command.getNewValue().toString()));
                        }
                    }
                    break;

                case "speed":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            MovingEntity me = (MovingEntity) e;
                            me.setSpeed(Integer.parseInt(command.getNewValue().toString()));
                        }
                    }
                    break;

                case "damage":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            MovingEntity me = (MovingEntity) e;
                            me.setDamage(Integer.parseInt(command.getNewValue().toString()),false);
                        }
                    }
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
