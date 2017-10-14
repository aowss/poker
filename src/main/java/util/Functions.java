package util;

import model.Card;
import model.Hand;

import static java.util.stream.Collectors.*;
import static model.Game.HAND_SIZE;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Functions {

    /**
     * A random unique integer generator
     * @param the minimum value ( inclusive )
     * @param the maximum value ( exclusive )
     * @param the number of values to generate
     * @return a stream containing the number of unique integer specified
     */
    public static BiFunction<Integer, Integer, Function<Integer, IntStream>> uniqueRandomIntegers = (min, max) -> count -> {
        if (min < 0 || max < 0 || max <= min) throw new RuntimeException("The maximum value must be strictly greater than the minimum value; they both must be strictly positive values");
        if (count > ( max - min )) throw new RuntimeException("The number of unique values must be less than the range size");
        Random random = new Random(System.nanoTime());
        return random.ints(min, max).distinct().limit(count);
    };

    /**
     * A <code>Comparator</code> that compares the keys in a map according to their value
     * @param map The map that contains the keys and the values
     * @param <T> The map's key type
     * @param <U> The map's value type
     * @return the comparator
     */
    public static <T, U extends Comparable> Comparator<T> keyComparatorUsingValue(Map<T, U> map) {
        return (key1, key2) -> {
            return map.get(key1).compareTo(map.get(key2));
        };
    }

    /**
     * Get a list of the keys in a map sorted in reverse order of the values natural order than the keys natural order
     * @param <T> The map's key type
     * @param <U> The map's value type
     * @return a list of the values sorted in reverse order of the values natural order than the keys natural order
     */
    public static <T extends Comparable, U extends Comparable> Function<Map<T, U>, List<T>> sortMapKeysUsingValues() {
        return (Map<T,U> map) -> {
            Comparator<T> firstComparator = keyComparatorUsingValue(map);
            Stream<T> sortedKeys = map.
                    keySet().
                    stream().
                    sorted(firstComparator.
                            reversed().
                            thenComparing(Comparator.reverseOrder()));
            return sortedKeys.collect(Collectors.toList());
        };
    };

}
