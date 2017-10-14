import model.Card;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static model.Card.Rank.*;
import static model.Card.Suit.*;
import static model.HandComparator.sortMapUsingValues;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HandOrderingTest {

    private void validate(Card[] cards, int size, Card.Rank[] expectedResult) {
        Map<Card.Rank, Long> ranks = Arrays.stream(cards).collect(groupingBy(Card::getRank, counting()));
        List<Card.Rank> sortedRanks = sortMapUsingValues.apply(ranks);
        assertThat(sortedRanks.size(), is(size));
        assertThat(sortedRanks, is(Arrays.asList(expectedResult)));
    }

    @Test
    public void nothig() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, TWO)};
        validate(cards, 5, new Card.Rank[] { ACE, KING, QUEEN, JACK, TWO });
    }

    @Test
    public void onePair() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, JACK)};
        validate(cards, 4, new Card.Rank[] { JACK, ACE, KING, QUEEN });
    }

    @Test
    public void twoPairs() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(SPADE, KING),new Card(CLUB, JACK),new Card(HEART, JACK)};
        validate(cards, 3, new Card.Rank[] { KING, JACK, ACE});
    }

    @Test
    public void threeOfAKind() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(HEART, NINE)};
        validate(cards, 3, new Card.Rank[] { ACE, KING, NINE });
    }

    @Test
    public void fullHouse() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(HEART, KING)};
        validate(cards, 2, new Card.Rank[] { ACE, KING });
    }

    @Test
    public void fourOfAkind() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(HEART, ACE),new Card(DIAMOND, ACE),new Card(SPADE, ACE)};
        validate(cards, 2, new Card.Rank[] { ACE, KING });
    }

    @Test
    public void straight() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(HEART, TEN)};
        validate(cards, 5, new Card.Rank[] { ACE, KING, QUEEN, JACK, TEN });
    }

    @Test
    public void straightWithOne() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, FIVE),new Card(CLUB, FOUR),new Card(CLUB, THREE),new Card(HEART, TWO)};
        validate(cards, 5, new Card.Rank[] { ACE, FIVE, FOUR, THREE, TWO });
    }

    @Test
    public void straightFlush() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(CLUB, TEN)};
        validate(cards, 5, new Card.Rank[] { ACE, KING, QUEEN, JACK, TEN });
    }

    @Test
    public void straightFlushWithOne() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, FIVE),new Card(CLUB, FOUR),new Card(CLUB, THREE),new Card(CLUB, TWO)};
        validate(cards, 5, new Card.Rank[] { ACE, FIVE, FOUR, THREE, TWO });
    }

    @Test
    public void flush() {
        Card[] cards = new Card[] {new Card(CLUB, ACE),new Card(CLUB, KING),new Card(CLUB, QUEEN),new Card(CLUB, JACK),new Card(CLUB, NINE)};
        validate(cards, 5, new Card.Rank[] { ACE, KING, QUEEN, JACK, NINE });
    }

}
