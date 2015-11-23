package multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by woute on 23-11-2015.
 */
public class ClientS {

    Socket socket;
    PrintWriter out;
    BufferedReader in;

    public ClientS(){

        try {
            socket = new Socket("127.0.0.1", 8008);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void sendMessage(String message){

        try {
            out.println(message);
        }catch (Exception e){e.printStackTrace();}

    }

    public static void main(String[] args)
    {
        ClientS cs2 = new ClientS();
        cs2.sendMessage("kkkk");
    }

}
