package callofcactus.multiplayer;

import callofcactus.Administration;
import callofcactus.entities.Entity;
import callofcactus.entities.Player;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by o on 7-12-2015.
 */
public class ClientSideServer {

    private Administration administration;
    private Serializer serializer = new Serializer();

    /**
     * This is the Constructor and runs a constant procces on the server
     * This will eventually become multithreaded but for now it runs one action at a time.
     */
    public ClientSideServer() {

        administration = Administration.getInstance();

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
                        System.out.println("ClientServer :" + input);

                        //handles the input and returns the wanted data.
                        out.println(handleInput(Command.fromString(input)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start(); // And, start the thread running
    }

    /**
     * Gets a command and takes the corresponding action for wich method is requested
     * @param command command to set wich action to take.
     * @return
     */
    private String handleInput(Command command) {

        Command returnValue = null;
        try {
            switch (command.getMethod()) {

                case POST:
                    administration.setEntities(Arrays.asList((Entity[]) command.getObjects()) );
                    break;
                case CHANGE:
                    handleInputCHANGE(command);
                    break;
            }
            returnValue = new Command(Command.methods.SUCCES, null, Command.objectEnum.Succes);

        }catch (Exception e){

            e.printStackTrace();
            returnValue = new Command(Command.methods.FAIL, null, Command.objectEnum.Fail);

        }
        return returnValue.toString();
    }

    private Command handleInputCHANGE(Command command) {

        try {
            Entity entityFromCommand = (Entity) command.getObjects()[0];
            switch (command.getFieldToChange()) {
                case "locatie":
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == entityFromCommand.getID()) {
                            e.setLocation((Vector2) command.getNewValue());
                        }
                    }
                    break;
                case "angle":
                    ((Player[]) command.getObjects())[0].setAngle((Integer) command.getNewValue());
                    for (Entity e : administration.getMovingEntities()) {
                        if (e.getID() == entityFromCommand.getID()) {
                            Player p = (Player) e;
                            p.setAngle((Integer) command.getNewValue());
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
}
