import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        new Solution(System.in, System.out).execute();
    }

    private final Scanner sc;
    private final PrintStream out;

    public Solution(InputStream in, PrintStream out) {
        this.sc = new Scanner(in);
        this.out = out;
    }

    public void execute() {
        // 1: the number of test cases (T)
        int t = sc.nextInt();

        try {
            for (int i = 1; i <= t; ++i) {
                processNext(i);
            }
        } catch (NoSuchElementException e) {
            // End
        }
        out.flush();

    }

    private List<Integer> all;
    private List<Integer> pile1;
    private List<Integer> pile2;
    private long totalPile1;
    private long totalPile2;
    private int N;

    private long finalSum = 0;

    public void processNext(int caseNumber) {

        // N: half integers
        N = sc.nextInt();

        // Reset
        all = new ArrayList<>(2 * N);
        pile1 = new ArrayList<>(N);
        pile2 = new ArrayList<>(N);
        totalPile1 = 0;
        totalPile2 = 0;

        // Send our half
        for (int i = 1; i <= N; ++i) {
            all.add(i);
            if (i != 1) {
                out.print(' ');
            }
            out.print(i);
        }
        out.println();
        out.flush();

        // Get the other half
        for (int i = 1; i <= N; ++i) {
            var next = sc.nextInt();
            if (next == -1) {
                throw new RuntimeException("Judge said no");
            }
            all.add(next);
        }

        // Sort them
        Collections.sort(all);
        Collections.reverse(all);

        // Calculate the sum per pile
        all.forEach(it -> finalSum += it);
        finalSum /= 2;

        // Separate the piles
        for (int i = 0; i < 2 * N; i = i + 2) {
            pile1.add(all.get(i));
            totalPile1 += all.get(i);
            pile2.add(all.get(i + 1));
            totalPile2 += all.get(i + 1);
        }

        while (totalPile1 != totalPile2) {
            swap();
        }

        // End
        System.err.println("END");
        System.err.println(totalPile1);
        System.err.println(totalPile2);

    }

    private void swap() {
        var delta = finalSum - totalPile1;
        System.err.println("---");
        System.err.println(delta);

        // pile2swap - pile1swap = delta
        for (int a = 0; a < N; ++a) {
            var pile1swap = pile1.get(a);
            for (int b = 0; b < N; ++b) {
                var pile2swap = pile2.get(b);

                var close = (pile2swap - pile1swap) -delta;
                if (close < 0) {
                    continue;
                }
                // TODO Not working - If many swaps needed, won't be an exact match
                if (pile2swap - pile1swap == delta) {
                    pile1.set(a, pile2swap);
                    pile2.set(b, pile1swap);
                    totalPile1 = totalPile1 - pile1swap + pile2swap;
                    totalPile2 = totalPile2 - pile2swap + pile1swap;
                    return;
                }
            }
        }

    }


}
