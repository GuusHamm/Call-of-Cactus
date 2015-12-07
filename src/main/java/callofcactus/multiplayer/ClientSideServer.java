package callofcactus.multiplayer;

import callofcactus.Administration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
     * Starts the server
     *
     * @param args command line arguments thes will not be used.
     */
    public static void main(String args[]) {
        ClientSideServer server = new ClientSideServer();
    }

    /**
     * Gets a command and takes the corresponding action for wich method is requested
     * @param command command to set wich action to take.
     * @return
     */
    private String handleInput(Command command) {

        Command returnValue = null;

        switch (command.getMethod()) {

            case POST:
                administration.addEntities((Entity[])command.getObjects());
                break;
            case CHANGE:
                //implement changing another player
                break;
        }

        return returnValue.toString();
    }
}
