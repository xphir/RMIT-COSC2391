/**
 * 
 */
package model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

import model.interfaces.Player;

/**
 * @author "Michael Vescovo - s3459317"
 *
 */
public class HandleAServer implements Runnable {
	private Socket socket = null;
	private String serverName = "localhost";
	private int port;
	private Player player = null;
	private Map<Player, ObjectOutputStream> hashMapObject;
	private Map<Player, Socket> hashMapSocket;

	// streams
	ObjectOutputStream toServerObject = null;
	
	public HandleAServer(Player player, int port,
			Map<Player, ObjectOutputStream> hashMapObject,
			Map<Player, Socket> hashMapSocket) {
		this.player = player;
		this.port = port;
		this.hashMapObject = hashMapObject;
		this.hashMapSocket = hashMapSocket;
	}

	@Override
	public void run() {
		try {
			// connect to the server
			socket = new Socket(serverName, port);
				
			// setup streams
			toServerObject = new ObjectOutputStream(socket.getOutputStream());
			
			hashMapObject.put(player, toServerObject);
			hashMapSocket.put(player, socket);
		} catch (IOException e) {
			System.out.println("Could not connect to server: " + e.getMessage());
			try {
				System.out.println("Sleeping for 35 ms");
				Thread.sleep(35);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}