import model.Card;
import model.Hand;
import model.HandComparator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static model.Card.Rank.*;
import static model.Card.Suit.*;
import static model.Hand.Type.*;
import static model.HandComparator.determineHandType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class HandComparatorTest {

    HandComparator comparator;

    @BeforeClass
    public void setUp() {
        comparator = new HandComparator();
    }

    @Test
    public void nothig() {
        Card[] nothing = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, TWO)};
        Card[] onePair = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, JACK)};
        assertThat(comparator.compare(new Hand(nothing), new Hand(onePair)), lessThanOrEqualTo(-1));
    }

    @Test
    public void onePair() {
        Card[] onePair = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, JACK)};
        Card[] twoPairs = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(SPADE, KING),new Card(CLUB, JACK),new Card(HEART, JACK)};
        assertThat(comparator.compare(new Hand(onePair), new Hand(twoPairs)), lessThanOrEqualTo(-1));
    }

    @Test
    public void twoPairs() {
        Card[] twoPairs = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(SPADE, KING),new Card(CLUB, JACK),new Card(HEART, JACK)};
        Card[] threeOfAKind = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(HEART, NINE)};
        assertThat(comparator.compare(new Hand(twoPairs), new Hand(threeOfAKind)), lessThanOrEqualTo(-1));
    }

    @Test
    public void threeOfAKind() {
        Card[] threeOfAKind = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(HEART, NINE)};
        Card[] fullHouse = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(HEART, KING)};
        assertThat(comparator.compare(new Hand(threeOfAKind), new Hand(fullHouse)), lessThanOrEqualTo(-1));
    }

    @Test
    public void fullHouse() {
        Card[] fullHouse = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(HEART, KING)};
        Card[] fourOfAkind = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(SPADE, ACE)};
        assertThat(comparator.compare(new Hand(fullHouse), new Hand(fourOfAkind)), lessThanOrEqualTo(-1));
    }

    @Test
    public void fourOfAkind() {
        Card[] fourOfAkind = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(SPADE, ACE)};
        Card[] straight = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, TEN)};
        assertThat(comparator.compare(new Hand(fourOfAkind), new Hand(straight)), greaterThanOrEqualTo(1));
    }

    @Test
    public void straight() {
        Card[] straight = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, TEN)};
        Card[] straightWithOne = new Card[] {new Card(CLUB, ACE),new Card(CLUB, FIVE),new Card(CLUB, FOUR),new Card(CLUB, THREE),new Card(HEART, TWO)};
        assertThat(comparator.compare(new Hand(straight), new Hand(straightWithOne)), greaterThanOrEqualTo(1));
    }

    @Test
    public void straightWithOne() {
        Card[] straightWithOne = new Card[] {new Card(CLUB, ACE),new Card(CLUB, FIVE),new Card(CLUB, FOUR),new Card(CLUB, THREE),new Card(HEART, TWO)};
        Card[] straightFlush = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(CLUB, TEN)};
        assertThat(comparator.compare(new Hand(straightWithOne), new Hand(straightFlush)), lessThanOrEqualTo(-1));
    }

    @Test
    public void straightFlush() {
        Card[] straightFlush = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(CLUB, TEN)};
        Card[] straightFlushWithOne = new Card[] {new Card(CLUB, ACE),new Card(CLUB, FIVE),new Card(CLUB, FOUR),new Card(CLUB, THREE),new Card(CLUB, TWO)};
        assertThat(comparator.compare(new Hand(straightFlush), new Hand(straightFlushWithOne)), greaterThanOrEqualTo(1));
    }

    @Test
    public void straightFlushWithOne() {
        Card[] straightFlushWithOne = new Card[] {new Card(CLUB, ACE),new Card(CLUB, FIVE),new Card(CLUB, FOUR),new Card(CLUB, THREE),new Card(CLUB, TWO)};
        Card[] flush = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(CLUB, NINE)};
        assertThat(comparator.compare(new Hand(straightFlushWithOne), new Hand(flush)), greaterThanOrEqualTo(1));
    }

    @Test
    public void flush() {
        Card[] flush = new Card[] {new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(CLUB, NINE),new Card(CLUB, EIGHT)};
        Card[] nothing = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, TWO)};
        assertThat(comparator.compare(new Hand(flush), new Hand(nothing)), greaterThanOrEqualTo(1));
    }

}
