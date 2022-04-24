import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        execute(System.in, System.out);
    }

    public static void execute(InputStream in, PrintStream out) {
        Scanner sc = new Scanner(in);

        // Line 1: the number of test cases (T)
        int t = sc.nextInt();

        try {
            for (int i = 1; i <= t; ++i) {
                processNext(i, sc, out);
            }
        } catch (NoSuchElementException e) {
            // End
        }
        out.flush();

    }

    private static int[][] X;
    private static int N;
    private static int P;

    public static void processNext(int caseNumber, Scanner sc, PrintStream out) {

        // N: number of customers
        N = sc.nextInt();

        // P: number of products per customer
        P = sc.nextInt();

        // Xi: target pressure
        X = new int[N][P];
        for (int n = 0; n < N; ++n) {
            var all = new ArrayList<Integer>(P);
            for (int p = 0; p < P; ++p) {
                all.add(sc.nextInt());
            }
            Collections.sort(all);
            for (int p = 0; p < P; ++p) {
                X[n][p] = all.get(p);
            }

        }
        costByInitAndCustomer = new HashMap<>();

        var result = findCost(0, 0);

        out.println("Case #" + caseNumber + ": " + result);
    }

    private static Map<String, Long> costByInitAndCustomer ;

    private static long findCost(int initX, int n) {

        String key = initX + "|" + n;
        Long value = costByInitAndCustomer.get(key);
        if (value != null) {
            return value;
        }

        if (n == N - 1) {
            // It is the last one. Don't care about the final value
            long cost = findCost(initX, n, -1);
            costByInitAndCustomer.put(key, cost);
            return cost;
        }

        // Current next
        long minAmount = Long.MAX_VALUE;
        for (int p = 0; p < P; ++p) {
            var nextInitialX = X[n][p];
            var testCurrent = findCost(initX, n, nextInitialX);
            var testNext = findCost(nextInitialX, n + 1);
            var currentAmount = testCurrent + testNext;
            if (currentAmount < minAmount) {
                minAmount = currentAmount;
            }
        }

        costByInitAndCustomer.put(key, minAmount);
        return minAmount;
    }

    private static long findCost(int initX, int n, int finalX) {

        var items = new LinkedList<Integer>();

        // Get all presures
        for (int p = 0; p < P; ++p) {
            var next = X[n][p];
            items.add(next);
        }

        // Remove last
        if (finalX != -1) {
            items.remove((Integer) finalX);
        }

        // Try all orders
        var combinations = allCombinations(items);
        long minAmount = Long.MAX_VALUE;
        for (var combination : combinations) {
            var cX = initX;
            long amount = 0;

            for (var next : combination) {
                amount += Math.abs(next - cX);
                cX = next;
            }

            // Last one
            if (finalX != -1) {
                amount += Math.abs(finalX - cX);
                cX = finalX;
            }

            if (amount < minAmount) {
                minAmount = amount;
            }
        }

        return minAmount;
    }


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
