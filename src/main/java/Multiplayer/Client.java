package Multiplayer;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.IOException;

/**
 * Created by Wouter Vanmulken on 9-11-2015.
 */
public class Client {

    /*
    *  We are using port 8008 for all network communication within our application
    */

    Socket socket ;
    SocketHints socketHints;

    public Client() {

        socketHints = new SocketHints();
        // Socket will time our in 4 seconds
        socketHints.connectTimeout = 4000;
        socketHints.keepAlive=true;

        //create the socket and connect to the server entered in the text box ( x.x.x.x format ) on port 9021
//        Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "127.0.0.1", 9021, socketHints);

    }
    public void  sendMessage(String text){

        // When the button is clicked, get the message text or create a default string value
        String textToSend = new String();
            textToSend = text + ("\n"); // Brute for a newline so readline gets a line

        SocketHints socketHints = new SocketHints();
        // Socket will time our in 4 seconds
        socketHints.connectTimeout = 1000;
        //create the socket and connect to the server entered in the text box ( x.x.x.x format ) on port 8008
        Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "127.0.0.1", 8008, socketHints);
        try {
            // write our entered message to the stream
            socket.getOutputStream().write(textToSend.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void test()
    {
        System.out.println("wow much network");

        for(int i =0; i<10;i++)
        {
            sendMessage("kkkk");
            System.out.println("kkkk sent");
        }
    }
}
