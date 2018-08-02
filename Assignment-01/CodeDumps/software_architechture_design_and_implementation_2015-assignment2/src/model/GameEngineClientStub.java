/**
 * 
 */
package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;

/**
 * @author "Michael Vescovo - s3459317"
 *
 */
public class GameEngineClientStub implements GameEngine {
	private GameEngineCallback gameEngineCallback = null;
	private GameEngineCallbackServer gameEngineCallbackServer = null;
	private Player player = null;
	
	// socket variables for gameEngineServerStub
	private Socket socket;
	private String serverName = "localhost";
	private int port = 10000;
	
	// streams for gameEngineServerStub
	private ObjectOutputStream toServerObject = null;
	private ObjectInputStream fromServerObject = null;
	
	// command objects for communicating with the server
	private AddPlayerCommand addPlayerCommand = null;
	private AddPointsCommand addPointsCommand = null;
	private CalculateResultsCommand calculateResultsCommand = null;
	private PlaceBetCommand placeBetCommand = null;
	private ExitCommand exitCommand = null;
	private RemovePlayerCommand removePlayerCommand = null;
	private RollPlayerCommand rollPlayerCommand = null;
	private CheckHouseRollingCommand checkHouseRollingCommand = null;
	private Response response = null;
	
	public GameEngineClientStub() {	
		// create a GameEngineCallback server
		gameEngineCallbackServer = new GameEngineCallbackServer(this);
		
		try {
			// connect to the server
			socket = new Socket(serverName, port);
			
			// setup streams
			toServerObject = new ObjectOutputStream(socket.getOutputStream());
			fromServerObject = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Could not connect to server: " + e.getMessage());
			System.exit(-1);
		}
	}

	@Override
	public void rollPlayer(Player nullPlayer, int initialDelay, int finalDelay,
			int delayIncrement) {
		((SimplePlayer)player).setParticipatingInRound(true);

		// send the roll to the server
		try {
			rollPlayerCommand = new RollPlayerCommand(player.getPlayerId(), initialDelay, finalDelay, delayIncrement);
//			toServerObject.reset();
			toServerObject.writeObject(rollPlayerCommand);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void rollHouse(int initialDelay, int finalDelay, int delayIncrement) {
		// not used in client engine, see calculateResult instead.
	}

	@Override
	public void addPlayer(Player player) {
		// set local gameEngine player
		this.player = player;
		
		// start the GameEngineCallbackServer so the client can receive callbacks
		gameEngineCallbackServer.startServer();
		
		addPlayerCommand = new AddPlayerCommand(this.player, gameEngineCallbackServer.getPort());
		
		// add player to the server
		try {
			toServerObject.writeObject(addPlayerCommand);
			// receive back new version of player with unique id
			this.player = (Player)fromServerObject.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean removePlayer(Player player) {
		boolean isSuccess = false;
		// passed in player is null. use local gameEngine player.
		removePlayerCommand = new RemovePlayerCommand(this.player.getPlayerId());
		
		try {
			toServerObject.writeObject(removePlayerCommand);
			response = (Response)fromServerObject.readObject();
			
			if (response.getResponse()) {	
				// close server socket as the game server no longer
				// has a player to reference the connection
				gameEngineCallbackServer.stopServer();
								
				this.player = null;
				isSuccess = true;
			} else {
				isSuccess = false;
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return isSuccess;
	}

	@Override
	public void calculateResult() {
		// request server to calculate results (including roll house)
		try {
			calculateResultsCommand = new CalculateResultsCommand(player.getPlayerId());
			toServerObject.writeObject(calculateResultsCommand);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		this.gameEngineCallback = gameEngineCallback;
	}

	@Override
	public Collection<Player> getAllPlayers() {
		// nothing to do here
		return null;
	}

	@Override
	public boolean placeBet(Player player, int bet) {
		boolean betOk = false;
		
		try {
			placeBetCommand = new PlaceBetCommand(this.player.getPlayerId(), bet);
			toServerObject.writeObject(placeBetCommand);
			response = (Response)fromServerObject.readObject();
			
			if (response.getResponse()) {
				betOk = true;
			} else {
				betOk = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return betOk;
	}

	public Player getPlayer() {
		return player;
	}
	
	public void addPoints(int points) {
		// update local player
		player.setPoints(player.getPoints() + points);
		
		addPointsCommand = new AddPointsCommand(player.getPlayerId(), points);
		
		// send points to server version
		try {
			toServerObject.writeObject(addPointsCommand);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addGameEngineCallbackToGameEngineCallbackServer() {
		gameEngineCallbackServer.addGameEngineCallback(gameEngineCallback);
	}
	
	public void exitGame() {
		exitCommand = new ExitCommand();
		
		try {
			toServerObject.writeObject(exitCommand);
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public GameEngineCallback getGameEngineCallback() {
		return gameEngineCallback;
	}
	
	public boolean checkHouseRolling() {
		checkHouseRollingCommand = new CheckHouseRollingCommand();
		
		try {
			toServerObject.writeObject(checkHouseRollingCommand);
			response = (Response)fromServerObject.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (response.getResponse()) {
			return true;
		} else {
			return false;
		}
	}
}
