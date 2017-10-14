import model.Card;
import model.Hand;
import org.testng.annotations.Test;

import static model.Card.Rank.*;
import static model.Card.Suit.*;
import static model.Hand.Type.*;

import static model.HandComparator.determineHandType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HandTypeTest {

    @Test
    public void nothig() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, TWO)};
        assertThat(determineHandType.apply(new Hand(cards)), is(NOTHING));
    }

    @Test
    public void onePair() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, JACK)};
        assertThat(determineHandType.apply(new Hand(cards)), is(PAIR));
    }

    @Test
    public void twoPairs() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(SPADE, KING),new Card(CLUB, JACK),new Card(HEART, JACK)};
        assertThat(determineHandType.apply(new Hand(cards)), is(TWO_PAIRS));
    }

    @Test
    public void threeOfAKind() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(HEART, NINE)};
        assertThat(determineHandType.apply(new Hand(cards)), is(THREE_OF_A_KIND));
    }

    @Test
    public void fullHouse() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(HEART, KING)};
        assertThat(determineHandType.apply(new Hand(cards)), is(FULL_HOUSE));
    }

    @Test
    public void fourOfAkind() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(SPADE, ACE)};
        assertThat(determineHandType.apply(new Hand(cards)), is(FOUR_OF_A_KIND));
    }

    @Test
    public void straight() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, TEN)};
        assertThat(determineHandType.apply(new Hand(cards)), is(STRAIGHT));
    }

    @Test
    public void straightWithOne() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, FIVE),new Card(CLUB, FOUR),new Card(CLUB, THREE),new Card(HEART, TWO)};
        assertThat(determineHandType.apply(new Hand(cards)), is(STRAIGHT));
    }

    @Test
    public void straightFlush() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(CLUB, TEN)};
        assertThat(determineHandType.apply(new Hand(cards)), is(STRAIGHT_FLUSH));
    }

    @Test
    public void straightFlushWithOne() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, FIVE),new Card(CLUB, FOUR),new Card(CLUB, THREE),new Card(CLUB, TWO)};
        assertThat(determineHandType.apply(new Hand(cards)), is(STRAIGHT_FLUSH));
    }

    @Test
    public void flush() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(CLUB, NINE)};
        assertThat(determineHandType.apply(new Hand(cards)), is(FLUSH));
    }

}
