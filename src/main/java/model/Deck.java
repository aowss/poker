package model;

import java.util.Stack;

import static model.Game.DECK_SIZE;
import static util.Functions.uniqueRandomIntegers;

/**
 * A standard international 52-card pack
 */
public class Deck {

    public static final Card[] fullDeck = new Card[DECK_SIZE];

    //  <code>Deque</code> might be more appropriate
    private Stack<Card> cards = new Stack<>();

    static {
        int index = 0;
        for (Card.Rank rank : Card.Rank.values()) {
            for (Card.Suit suit : Card.Suit.values()) {
                fullDeck[index++] = new Card(suit, rank);
            }
        }
    }

    public Deck() {
        uniqueRandomIntegers.apply(0,DECK_SIZE).apply(DECK_SIZE).forEach(index -> cards.push(fullDeck[index]));
        System.out.println("deck at start : " + cards);
    }

    public Stack<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return '{' +
                    "cards: " + cards +
                '}';
    }

}
