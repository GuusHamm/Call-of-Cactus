package callofcactus.multiplayer;

import callofcactus.Administration;
import callofcactus.entities.Entity;
import callofcactus.entities.Player;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by Wouter Vanmulken on 23-11-2015.
 */
public class ClientS {

    Socket socket;
    PrintWriter out;
    BufferedReader in;
    //MultiPlayerGame game;
    Administration administration ;
    public static ClientS instance;

    private ClientS() {

////        administration = Administration.getInstance();
//      //  game = g;
//

    }
    public static ClientS getInstance() {
        if (instance == null) {
            instance = new ClientS();
        }
        return instance;
    }

//
//    public void sendMessage(Command message) {
//        try {
//
//            out = new PrintWriter(socket.getOutputStream(), true);
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            out.println(message.toString());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    /**
     * Sends a Command to the server and gets a result
     * Return value can be null!!!
     * @param message
     */
    public void sendMessageAndReturn(Command message) {

        if(socket == null || socket.isClosed()){
            try {
                socket = new Socket("127.0.0.1", 8008);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String feedback = "";
        try {

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Sending message
            out.println(message.toString());
            System.out.println("message sent");

            //while (feedback.equals("") || feedback==null || feedback.isEmpty()) {
                System.out.println("test");
                feedback = in.readLine();
                System.out.println("Client feedback the server sent:"+feedback);
            //}
            in.close();

            System.out.println("The client received this as feedback :" + feedback);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("client :" + feedback);

        Command c = Command.fromString(feedback);

        System.out.println("we have liftoff!!!");

        handleInput(c);
    }
    //TODO might give back a command like it did before but i have currently no idea why cause this would just keep the commands going 0.o - Wouter Vanmulken to Wouter Vanmulken
    private void handleInput(Command command) {

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
            case SUCCES:

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
            administration.setEntities(Arrays.asList((Entity[]) command.getObjects()));
        } catch (Exception e) {
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
    private Command handleInputPOST(Command command) {

        try {
            Entity[] entities = (Entity[]) command.getObjects();
            administration.setEntities(Arrays.asList(entities));

        }catch (Exception e){
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
    private Command handleInputCHANGE(callofcactus.multiplayer.Command command) {
        try {
            switch (command.getFieldToChange()) {
                case "location":
                    ((Entity[]) command.getObjects())[0].setLocation((Vector2) command.getNewValue());
                    break;
                case "angle":
                    ((Player[]) command.getObjects())[0].setAngle((Integer) command.getNewValue());
                    break;
            }
        } catch (Exception e) {

            e.printStackTrace();
            return new Command(Command.methods.FAIL, null, null);
        }
        return new Command(Command.methods.SUCCES, null, null);
    }


}
