package callofcactus.multiplayer;

import callofcactus.entities.Entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wouter Vanmulken on 23-11-2015.
 */
public class ClientS {

    Socket socket;
    PrintWriter out;
    BufferedReader in;
    //MultiPlayerGame game;

    public ClientS() {

      //  game = g;

        try {
            socket = new Socket("127.0.0.1", 9090);

        } catch (IOException e) {
            e.printStackTrace();
        }

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
    public List<Entity> sendMessageAndReturn(Command message) {

        String feedback = null;
        try {

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Sending message
            out.println(message.toString());
            System.out.println("message sent");

            //Getting the feedback
            feedback = in.readLine();

            while (feedback == null) {
                System.out.println("test");
                feedback = in.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("client :" + feedback);

        List<Entity> o = new ArrayList<Entity>();
        Command c = Command.fromString(feedback);
        for(Object e : c.getObjects()){
            o.add((Entity) e);
        }
        System.out.println("we have liftoff!!!");
        return o;

    }

}
