import java.util.*;

/**
 * When given a list of items, try to put then in all the possible orders
 */
public class AllCardCombinations {

    public static List<Integer[]> allCombinations(List<Integer> items) {

        var results = new ArrayList<Integer[]>();

        Integer[] nextResult = new Integer[items.size()];
        boolean[] itemAvailable = new boolean[items.size()];
        for (int i = 0; i < items.size(); ++i) {
            itemAvailable[i] = true;
        }

        allCombinations(items.toArray(new Integer[items.size()]), itemAvailable, results, 0, nextResult);

        return results;
    }

    private static void allCombinations(Integer[] allItems, boolean[] itemAvailable, List<Integer[]> results, int resultPos, Integer[] nextResult) {

        // Is a new result
        int itemsCount = allItems.length;
        if (resultPos == itemsCount) {
            results.add(Arrays.copyOf(nextResult, itemsCount));
            return;
        }

        // Try all
        for (int i = 0; i < itemsCount; ++i) {

            // Check if available at the position
            if (!itemAvailable[i]) {
                continue;
            }

            // Pick the one at the position
            itemAvailable[i] = false;
            nextResult[resultPos] = allItems[i];
            allCombinations(allItems, itemAvailable, results, resultPos + 1, nextResult);

            // Add back where it was
            itemAvailable[i] = true;
        }

    }
}
