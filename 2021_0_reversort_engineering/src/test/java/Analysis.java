import com.foilen.smalltools.tuple.Tuple2;
import com.google.common.collect.ComparisonChain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Analysis {

    public static void main(String[] args) {

        // Create all possibilities
        List<int[]> possibilities = new ArrayList<>();
        List<Integer> deck = new ArrayList<>();
        deck.addAll(Arrays.asList(1, 2, 3, 4, 5));
        findPossibilities(deck, possibilities);

        possibilities.stream()
                .map(p -> {
                    int[] copyOfP = p.clone();
                    return new Tuple2<>(getTotalCost(p), copyOfP);
                })
                .sorted((a, b) -> ComparisonChain.start()
                        .compare(a.getA(), b.getA())
                        .result())
                .forEach(costAndPossibility -> {
                    System.out.println(costAndPossibility.getA() + " : " + intArrayToString(costAndPossibility.getB()));
                });

    }

    private static void findPossibilities(List<Integer> deck, List<int[]> possibilities) {
        List<Integer> currentPossibility = new ArrayList<>();
        findPossibilities(deck, currentPossibility, possibilities);
    }

    protected static void findPossibilities(List<Integer> deck, List<Integer> currentPossibility, List<int[]> possibilities) {

        // It is the possibility
        if (deck.isEmpty()) {
            var possibility = new int[currentPossibility.size()];
            for (int i = 0; i < currentPossibility.size(); ++i) {
                possibility[i] = currentPossibility.get(i);
            }
            possibilities.add(possibility);
            return;
        }

        // All next possibilities
        for (int i = 0; i < deck.size(); ++i) {
            List<Integer> nextPossibility = new ArrayList<>();
            nextPossibility.addAll(currentPossibility);
            List<Integer> nextDeck = new ArrayList<>();
            nextDeck.addAll(deck);

            // Pick one value
            var value = nextDeck.remove(i);
            nextPossibility.add(value);
            findPossibilities(nextDeck, nextPossibility, possibilities);
        }

    }

    private static String intArrayToString(int[] l) {
        return IntStream.of(l).mapToObj(i -> String.valueOf(i)).collect(Collectors.joining(" "));
    }


    public static int getTotalCost(int[] l) {
        var n = l.length;

        int totalCost = 0;
        for (int i = 0; i < n - 1; ++i) {

            // Find smaller
            int currentMinIndex = i;
            int currentMinValue = l[currentMinIndex];
            for (int j = i; j < n; ++j) {
                if (l[j] < currentMinValue) {
                    currentMinIndex = j;
                    currentMinValue = l[currentMinIndex];
                }
            }

            // Reverse part
            int cost = Solution.reverse(l, i, currentMinIndex);

            // Cost
            totalCost += cost;
        }
        return totalCost;
    }

}
