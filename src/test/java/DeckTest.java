import model.Card;
import model.Deck;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DeckTest {

    @Test
    public void newDeck() {
        Stack<Card> deck = new Deck().getCards();
        assertThat(deck.size(), is(52));
        Set<Card> unique = new HashSet<>();
        deck.stream().forEach(card -> assertThat(unique.add(card), is(true)));
    }

}
