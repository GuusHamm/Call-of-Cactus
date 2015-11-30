package callofcactus.multiplayer;

import callofcactus.MultiPlayerGame;
import callofcactus.entities.Entity;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Wouter Vanmulken on 9-11-2015.
 */
public class ServerS {

    private MultiPlayerGame game;


	public ServerS(MultiPlayerGame g) {

        game = g;
		new Thread(new Runnable() {

            int count =0;

            @Override
			public void run() {

                ServerSocket serverSocket=null;
                Socket clientSocket=null;

                try {
                    if(serverSocket==null) {
                        System.out.println("Server is being initialized");
                        serverSocket = new ServerSocket(9090);
                    }else System.out.println("Server was already initailized : Error -------------------------------------------------");

                    while(true) {
                        System.out.println("Will now accept input");
                        clientSocket = serverSocket.accept();
                        System.out.println("---new input---");

                        BufferedReader buffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter out =
                                new PrintWriter(clientSocket.getOutputStream(), true);

                        String input = buffer.readLine();
                        System.out.println("server :" +input);

                        //handles the input and returns the wanted data.
                        out.println(handleInput(input));


                    }
                }catch (Exception e ){e.printStackTrace();}
			}
		}).start(); // And, start the thread running

        //update the server
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
//                game.spawnAI();
                List<Entity> k =game.getAllEntities();

                game.setAllEntities(k);
                System.out.println("woop woop");
                System.out.println(game.getAllEntities().size());
                //for(Ball b :k){b.update(1000);}

            }
        },1000,1000);
	}

    /**
     * Starts the server
     * @param args command line arguments thes will not be used.
     */
	public static void main(String args[]) {
        ServerS server = new ServerS(new MultiPlayerGame());
	}

    /**
     * Gets a command and takes the corresponding action
     * @param command command to set wich action to take.
     * @return
     */
    private String handleInput(String command){

        String returnValue="";

        switch (command) {
            case "getallBalls":
                returnValue = serialeDesiredObjects64(game.getAllEntities().toArray().clone());
                break;
        }
        return returnValue;
    }

    /**
     * Gets a array of object to serialize, and serializes them in a base64 format to a string and returns the objects.
     * @param objectsToSerialize These are the objects that will be serialized to base64
     * @return The string of serialized objectArray
     */
    private String serialeDesiredObjects64(Object[] objectsToSerialize){

        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream( baos );
            oos.writeObject( objectsToSerialize );
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
