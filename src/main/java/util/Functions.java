package util;

import model.Card;
import model.Hand;

import static java.util.stream.Collectors.*;
import static model.Game.HAND_SIZE;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Functions {

    public static BiFunction<Integer, Integer, Function<Integer, IntStream>> uniqueRandomIntegers = (min, max) -> count -> {
        Random random = new Random(System.nanoTime());
        return random.ints(min, max).distinct().limit(count);
    };




    private static Function<Map<Card.Rank, Long>, Comparator<Card.Rank>> occurrenceComparator = map -> (rank1, rank2) -> {
//        System.out.println("comparing " + rank1 + " and " + rank2 + " = " + ( map.get(rank1) - map.get(rank2) ));
        return (int)(map.get(rank1) - map.get(rank2));
    };

    /**
     *
     * @param a <code>Map</code> containing the rank as the key and the number of occurrences of that rank in the hand as the value
     * @return a <code>List</code> containing the ranks ordered by the number of occurrences then by the rank
     */
    public static Function<Map<Card.Rank, Long>, List<Card.Rank>> sortMapUsingValues = map -> {
        Comparator<Card.Rank> firstComparator = occurrenceComparator.apply(map);
        return map.keySet().stream().sorted(firstComparator.thenComparing(Card.Rank::getValue).reversed()).collect(Collectors.toList());
    };

    public static Comparator<List<Card.Rank>> handComparator = ( list1, list2) -> {
        if (list1.size() != list2.size()) throw new RuntimeException("The 2 lists must have the same size");
        for (int i = 0; i < list1.size(); i++) {
            int result = Integer.valueOf(list1.get(i).getValue()).compareTo(list2.get(i).getValue());
            if (result != 0) {
                return result;
            }
        }
        return 0;
    };

}
