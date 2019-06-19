package client;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.GameEngineImpl;
import model.SimplePlayer;
import model.PlayingCardImpl;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import validate.Validator;
import view.GameEngineCallbackImpl;

/**
 * Simple console client for SADI assignment 2, 2018 NOTE: This code will not
 * compile until you have implemented code for the supplied interfaces!
 * 
 * You must be able to compile your code WITHOUT modifying this class.
 * Additional testing should be done by copying and adding to this class while
 * ensuring this class still works.
 * 
 * The provided Validator.jar will check if your code adheres to the specified
 * interfaces!
 * 
 * @author Caspar Ryan
 * 
 */
public class MyTestClient {
	private static Logger logger = Logger.getLogger("assignment1");

	public static void main(String args[]) {
		final GameEngine gameEngine = new GameEngineImpl();
		List<Player> testPlayers = new ArrayList<Player>();
		int playerCount = 30;
		int reqRunCount = 10;
		int runCount = 0;
		
		Validator.validate(true);
		
		// create test players
		for (int i = 1; i <= playerCount; i++) {
			testPlayers.add(new SimplePlayer(Integer.toString(i), "Player #" + Integer.toString(i), 100));
		}

		// add logging callback
		gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());

		// main loop to add players, place a bet and receive hand
		for (Player player : testPlayers) {
			gameEngine.addPlayer(player);
		}

		//This will test x players (playerCount) for x rounds (reqRunCount)
		do {
			for (Player player : testPlayers) {
				gameEngine.placeBet(player, 100);
				gameEngine.dealPlayer(player, 1);
			}

			gameEngine.dealHouse(10);
			runCount++;
		} while (runCount < reqRunCount);
	}

	@SuppressWarnings("unused")
	private static void printCards(Deque<PlayingCard> deck) {
		for (PlayingCard card : deck)
			logger.log(Level.INFO, card.toString());
	}
}
