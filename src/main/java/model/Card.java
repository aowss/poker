package model;

public class Card implements Comparable<Card> {

    /**
     *  In standard poker there is no ranking of suits for the purpose of comparing hands.
     *  When there is, this is the ranking lowest to highest
     */
    public enum Suit {
        CLUB, DIAMOND, HEART, SPADE;
    }

    /**
     * The rank of the cards, from high to low, is A, K, Q, J, 10, 9, 8, 7, 6, 5, 4, 3, 2.
     * In certain circumstances the ace can be used as a low card, below the 2.
     */
    public enum Rank {

        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14);

        private int value;

        Rank(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }


    @Override
    public int compareTo(Card o) {
        return Integer.valueOf(this.getRank().getValue()).compareTo(o.getRank().getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (suit != card.suit) return false;
        return rank == card.rank;
    }

    @Override
    public int hashCode() {
        int result = suit.hashCode();
        result = 31 * result + rank.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return '{' +
                    "suit: " + suit +
                ", rank: " + rank +
                '}';
    }

}
