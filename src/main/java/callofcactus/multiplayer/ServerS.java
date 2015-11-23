package callofcactus.multiplayer;

import callofcactus.IGame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by Wouter Vanmulken on 9-11-2015.
 */
public class ServerS {

    public IGame game;
    public List<InetAddress> players;

	public ServerS(IGame g) {

        game = g;
		new Thread(new Runnable() {

            int count =0;

            @Override
			public void run() {

                ServerSocket serverSocket=null;
                Socket clientSocket=null;

                try {
                    serverSocket = new ServerSocket(8008);

                    while(true) {
                        System.out.println("running");
                        clientSocket = serverSocket.accept();
                        System.out.println("---new input---");

                        BufferedReader buffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        String k = buffer.readLine();

                        handleInput(k, null);
                        System.out.println(k);

                    }
                }catch (Exception e ){e.printStackTrace();}
			}
		}).start(); // And, start the thread running
	}

	public static void main(String args[]) {
        ServerS s = new ServerS(null);
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
