package callofcactus.multiplayer;

import callofcactus.IGame;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
                             //   handleInput(k, null);
//                                System.out.println(k);
//                            }
//                        }).start();


                    }
                }catch (Exception e ){e.printStackTrace();}
			}
		}).start(); // And, start the thread running

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                game.compareHit();
            }
        },10);
	}

	public static void main(String args[]) {
        ServerS server = new ServerS(null);
	}

    public boolean handleInput(String input, List<Object> parameters)
    {
        if(!input.isEmpty()) {
            input = input.toLowerCase();
        }

        try {
            final String finalInput = input;
            Platform.runLater(() -> {

                switch (finalInput) {
                    case "playrandombulletsound":
                        game.playRandomBulletSound();
                        System.out.println("woop wop");
                        break;
                    case "playrandomhitsound":
                        game.playRandomHitSound();
                        System.out.println("woop wop");
                        break;
                }

            }
            );
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
