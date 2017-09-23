package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static model.Game.HAND_SIZE;

public class Hand implements Comparable<Hand> {

    /**
     * The ranking of hands from high to low in standard poker is as follows :
     * Straight Flush, Four of a Kind, Full House, Flush, Straight, Three of a Kind, Two Pair, Pair, Nothing
     */
    public enum Type {
        STRAIGHT_FLUSH, FOUR_OF_A_KIND, FULL_HOUSE, FLUSH, STRAIGHT, THREE_OF_A_KIND, TWO_PAIRS, PAIR, NOTHING
    }

    private List<Card> cards;

    public Hand(Card[] cards) {
        if (cards == null || cards.length != HAND_SIZE) throw new RuntimeException("A hand must be " + HAND_SIZE + " cards");
        this.cards = Arrays.asList(cards);
        Collections.sort(this.cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public int compareTo(Hand other) {
        return 0;
    }

    @Override
    public String toString() {
        return cards.toString();
    }

}
