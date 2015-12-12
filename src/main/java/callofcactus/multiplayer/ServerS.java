package callofcactus.multiplayer;

import callofcactus.MultiPlayerGame;
import callofcactus.entities.*;
import com.badlogic.gdx.math.Vector2;
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by Wouter Vanmulken on 9-11-2015.
 */
public class ServerS {

    private MultiPlayerGame game;
    private Serializer serializer = new Serializer();
    private List<String> ipAdresses;

    /**
     * This is the Constructor and runs a constant procces on the server
     * This will eventually become multithreaded but for now it runs one action at a time.
     *
     * @param g
     */
    public ServerS(MultiPlayerGame g, List<String> ips) {
        System.out.println(DateTime.now().getHourOfDay()+DateTime.now().getMinuteOfDay()+DateTime.now().getSecondOfDay() + ": Server has been innitialized");
        ipAdresses = ips;

        game = g;
        new Thread(new Runnable() {

            int count = 0;

            @Override
            public void run() {

                ServerSocket serverSocket = null;
                Socket clientSocket = null;

                try {
                    if (serverSocket == null) {
                        System.out.println(DateTime.now().getHourOfDay()+DateTime.now().getMinuteOfDay()+DateTime.now().getSecondOfDay() + ": Server is being initialized");
                        serverSocket = new ServerSocket(8008);
                    } else {
                        System.out.println(DateTime.now().getHourOfDay()+DateTime.now().getMinuteOfDay()+DateTime.now().getSecondOfDay() + ": Server was already initailized : Error -------------------------------------------------");
                    }

                    while (true) {
                        System.out.println(DateTime.now().getHourOfDay()+DateTime.now().getMinuteOfDay()+DateTime.now().getSecondOfDay() + ": Will now accept input");
                        clientSocket = serverSocket.accept();
                        System.out.println(DateTime.now().getHourOfDay()+DateTime.now().getMinuteOfDay()+DateTime.now().getSecondOfDay() + ": \n---new input---");

                        BufferedReader buffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                        String input = buffer.readLine();
                        System.out.println(DateTime.now().getHourOfDay()+DateTime.now().getMinuteOfDay()+DateTime.now().getSecondOfDay() + ": server :" + input);

                        //handles the input and returns the wanted data.
                        Command c = Command.fromString(input);
                        new Thread(() -> { handleInput(c, out); }).start();

                        //CHANGE commands no longer send output back to the server

//                        System.out.println(DateTime.now().getHourOfDay()+DateTime.now().getMinuteOfDay()+DateTime.now().getSecondOfDay() + ": done sending info on the server");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        serverSocket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    new ServerS(game, ipAdresses);
                }

            }
        }).start(); // And, start the thread running

        //update the server
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
//                game.spawnAI();
//                List<Entity> k = game.getAllEntities();
//
//                game.setAllEntities(k);
//                game.compareHit();
                for(Entity e : game.getAllEntities()){
                    if(e instanceof Bullet){
                        ((Bullet)e).move();
                    }
                }
//                System.out.println(DateTime.now().getHourOfDay()+DateTime.now().getMinuteOfDay()+DateTime.now().getSecondOfDay() + ": woop woop");
//                System.out.println(game.getAllEnt.getHourOfDay()+DateTime.now().getMinuteOfDay()+DateTime.now().getSecondOfDay()ities().size());
//                System.out.println(game.getPlayer.getHourOfDay()+DateTime.now().getMinuteOfDay()+DateTime.now().getSecondOfDay()s().get(0).getAngle());
//                System.out.println(game.getPlayer.getHourOfDay()+DateTime.now().getMinuteOfDay()+DateTime.now().getSecondOfDay()s().get(0).getLocation().toString());
                //for(Ball b :k){b.update(1000);}

            }
        }, 1000, 100);
    }


    /**
     * Gets a command and takes the corresponding action for wich method is requested
     *
     * @param command command to set wich action to take.
     * @return
     */
    private void handleInput(Command command, PrintWriter out) {

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendMessagePush(command);

            }
        }).start();

        if(command.getMethod() == Command.methods.GET || command.getMethod() == Command.methods.POST){
            out.println(returnValue.toString());
        }
        out.flush();
    }

    /**
     * Takes the corresponding action within the GET command
     *
     * @param command
     * @return
     */
    private Command handleInputGET(Command command) {
        //TODO handle differen gets
        Command c = new Command(Command.methods.GET, ((Entity[]) game.getAllEntities().toArray()), command.getObjectToChange());
        return c;
    }

    /**
     * Takes the corresponding action within the POST command
     *
     * @param command
     * @return
     */
    private Command handleInputPOST(Command command) {

        int ID= -1;
        try {
            Entity entity = (Entity) command.getObjects()[0];
            ID = game.addEntityToGameWithIDReturn(entity);
            entity.setID(ID);
//            Entity[] entities = (Entity[]) command.getObjects();
//            for (Entity e : entities) {
//                ID = game.addEntityToGameWithIDReturn(e);
//            }
//            entities[0].setID(ID);

        } catch (Exception e) {
            e.printStackTrace();
            return new Command(ID,command.getFieldToChange(),command.getNewValue().toString(),Command.objectEnum.Fail);
        }
        return new Command(ID,command.getFieldToChange(),command.getNewValue().toString(),Command.objectEnum.Succes);
    }

    /**
     * Takes the corresponding action within the POST command
     *
     * @param command
     * @return
     */
    private Command handleInputCHANGE(Command command) {

        int ID = command.getID();
        try {
            switch (command.getFieldToChange()) {
                case "location":
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == ID) {
                            //First set lastLocation
                            e.setLastLocation(e.getLocation());
                            //Now for the actual location
                            String position = (String) command.getNewValue();
                            String[] pos = position.split(";");

                            e.setLocation(new Vector2(Float.parseFloat(pos[0]), Float.parseFloat(pos[1])),true);
//                            game.replaceMovingeEntity((MovingEntity) ID                       System.out.println(DateTime.now().getHourOfDay()+DateTime.now().getMinuteOfDay()+DateTime.now().getSecondOfDay() + ": new location :"+ e.getLocation());
                        }
                    }
                    break;
                case "angle":
//                    System.out.println(DateTime.now().getHourOfDay()+DateTime.now().getMinuteOfDay()+DateTime.now().getSecondOfDay() + ": This should be players :"+ ((MovingEntity)command.getObjects()[0]).getClass());
//                    ((Player) command.getObjects()[0]).setAngle(Integer.parseInt( command.getNewValue().toString() ));
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == ID) {
                            Player p = (Player) e;
                            p.setAngle(Integer.parseInt(command.getNewValue().toString()),false);
                        }
                    }
                    break;

                case "health":
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == ID) {
                            e.setHealth(Integer.parseInt(command.getNewValue().toString()));
                        }
                    }
                    break;

                case "score":
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == ID) {
                            HumanCharacter h = (HumanCharacter) e;
                            h.setScore(Integer.parseInt(command.getNewValue().toString()));
                        }
                    }
                    break;

                case "deathCount":
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == ID) {
                            HumanCharacter h = (HumanCharacter) e;
                            h.setDeathCount(Integer.parseInt( command.getNewValue().toString()));
                        }
                    }
                    break;

                case "killCount":
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == ID) {
                            HumanCharacter h = (HumanCharacter) e;
                            h.setKillCount(Integer.parseInt( command.getNewValue().toString()));
                        }
                    }
                    break;

                case "speed":
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == ID) {
                            MovingEntity me = (MovingEntity) e;
                            me.setSpeed(Integer.parseInt( command.getNewValue().toString()));
                        }
                    }
                    break;

                case "damage":
                    for (Entity e : game.getMovingEntities()) {
                        if (e.getID() == ID) {
                            MovingEntity me = (MovingEntity) e;
                            me.setDamage(Integer.parseInt( command.getNewValue().toString()),false);
                        }
                    }
                    break;

            }

        } catch (Exception e) {

            e.printStackTrace();
            return new Command(Command.methods.FAIL, ID, command.getFieldToChange(), command.getNewValue().toString(), command.getObjectToChange());

        }
        return new Command(Command.methods.SUCCES, ID, command.getFieldToChange(), command.getNewValue().toString(), command.getObjectToChange());

    }




    List<Socket> players = null;

    /**
     * Sends a Command to the server and gets a result
     * Return value can be null!!!
     *
     * @param message
     */
    public void sendMessagePush(Command message) {
//        if(players == null){
//            players = new ArrayList<>();
//            for(String ip : ipAdresses){
//                try {
//                    players.add(new Socket("127.0.0.1",8009));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        new Thread(new Runnable() {
            @Override
            public void run() {
//                for (String ip : ipAdresses) {
//                    System.out.println(DateTime.now().getHourOfDay()+DateTime.now().getMinuteOfDay()+DateTime.now().getSecondOfDay() + ": i pee haha:"+ip);
                    try {
                        Socket s = new Socket("127.0.0.1",8009);////////////////////////////////////////////////////////////////////////<----- this needs to be fixe (purely for testing pourpesus)
                        System.out.println(DateTime.now().getSecondOfDay() + ": Servers sending data to ClientSideServer");
                        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                        //Sending message
                        out.println(message.toString());
                        out.close();
                        s.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                }
            }
        }).start();

    }

    public void sendMessagePush2(Command message) {
        if(players == null){
            players = new ArrayList<>();
            for(String ip : ipAdresses){
                try {
                    players.add(new Socket("127.0.0.1",8009));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
         for (Socket socket : players) {
             new Thread(new Runnable() {
                 PrintWriter out;
                 BufferedReader in;

                 @Override
                 public void run() {
                     if (socket == null || socket.isClosed()) {

                     }

                     try {
                         out = new PrintWriter(socket.getOutputStream(), true);
                         in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                         //Sending message
                         out.println(message.toString());
                         in.close();
                         out.close();
                         socket.close();

                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
             }).start();
         }
    }
}
