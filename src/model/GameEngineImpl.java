package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;
import view.interfaces.GameEngineCallback;

/**
 * 
 * 
 */

public class GameEngineImpl implements GameEngine {
	private List<Player> players = new ArrayList<Player>();
	private Deque<PlayingCard> publicDeck = new ArrayDeque<PlayingCard>();
	private ArrayList<GameEngineCallback> gameEngineCallbacks = new ArrayList<GameEngineCallback>();

	@Override
	public void dealPlayer(Player player, int delay) {
		// TODO Auto-generated method stub
		PlayingCard card;
		int handScore = 0;
		Boolean ongoing = true;

		// checks if a new deck is needed
		newDeck();

		do {
			// removing the card and giving it to the player
			card = publicDeck.removeFirst();

			// adding the dealt card to the hand total
			handScore = handScore + card.getScore();

			// This adds the delay between hands
			wait(delay);

			// logging the dealt card
			ongoing = bustHandle(player, handScore, card);

			// keep running through the hand till the player busts
		} while (ongoing);
	}

	@Override
	public void dealHouse(int delay) {
		// TODO Auto-generated method stub
		PlayingCard card;
		int handScore = 0;
		Boolean ongoing = true;

		// checks if a new deck is needed
		newDeck();

		do {
			// removing the card and giving it to the player
			card = publicDeck.removeFirst();

			// adding the dealt card to the hand total
			handScore = handScore + card.getScore();

			// This adds the delay between hands
			wait(delay);

			// logging the dealt card + some other end of round work
			ongoing = bustHandle(handScore, card);

			// keep running through the hand till the player busts
		} while (ongoing);

		// The Round has ended, clear the old deck and get a new one
		publicDeck = getShuffledDeck();
	}

	@Override
	public void addPlayer(Player player) {
		players.add(player);
	}

	@Override
	public Player getPlayer(String id) {
		for (Player aplayer : players) {
			if (aplayer.getPlayerId().equals(id))
				return aplayer;
		}
		return null;
	}

	@Override
	public boolean removePlayer(Player player) {
		for (Player aplayer : players) {
			if (player == aplayer) {
				players.remove(aplayer);
				return true;
			}
		}
		return false;
	}

	// DONE
	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		gameEngineCallbacks.add(gameEngineCallback);
	}

	// DONE
	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		for (GameEngineCallback aGameEngineCallback : gameEngineCallbacks) {
			if (gameEngineCallback == aGameEngineCallback) {
				return true;
			}
		}
		return false;
	}

	// DONE
	@Override
	public Collection<Player> getAllPlayers() {
		List<Player> immutablePlayerLists = Collections.unmodifiableList(players);
		return immutablePlayerLists;
	}

	// DONE
	@Override
	public boolean placeBet(Player player, int bet) {
		boolean result;
		result = player.placeBet(bet);
		return result;
	}

	// DONE - Might be better alway keeping it as a deque and shuffling that
	// IMPORTANT NOTE THIS MAY BE CALLED A DEBUG METHOD, BUT IT IS USED ELSEWHERE
	@Override
	public Deque<PlayingCard> getShuffledDeck() {
		List<PlayingCard> newDeck = new ArrayList<PlayingCard>();
		Deque<PlayingCard> shuffledDeck = new LinkedList<PlayingCard>();

		// Creates a brand new unshuffled deck
		for (Suit suit : PlayingCard.Suit.values()) {
			for (Value value : PlayingCard.Value.values()) {
				newDeck.add(new PlayingCardImpl(suit, value));
			}
		}
		// Shuffles the new deck - can't shuffle a Deque
		Collections.shuffle(newDeck);

		// converts the deck into Deque form
		for (PlayingCard card : newDeck) {
			shuffledDeck.addLast(card);
		}

		return shuffledDeck;
	}

	// used to wait the delay inputed
	private void wait(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// THIS HANDLES HOSTS
	// called by dealHost, does the logging and score management of dealHost, and
	// on busting will return false
	private boolean bustHandle(int handScore, PlayingCard card) {
		int finalResult;
		if (handScore > BUST_LEVEL) {
			for (GameEngineCallback aGameEngineCallback : gameEngineCallbacks) {
				aGameEngineCallback.houseBustCard(card, this);
			}
			finalResult = handScore - card.getScore();
			betHandle(finalResult);
			for (GameEngineCallback aGameEngineCallback : gameEngineCallbacks) {
				aGameEngineCallback.houseResult(finalResult, this);
			}
			return false;
		} else {
			for (GameEngineCallback aGameEngineCallback : gameEngineCallbacks) {
				aGameEngineCallback.nextHouseCard(card, this);
			}
			return true;
		}

	}

	// THIS HANDLES PLAYERS
	// Method overloading the host version of bustHandle, see above
	private boolean bustHandle(Player player, int handScore, PlayingCard card) {
		int finalResult;
		if (handScore > BUST_LEVEL) {
			finalResult = handScore - card.getScore();
			player.setResult(finalResult);

			for (GameEngineCallback aGameEngineCallback : gameEngineCallbacks) {
				aGameEngineCallback.bustCard(player, card, this);
			}
			for (GameEngineCallback aGameEngineCallback : gameEngineCallbacks) {
				aGameEngineCallback.result(player, finalResult, this);
			}

			return false;
		} else {
			for (GameEngineCallback aGameEngineCallback : gameEngineCallbacks) {
				aGameEngineCallback.nextCard(player, card, this);
			}
			return true;
		}
	}

	// Handles the end of round bet returns and the reset of bets.
	private void betHandle(int houseScore) {
		for (Player aplayer : players) {
			if (aplayer.getResult() > houseScore) {
				// player wins
				aplayer.setPoints(aplayer.getPoints() + aplayer.getBet());
				aplayer.resetBet();
			} else if (aplayer.getResult() == houseScore) {
				aplayer.resetBet();
			} else {
				aplayer.setPoints(aplayer.getPoints() - aplayer.getBet());
				;
				aplayer.resetBet();
			}
		}

	}

	private void newDeck() {
		/*
		 * 1 + 1 + 1 + 1 = 4 2 + 2 + 2 + 2 = 8 3 + 3 + 3 + 3 = 12 4 + 8 + 12 = 24 Thus
		 * 12 cards is the highest ammount of cards that can be given per hand thus if
		 * there is less than 12 cards you need a new deck
		 */
		int minDeckSize = 12;

		if (publicDeck.size() <= minDeckSize) {
			// Get a new deck
			publicDeck = getShuffledDeck();
		}
	}

}