import model.Game;
import model.Player;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static model.Game.HAND_SIZE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GameTest {

    @Test
    public void newGame() {
        Game game = new Game(new HashSet<>(Arrays.asList("Player 1", "Player 2")));
        game.start();
        //  Check number of players
        assertThat(game.getPlayers().size(), is(2));
        //  Check number of cards per hand
        game.getPlayers().forEach(player -> assertThat(player.getHand().getCards().size(), is(HAND_SIZE)));
        //  Check cards uniqueness accross
        assertThat(
                game.getPlayers().
                    stream().
                    flatMap(player -> player.getHand().getCards().stream()).
                    distinct().
                    collect(Collectors.toSet()).size(),
                is(HAND_SIZE * game.getPlayers().size()));
    }

}
