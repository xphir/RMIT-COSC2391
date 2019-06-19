package view;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

/**
 * 
 * Skeleton/Partial example implementation of GameEngineCallback showing Java
 * logging behaviour
 * 
 * @author Caspar Ryan
 * @see view.interfaces.GameEngineCallback
 * 
 */
public class GameEngineCallbackImpl implements GameEngineCallback {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public GameEngineCallbackImpl() {
		// FINE shows dealing output, INFO only shows result
		logger.setLevel(Level.FINE);
	}

	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		// intermediate results logged at Level.FINE
		// TODO SHOULD BE FINE BUT IT DOESNT WORK
		logger.log(Level.FINE, String.format("Card Dealt to %s .. %s", player.getPlayerName(), card.toString()));
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {
		// final results logged at Level.INFO
		logger.log(Level.INFO, String.format("%s, final result=%d", player.getPlayerName(), result));
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		// intermediate results logged at Level.FINE
		// TODO SHOULD BE FINE BUT IT DOESNT WORK
		logger.log(Level.FINE,
				String.format("Card Dealt to %s .. %s ... YOU BUSTED!", player.getPlayerName(), card.toString()));
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		// intermediate results logged at Level.FINE
		// TODO SHOULD BE FINE BUT IT DOESNT WORK
		logger.log(Level.FINE, String.format("Card Dealt to House .. %s ... YOU BUSTED!", card.toString()));
	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		// intermediate results logged at Level.FINE
		// TODO SHOULD BE FINE BUT IT DOESNT WORK
		logger.log(Level.FINE, String.format("House, final result=%d", result));
		logger.log(Level.FINE, String.format("Final Player Results\n%s", logPlayerResults(engine)));
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		// intermediate results logged at Level.FINE
		// TODO SHOULD BE FINE BUT IT DOESNT WORK
		logger.log(Level.FINE, String.format("Card Dealt to House .. %s", card.toString()));
	}

	private String logPlayerResults(GameEngine engine) {
		StringBuilder playerResultString = new StringBuilder();
		for (Player players : engine.getAllPlayers()) {
			playerResultString.append(String.format("%s\n", players.toString()));
		}
		return playerResultString.toString();
	}

}
