package callofcactus.multiplayer;

import callofcactus.Administration;
import callofcactus.GameInitializer;
import callofcactus.entities.*;
import callofcactus.menu.MultiPlayerEndScreen;
import callofcactus.role.Boss;
import callofcactus.menu.EndScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

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

                    while (!ServerVariables.getShouldServerStop()) {
                        clientSocket = serverSocket.accept();
                        try {
                            if(administration.isConnectionLost()){
                                administration.setConnectionLost(false);
                            }
                            BufferedReader buffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            String input = buffer.readLine();
                            buffer.close();

                            System.out.println("ClientSideServer :" + input);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Command c = Command.fromString(input);
                                    handleInput(c);
                                }
                            }).start();
//                            commandQueue.addCommand(c);
                        }
                        catch(SocketException se){
                            System.out.println("Connection lost");
                            administration.setConnectionLost(true);
                        }
                        catch (Exception e){e.printStackTrace();}
                        finally {
//                            clientSocket.close();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (serverSocket != null) {
                        try {
                            serverSocket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    new ClientSideServer();
                }
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start(); // And, start the thread running

        new Thread(() -> {
            while (!ServerVariables.getShouldServerStop()) {
                Command c;
                while (!ServerVariables.getShouldServerStop() && (c = commandQueue.getNext()) != null) {
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
            case DESTROY:
                Entity a = administration.searchEntity(command.getID());
                administration.removeEntity(a);
                break;
            case STOP:
                if(Integer.parseInt(command.getNewValue().toString())==-1) {
                    ServerVariables.setShouldServerStop(true);
                    Gdx.app.postRunnable(() -> {
                        GameInitializer.getInstance().setScreen(new MultiPlayerEndScreen(GameInitializer.getInstance()));
                    });
                }
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
                if(e instanceof Bullet){
                    administration.getGameSounds().playBulletFireSound();
                }
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
        //LOOK AT THIS!!!
        if(administration.getLocalPlayer().getID() == ID && (command.getFieldToChange().toLowerCase().contains("angle") || command.getFieldToChange().toLowerCase().contains("location"))
                &&
                command.getObjectToChange()!= Command.objectEnum.SetLastLocation){
            return;
        }
        try {
            counter++;
            switch (command.getFieldToChange()) {
                case "location":
                    List<MovingEntity> movingEntityList = administration.getMovingEntities();
                    synchronized (movingEntityList) {
                        for (Entity e : movingEntityList) {
                            if (e.getID() == ID) {
                                if(command.getObjectToChange()!= Command.objectEnum.SetLastLocation) {

                                    //First set lastLocation
                                    e.setLastLocation(e.getLocation());
                                    //Now for the actual location
                                    String position = (String) command.getNewValue();
                                    String[] pos = position.split(";");
                                    e.setLocation(new Vector2(Float.parseFloat(pos[0]), Float.parseFloat(pos[1])), false);

                                }else if(command.getObjectToChange()== Command.objectEnum.SetLastLocation){


                                        e.setLocation(e.getLastLocation(), false);
                                    
                                        //First set lastLocation
                                        //e.setLastLocation(e.getLocation());
//                                        //Now for the actual location
//                                        String position = (String) command.getNewValue();
//                                        String[] pos = position.split(";");
//                                        e.setLocation(new Vector2(Float.parseFloat(pos[0]), Float.parseFloat(pos[1])), false);
                                }


                                //System.out.println();


//                            administration.replaceMovingeEntity((MovingEntity) ID                       System.out.println("new location :"+ e.getLocation());
                            }
                        }
                    }
                    break;
                case "angle":
//                    System.out.println("This should be players :"+ ((MovingEntity)command.getObjects()[0]).getClass());
//                    ((Player) command.getObjects()[0]).setAngle(Integer.parseInt( command.getNewValue().toString() ));

                    List<MovingEntity> movingEntityList2 = administration.getMovingEntities();
                    synchronized (movingEntityList2) {
                        for (Entity e : movingEntityList2) {
                            if (e.getID() == ID) {
                                if (e instanceof Bullet) break;
                                Player p = (Player) e;
                                p.setAngle(Integer.parseInt(command.getNewValue().toString()), false);
                            }
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

                case "deathCount":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            HumanCharacter h = (HumanCharacter) e;
                            h.setDeathCount(Integer.parseInt(command.getNewValue().toString()));
                            System.out.println("DeathCount message received " + h.getAccount().getUsername() + "; With new DeathCount: " + h.getAccount().getDeathCount());
                        }
                    }
                    break;

                case "speed":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            MovingEntity me = (MovingEntity) e;
                            me.setSpeed(Integer.parseInt(command.getNewValue().toString()), false);
                        }
                    }
                    break;

                case "health":
                    for (int i=0; i< administration.getMovingEntities().size();i++) {
                        if (administration.getMovingEntities().get(i).getID() == ID) {
                            MovingEntity e =administration.getMovingEntities().get(i);
                            if(e instanceof Player){

                                //e.setHealth(e.takeDamage(Integer.parseInt(command.getNewValue().toString()),false),false); u wot m8
                                e.setHealth(Integer.parseInt(command.getNewValue().toString()),false);
                                administration.getInstance().getGameSounds().playRandomHitSound();
                                //Thread.sleep(100);

                                administration.setMovingEntity(i, e);
                                System.out.println("___ClientSideServer handleInputChange.case health. Id  " + e.getID() + "has taken damage. New health: " + e.getHealth());
                            }

                        }
                    }
                    break;

                case "role":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == ID) {
                            ((HumanCharacter)e).changeRole(new Boss());
                            e.setSpriteHeight(64);
                            e.setSpriteWidth(32);
                        }
                    }
                    break;
                case "matchID":
                    administration.setMatchID(Integer.parseInt(command.getNewValue().toString()));
                    break;
                case "bossModeActive":
                    administration.setBossModeActive(Boolean.valueOf(command.getNewValue().toString()));
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    int counter=0;
}