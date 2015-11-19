package Multiplayer;

import callofcactus.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Wouter Vanmulken on 9-11-2015.
 */
public class ServerS {

    public Game game;

	public ServerS(Game g) {
        game = g;
		new Thread(new Runnable() {
            int coumnt =0;
			@Override
			public void run() {

				ServerSocketHints serverSocketHint = new ServerSocketHints();
				serverSocketHint.acceptTimeout = 10000;

				// Create a socket
				ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, 8008, serverSocketHint);
                Socket socket;
                socket = serverSocket.accept(null);

				// Loop forever
				while (true) {

					// Read data from the socket into a BufferedReader
					BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    try {
                        String k = buffer.readLine();

                        if(k == null || k.isEmpty())
                            continue;
                        else {
                            new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    handleInput(k, null);
                                    System.out.println(k);
                                }
                            });
                        }

                    } catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start(); // And, start the thread running
	}

	public static void main(String args[]) {
//        ServerS s = new ServerS();
	}
    public boolean handleInput(String input, List<Object> parameters)
    {
        input = input.toLowerCase();

        try{

            switch (input) {
                case "playrandombulletsound":
                    game.playRandomBulletSound();
                    break;
                case "playrandomhitsound":
                    game.playRandomHitSound();
                    break;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
