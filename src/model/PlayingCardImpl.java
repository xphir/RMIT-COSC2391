package model;

import model.interfaces.PlayingCard;

public class PlayingCardImpl implements PlayingCard
{
	private Suit suit;
	private Value value;

	public PlayingCardImpl(Suit suit, Value value)
	{
		super();
		this.suit = suit;
		this.value = value;
	}

	@Override
	public Suit getSuit()
	{
		return suit;
	}

	@Override
	public Value getValue()
	{
		return value;
	}

	@Override
	public int getScore()
	{
		switch (value)
		{
		case ACE:
			return 1;
		case TWO:
			return 2;
		case THREE:
			return 3;
		case FOUR:
			return 4;
		case FIVE:
			return 5;
		case SIX:
			return 6;
		case SEVEN:
			return 7;
		case EIGHT:
			return 8;
		case NINE:
			return 9;
		case TEN:
			return 10;
		case JACK:
			return 10;
		case QUEEN:
			return 10;
		case KING:
			return 10;
		default:
			// this shouldn't happen unless there is no value
			return 0;
		}
	}

	@Override
	public boolean equals(PlayingCard card)
	{
		if (card.getSuit() == this.suit && card.getValue() == this.value)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public String toString()
	{
		// TODO
		String stringFormat = String.format("Suit: %s, Value: %s, Score: %d", getSuit(), getValue(), getScore());
		return stringFormat;
	}

}
