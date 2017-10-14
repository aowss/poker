package model;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

import static model.Game.HAND_SIZE;

import static util.Functions.keyComparatorUsingValue;

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
        switch(type1) {
            case STRAIGHT_FLUSH: case STRAIGHT:
                return hand1.getCards().get(0).compareTo(hand2.getCards().get(0)); //  Comparing the lowest card to avoid the problem with the Ace ( In certain circumstances the ace can be used as a low card, below the 2 )
            default:
                return similarNonStraightHandComparator.compare(hand1, hand2);
        }

    }

    /**
     * A predicates that tests that the cards in an ordered list of cards are consecutive
     * The asssumption is that the list is already sorted so no need to call <code>Collections.sort(cards)</code>
     */
    private static Predicate<List<Card>> inARow = cards -> {
        int firstValue = cards.get(0).getRank().getValue();
        int totalValue = cards.stream().map(card -> card.getRank()).collect(summingInt(Card.Rank::getValue));
        if (totalValue == HAND_SIZE * ( firstValue + HAND_SIZE / 2) ) {
            return true;
        } else {
            //  Case of the ACE needs to be dealt with : In certain circumstances the ace can be used as a low card, below the 2
            if (cards.get(cards.size() - 1).getRank() == Card.Rank.ACE && totalValue == 28) {
                return true;
            } else {
                return false;
            }
        }
    };

    /**
     * A function that determines the hand type
     */
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
                throw new RuntimeException("You can't have more than " + HAND_SIZE + " cards or 5 cards of the same rank !");
        }
    };

    /**
     * A function that sorts the keys in the map according to the natural order of the value then the key
     * @param a <code>Map</code> containing the rank as the key and the number of occurrences of that rank in the hand as the value
     * @return a <code>List</code> containing the ranks ordered by the number of occurrences then by the rank
     */
    public static Function<Map<Card.Rank, Long>, List<Card.Rank>> sortMapUsingValues = map -> {
        Comparator<Card.Rank> firstComparator = keyComparatorUsingValue(map);
        return map.keySet().stream().sorted(firstComparator.reversed().thenComparing(Card.Rank::getValue, Comparator.reverseOrder())).collect(Collectors.toList());
    };

    public static Comparator<List<Card.Rank>> ranksComparator = ( list1, list2) -> {
        if (list1.size() != list2.size()) throw new RuntimeException("The 2 lists must have the same size");
        for (int i = 0; i < list1.size(); i++) {
            int result = Integer.valueOf(list1.get(i).getValue()).compareTo(list2.get(i).getValue());
            if (result != 0) {
                return result;
            }
        }
        return 0;
    };

    /**
     * Compares hands that are not straights.
     */
    public static Comparator<Hand> similarNonStraightHandComparator = ( hand1, hand2) -> {
        Map<Card.Rank, Long> ranks1 = hand1.getCards().stream().collect(groupingBy(Card::getRank, counting()));
        List<Card.Rank> sortedRanks1 = sortMapUsingValues.apply(ranks1);
        //List<Card.Rank> sortedRanks1 = sortMapKeysUsingValues().apply(ranks1);
        Map<Card.Rank, Long> ranks2 = hand2.getCards().stream().collect(groupingBy(Card::getRank, counting()));
        List<Card.Rank> sortedRanks2 = sortMapUsingValues.apply(ranks2);
        return ranksComparator.compare(sortedRanks1, sortedRanks2);
    };

}
