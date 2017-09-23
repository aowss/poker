import model.Card;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SuitComparatorTest {

    @Test
    public void equalSuits() {
        assertThat(Card.Suit.CLUB.compareTo(Card.Suit.CLUB), is(0));
    }

    @Test
    public void lowerSuit() {
        assertThat(Card.Suit.HEART.compareTo(Card.Suit.SPADE), lessThanOrEqualTo(-1));
    }

    @Test
    public void higherSuit() {
        assertThat(Card.Suit.SPADE.compareTo(Card.Suit.CLUB), greaterThanOrEqualTo(1));
    }

}
