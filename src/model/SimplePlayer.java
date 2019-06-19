package model;

import model.interfaces.Player;

public class SimplePlayer implements Player {

	// As per
	private String playerID;
	private String playerName;
	private int initialPoints;
	private int bet;
	private int result;

	public SimplePlayer(String playerID, String playerName, int initialPoints) {
		this.playerID = playerID;
		this.playerName = playerName;
		this.initialPoints = initialPoints;
	}

	@Override
	public String getPlayerName() {
		return playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public int getPoints() {
		return initialPoints;
	}

	@Override
	public void setPoints(int points) {
		this.initialPoints = points;
	}

	@Override
	public String getPlayerId() {
		return playerID;
	}

	@Override
	public boolean placeBet(int bet) {
		if (bet >= 0 && bet <= initialPoints) {
			this.bet = bet;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getBet() {
		return bet;
	}

	@Override
	public void resetBet() {
		this.bet = 0;
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public void setResult(int result) {
		this.result = result;
	}

	@Override
	public String toString() {
		String stringFormat = String.format("Player: Id=%s, name=%s, points=%d", getPlayerId(), getPlayerName(),
				getPoints());
		return stringFormat;
	}

}
