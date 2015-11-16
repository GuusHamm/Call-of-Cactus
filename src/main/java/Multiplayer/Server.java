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

                        // Create the socket server using TCP protocol and listening on 9021
                        // Only one app can listen to a port at a time, keep in mind many ports are reserved
                        // especially in the lower numbers ( like 21, 80, etc )
//                        Server.serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, "127.0.0.1",65388, serverSocketHint);
                        if(Server.serverSocket==null) {
                            Server.serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, "127.0.0.1", 65388, serverSocketHint);
                            System.out.println("kkkkkkkkkkkkkkkkkfuckfuckfucfkfuckfuck");
                        }else
                        {
                            System.out.println("fuckfuckfucfkfuckfuck");
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
