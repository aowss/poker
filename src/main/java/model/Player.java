package model;

public class Player {

    private String name;
    private Hand hand;

    public Player(String name) {
        this.name = name;
    }

    public void newHand(Hand hand) {
        this.hand = hand;
    }

    @Override
    public String toString() {
        return '{' +
                    "name: '" + name + '\'' +
                    ", hand: " + ( hand == null ? "" : hand ) +
                '}';
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

}
