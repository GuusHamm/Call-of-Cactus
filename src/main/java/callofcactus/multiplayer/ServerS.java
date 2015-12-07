package callofcactus.multiplayer;

import callofcactus.MultiPlayerGame;
import callofcactus.entities.Entity;
import callofcactus.entities.Player;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
    public ServerS(MultiPlayerGame g) {

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
                        serverSocket = new ServerSocket(9090);
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
                        out.println(handleInput(Command.fromString(input)));


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
     * Starts the server
     *
     * @param args command line arguments thes will not be used.
     */
    public static void main(String args[]) {
        ServerS server = new ServerS(new MultiPlayerGame());
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

        Command c = new Command(Command.methods.GET,game.getAllEntities().toArray().clone() );
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
            return new Command(Command.methods.FAIL,null);
        }
        return new Command(Command.methods.SUCCES,null);


    }
    /**
     * Takes the corresponding action within the POST command
     * @param command
     * @return
     */
    private Command handleInputCHANGE(callofcactus.multiplayer.Command command) {

        try {

            switch (command.getFieldToChange()) {
                case "locatie":
                    ((Entity[]) command.getObjects())[0].setLocation((Vector2) command.getNewValue());
                    break;
                case "angle":
                    ((Player[]) command.getObjects())[0].setAngle((Integer) command.getNewValue());
                    break;
            }

        }catch (Exception e){

            e.printStackTrace();
            return new Command(Command.methods.FAIL,null);

        }
        return new Command(Command.methods.SUCCES, null);
    }

}
