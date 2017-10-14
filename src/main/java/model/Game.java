package model;

import java.util.*;
import java.util.stream.Stream;

/**
 * The rules are taken from https://www.pagat.com/poker/rules
 */
public class Game {

    /**
     * There are 52 cards in the pack
     */
    public final static int DECK_SIZE = 52;

    public final static int HAND_SIZE = 5;

    /**
     * Traditionally, poker has been thought of as a game for 2 to 7 players
     */
    public final static int MIN_PLAYERS = 2;
    public final static int MAX_PLAYERS = 7;

    //  <code>List</code> is more convenient than <code>Set</code> for random access and more convenient than an array; uniqueness is ensured by the <code>Set</code> in the constructor
    private List<Player> players = new ArrayList<>();
    private Deck deck;
    //  Not sure if a dealer is needed
    private Dealer dealer;

    public Game(Set<String> playerNames) {

        if (playerNames == null || playerNames.size() == 0) throw new RuntimeException("No players !");
        if (playerNames.size() < MIN_PLAYERS || playerNames.size() > MAX_PLAYERS) throw new RuntimeException("The number of players must be between " + MIN_PLAYERS + " and " + MAX_PLAYERS);

        dealer = new Dealer();
        playerNames.stream().forEach(name -> players.add(new Player(name)));
        deck = Deck.getShuffledDeck();

    }

    public void start() {
        giveHands();
    }

    private void giveHands() {
        Stack<Card> cards = deck.getCards();
        //  There are easier ways to do that but they don't simulate the breadth-first aspect of the dealing
        Card[][] hands = new Card[players.size()][HAND_SIZE];
        for (int i = 0; i < HAND_SIZE; i++) {
            for (int j = 0; j < players.size(); j++) {
                Card card = cards.pop();
                Player player = players.get(j);
                System.out.println(card + " -> " + player);
                hands[j][i] = card;
            }
        }
        for (int i = 0; i < players.size(); i++) {
            players.get(i).newHand(new Hand(hands[i]));
        }
        System.out.println(players);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Dealer getDealer() {
        return dealer;
    }

}
