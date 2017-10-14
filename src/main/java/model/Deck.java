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
    private Stack<Card> cards;

    static {
        int index = 0;
        for (Card.Rank rank : Card.Rank.values()) {
            for (Card.Suit suit : Card.Suit.values()) {
                fullDeck[index++] = new Card(suit, rank);
            }
        }
    }

    public static final Deck getShuffledDeck() {
        return new Deck();
    }

    public Deck() {
        cards = shuffle(fullDeck);
        System.out.println("deck at start : " + cards);
    }

    private Stack<Card> shuffle(Card[] deck) {
        Stack<Card> cards = new Stack<>();
        uniqueRandomIntegers.
                apply(0, deck.length).
                apply(deck.length).
                forEach(index -> cards.push(deck[index]));
        return cards;
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
