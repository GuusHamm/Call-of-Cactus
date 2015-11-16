package Multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Wouter Vanmulken on 9-11-2015.
 */
public class Server {

    /*
    *  We are using port 8008 for all network communication within our application
    */

    public static ServerSocket serverSocket;

    public Server() {

                new Thread(new Runnable() {

                    public ServerSocket serverSocket;

                    @Override
                    public void run() {
                        ServerSocketHints serverSocketHint = new ServerSocketHints();
                        SocketHints sh = new SocketHints();

                        // 0 means no timeout.  Probably not the greatest idea in production!
                        serverSocketHint.acceptTimeout = 0;

                        // Create the socket server using TCP protocol and listening on 8008
                        if(Server.serverSocket==null) {
                            Server.serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, "127.0.0.1", 8008, serverSocketHint);
                            System.out.println("Serverport created");
                        }

                        // Loop forever
                        while (true) {
                            // Create a socket
                            Socket socket = Server.serverSocket.accept(sh);

                            // Read data from the socket into a BufferedReader
                            BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            try {
                                // Read to the next newline (\n) and display that text on labelMessage
                                System.out.println(buffer.readLine());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start(); // And, start the thread running
            }
        }

//http://www.gamefromscratch.com/post/2014/03/11/LibGDX-Tutorial-10-Basic-networking.aspx
