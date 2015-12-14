package callofcactus.multiplayer;

import callofcactus.Administration;
import callofcactus.entities.Entity;
import callofcactus.entities.HumanCharacter;
import callofcactus.entities.Player;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Wouter Vanmulken on 23-11-2015.
 */
public class ClientS {

    Administration administration;
    public static ClientS instance;
    static String HOSTADRESS = "127.0.0.1";

    public static String getHostAddress() {
        return HOSTADRESS;
    }

    public static void setHostAddress(String HOSTADRESS) {
        ClientS.HOSTADRESS = HOSTADRESS;
    }

    private ClientS() {
        System.out.println("ClientS has been created");
    }

    public static ClientS getInstance() {
        if (instance == null) {
            instance = new ClientS();
        }
        return instance;
    }

    /**
     * Sends a Command to the server and gets a result
     * Return value can be null!!!
     *
     * @param message
     */
    public synchronized void sendMessageAndReturn(Command message) {
        if(administration == null){
            administration = Administration.getInstance();
        }
        new Thread(new Runnable() {
            Socket socket;
            PrintWriter out;
            BufferedReader in;

            @Override
            public void run() {

                if (socket == null || socket.isClosed()) {
                    try {
                        socket = new Socket(HOSTADRESS, 8008);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    //Sending message
                    out.println(message.toString());
                    if (message.getMethod() == Command.methods.GET || message.getMethod() == Command.methods.POST) {

                        String feedback = in.readLine();
                        System.out.println("The client received this as feedback :" + feedback);

                        Command c = Command.fromString(feedback);
                        if (message.getMethod() == Command.methods.POST) {
                            ((Entity) message.getObjects()[0]).setID(c.getID());
                        }
                        handleInput(c);
                    }
                    in.close();
                    out.close();
                    socket.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //TODO might give back a command like it did before but i have currently no idea why cause this would just keep the commands going 0.o - Wouter Vanmulken to Wouter Vanmulken
            private void handleInput(Command command) {
                if (command.getMethod() == Command.methods.POST) {
                    System.out.println();
                }

                switch (command.getMethod()) {
                    case GET:
                        handleInputGET(command);
                        break;
                    case POST:
                        handleInputPOST(command);
                        break;
                    case CHANGE:
                        handleInputCHANGE(command);
                        break;
                    case SUCCES:
                        handleInputPOST(command);
                        break;
                }

//        return returnValue.toString();
            }

            /**
             * Takes the corresponding action within the GET command
             *
             * @param command
             * @return
             */
            private Command handleInputGET(Command command) {
//        Command c = new Command(Command.methods.GET,game.getAllEntities().toArray());
//        return c;
                try {
                    Administration administration = Administration.getInstance();
                    administration.setEntitiesClientS(((Entity[]) message.getObjects()),((Entity[]) command.getObjects()));

                } catch (Exception e) {
                    return new Command(Command.methods.FAIL, null, null);
                }
                return new Command(Command.methods.SUCCES, null, null);
            }

            /**
             * Takes the corresponding action within the POST command
             * @param command
             * @return
             */
            private Command handleInputPOST(Command command) {

                try {
                    Administration.getInstance().setClientS();
                    Administration.getInstance().setEntitiesClientS(((Entity[]) message.getObjects()),((Entity[]) command.getObjects()));
//                    for(Entity e : administration.getAllEntities()){
//                        System.out.println("ID :" +e.getID());
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return new Command(Command.methods.FAIL, null, null);
                }
                return new Command(Command.methods.SUCCES, null, null);

            }

            /**
             * Takes the corresponding action within the POST command
             *
             * @param command
             * @return
             */
            private Command handleInputCHANGE(Command command) {
                try {
                    switch (command.getFieldToChange()) {
                        case "location":
                            ((Entity[]) command.getObjects())[0].setLocation((Vector2) command.getNewValue(), false);
                            break;
                        case "angle":
                            ((Player[]) command.getObjects())[0].setAngle((Integer) command.getNewValue(), false);
                            break;
                        case "matchID":
                            administration.setMatchID((Integer) command.getNewValue());
                            break;
                        case "health":
                            ((HumanCharacter[]) command.getObjects())[0].setHealth((Integer) command.getNewValue());
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                    return new Command(Command.methods.FAIL, null, null);
                }
                return new Command(Command.methods.SUCCES, null, null);
            }
        }).start();

    }


}
