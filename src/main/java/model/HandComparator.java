package model;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static model.Game.HAND_SIZE;

public class HandComparator implements Comparator<Hand> {

    /**
     * R1 : Any {@link Hand hand} of a higher {@link model.Hand.Type type} beats any hand of a lower {@link model.Hand.Type type}.
     * R2 : When comparing two {@link Hand hands} of the same {@link model.Hand.Type type}, the ranking is determined by the {@link model.Card.Rank ranks} of the individual {@link Card cards}.
     * The most numerous {@link model.Card.Rank rank} of {@link Card cards} in each {@link Hand hand} is compared first; if these are equal, any less numerous {@link model.Card.Rank ranks} are compared.
     * When two {@link model.Card.Rank ranks} are equally numerous, the highest-ranking {@link Card cards} are compared before the others.
     * @param hand1
     * @param hand2
     * @return
     */
    @Override
    public int compare(Hand hand1, Hand hand2) {

        //  R1
        Hand.Type type1 = determineHandType.apply(hand1);
        Hand.Type type2 = determineHandType.apply(hand2);
        int result = type1.compareTo(type2);

        if (result != 0) return result;

        //  R2
        List<Card> cards1 = hand1.getCards();
        List<Card> cards2 = hand2.getCards();
        switch(type1) {
            case STRAIGHT_FLUSH: case FLUSH: case STRAIGHT:
                return cards1.get(0).compareTo(cards2.get(0)) * -1; //  Comparing the lowest card to avoid the problem with the Ace ( In certain circumstances the ace can be used as a low card, below the 2 )
            case NOTHING:
                return cards1.get(0).compareTo(cards2.get(0));
            default:
                return 0;
        }

    }

    private static Predicate<List<Card>> inARow = cards -> {
        //  The list is already sorted
        //        Collections.sort(cards);
        int firstValue = cards.get(0).getRank().getValue();
        int totalValue = cards.stream().map(card -> card.getRank()).collect(summingInt(Card.Rank::getValue));
        if (totalValue == HAND_SIZE * ( firstValue + HAND_SIZE / 2) ) {
            return true;
        } else {
            //  Case of the ACE needs to be dealt with ( In certain circumstances the ace can be used as a low card, below the 2 )
            if (cards.get(cards.size() - 1).getRank() == Card.Rank.ACE && totalValue == 28) {
                return true;
            } else {
                return false;
            }
        }
    };

    public static Function<Hand, Hand.Type> determineHandType = hand -> {
        Map<Card.Rank, Long> ranks = hand.getCards().stream().collect(groupingBy(Card::getRank, counting()));
        switch (ranks.size()) {
            case 2:
                if (ranks.values().contains(4L)) {  //  4 & 1
                    return Hand.Type.FOUR_OF_A_KIND;
                } else {                            //  3 & 2
                    return Hand.Type.FULL_HOUSE;
                }
            case 3:
                if (ranks.values().contains(3L)) {  //  3, 1 & 1
                    return Hand.Type.THREE_OF_A_KIND;
                } else {                            //  2, 2 & 1
                    return Hand.Type.TWO_PAIRS;
                }
            case 4:                                 //  2, 1, 1 & 1
                return Hand.Type.PAIR;
            case 5:                                 //  1, 1, 1, 1 & 1
                Map<Card.Suit, Long> suits = hand.getCards().stream().collect(groupingBy(Card::getSuit, counting()));
                boolean ordered = inARow.test(hand.getCards());
                switch (suits.size()) {
                    case 1:
                        if (ordered) {
                            return Hand.Type.STRAIGHT_FLUSH;
                        } else {
                            return Hand.Type.FLUSH;
                        }
                    default:
                        if (ordered) {
                            return Hand.Type.STRAIGHT;
                        } else {
                            return Hand.Type.NOTHING;
                        }
                }
            default:
                throw new RuntimeException("You can't have more than 5 cards or 5 cards of the same rank !");
        }
    };

}
