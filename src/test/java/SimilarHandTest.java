import jdk.nashorn.internal.ir.annotations.Ignore;
import model.Card;
import org.testng.annotations.Test;

import java.util.*;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static model.Card.Rank.*;
import static model.Card.Suit.*;

import static model.HandComparator.ranksComparator;
import static model.HandComparator.sortMapUsingValues;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimilarHandTest {

    private int compare(Card[] hand1, Card[] hand2) {
        Map<Card.Rank, Long> ranks1 = Arrays.stream(hand1).collect(groupingBy(Card::getRank, counting()));
        List<Card.Rank> sortedRanks1 = sortMapUsingValues.apply(ranks1);
        Map<Card.Rank, Long> ranks2 = Arrays.stream(hand2).collect(groupingBy(Card::getRank, counting()));
        List<Card.Rank> sortedRanks2 = sortMapUsingValues.apply(ranks2);
        return ranksComparator.compare(sortedRanks1, sortedRanks2);
    }

    @Test
    public void nothigComparison() {
        Card[] cards1 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, TWO)};
        Card[] cards2 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, TEN),new Card(CLUB, JACK),new Card(HEART, TWO)};
        assertThat(compare(cards1, cards2), is(1));
    }

    @Test
    public void onePairComparison() {
        Card[] cards1 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, JACK)};
        Card[] cards2 = new Card[] {new Card(CLUB, TEN),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, JACK)};
        assertThat(compare(cards1, cards2), is(1));
    }

    @Test
    public void twoPairsComparison() {
        Card[] cards1 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(SPADE, KING),new Card(CLUB, JACK),new Card(HEART, JACK)};
        Card[] cards2 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(SPADE, KING),new Card(CLUB, ACE),new Card(HEART, JACK)};
        assertThat(compare(cards1, cards2), is(-1));
    }

    @Test
    public void threeOfAKindComparison() {
        Card[] cards1 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(HEART, NINE)};
        Card[] cards2 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(HEART, NINE)};
        assertThat(compare(cards1, cards2), is(0));
    }

    @Test
    public void fullHouseComparison() {
        Card[] cards1 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(HEART, KING)};
        Card[] cards2 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, QUEEN),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(HEART, QUEEN)};
        assertThat(compare(cards1, cards2), is(1));
    }

    @Test
    public void fourOfAkindComparison() {
        Card[] cards1 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, QUEEN),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(SPADE, ACE)};
        Card[] cards2 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(SPADE, ACE)};
        assertThat(compare(cards1, cards2), is(-1));
    }

    @Test(enabled = false)
    public void straightComparison() {
        Card[] cards1 = new Card[] {new Card(CLUB, NINE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, TEN)};
        Card[] cards2 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, TEN)};
        assertThat(compare(cards1, cards2), is(-1));
    }

    @Test(enabled = false)
    public void straightWithOneComparison() {
        Card[] cards1 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, FIVE),new Card(CLUB, FOUR),new Card(CLUB, THREE),new Card(HEART, TWO)};
        Card[] cards2 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, TEN)};
        assertThat(compare(cards1, cards2), is(-1));
    }

    @Test(enabled = false)
    public void straightFlushComparison() {
        Card[] cards1 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(CLUB, TEN)};
        Card[] cards2 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, FIVE),new Card(CLUB, FOUR),new Card(CLUB, THREE),new Card(CLUB, TWO)};
        assertThat(compare(cards1, cards2), is(1));
    }

    @Test(enabled = false)
    public void straightFlushWithOneComparison() {
        Card[] cards1 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, FIVE),new Card(CLUB, FOUR),new Card(CLUB, THREE),new Card(CLUB, TWO)};
        Card[] cards2 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, FIVE),new Card(CLUB, FOUR),new Card(CLUB, THREE),new Card(CLUB, TWO)};
        assertThat(compare(cards1, cards2), is(0));
    }

    @Test(enabled = false)
    public void straightFlushWithSingleOneComparison() {
        Card[] cards1 = new Card[] {new Card(CLUB, SIX),new Card(CLUB, FIVE),new Card(CLUB, FOUR),new Card(CLUB, THREE),new Card(CLUB, TWO)};
        Card[] cards2 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, FIVE),new Card(CLUB, FOUR),new Card(CLUB, THREE),new Card(CLUB, TWO)};
        assertThat(compare(cards1, cards2), is(1));
    }

    @Test
    public void flushComparison() {
        Card[] cards1 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(CLUB, NINE)};
        Card[] cards2 = new Card[] {new Card(CLUB, ACE),new Card(CLUB, TEN),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(CLUB, NINE)};
        assertThat(compare(cards1, cards2), is(1));
    }

}
