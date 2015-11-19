package Multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.PrintStream;

/**
 * Created by woute on 19-11-2015.
 */
public class ClientSocket {

    Socket socket;
	SocketHints socketHints;

	public ClientSocket() {

		socketHints = new SocketHints();
		// Socket will time our in 4 seconds
		socketHints.connectTimeout = 4000;
		socketHints.keepAlive = true;

		//create the socket and connect to the server entered in the text box ( x.x.x.x format ) on port 8008
		socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "127.0.0.1", 8008, socketHints);

	}

	public static void main(String args[]) {

        ClientSocket c = new ClientSocket();
		for (int i = 0; i < 10; i++) {
			c.sendMessage("kkk");
		}
	}

	public void sendMessage(String text) {

		String textToSend = new String();
		if (!socket.isConnected()) {
			socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "127.0.0.1", 8008, socketHints);
		}
		textToSend = text + "\n";

		SocketHints socketHints = new SocketHints();

		try {
			// write our entered message to the stream
			//socket.getOutputStream().write(textToSend.getBytes());

			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println(textToSend);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
