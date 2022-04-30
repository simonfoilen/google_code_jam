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
    final static long MAX = (long) Math.pow(10, 18);
    final static long MIN = -MAX;

    public static void processNext(int caseNumber, Scanner sc, PrintStream out) {

        // N: initial
        var N = sc.nextInt();
        // K: max to add
        var K = sc.nextInt();

        // E: elements
        long[] E = new long[N];
        long sumE = 0;
        long sumESqr = 0;
        for (int i = 0; i < N; ++i) {
            var e = sc.nextLong();
            E[i] = e;

            sumE += e;
            sumESqr += (e * e);
        }

        try {
            List<Long> Z = new ArrayList<>();
            findNext(Z, sumE, sumESqr, K);

            out.print("Case #" + caseNumber + ": ");
            var first = true;
            for (var z : Z) {
                if (first) {
                    first = false;
                } else {
                    out.print(" ");
                }
                out.print(z);
            }
            out.println();
        } catch (Exception e) {
            out.println("Case #" + caseNumber + ": IMPOSSIBLE");
        }
    }

    private static void findNext(List<Long> z, long sumE, long sumESqr, int K) {

        long left = sumE * sumE;

        // Already correct
        if (left == sumESqr) {
            if (z.isEmpty()) {
                z.add(0L);
                if (K == 0) {
                    throw new RuntimeException("impossible");
                }
            }
            return;
        }

        if (K == 0) {
            throw new RuntimeException("impossible");
        }

        if (left < sumESqr) {
            findNextPositive(z, sumE, sumESqr, K);
        } else {
            findNextNegative(z, sumE, sumESqr, K);
        }

    }

    private static void findNextNegative(List<Long> z, long sumE, long sumESqr, int K) {
        // Next number is negative
        for (long i = -1; i >= MIN; --i) {
            long nextSumE = sumE + i;
            long nextLeft = nextSumE * nextSumE;
            long nextSumESqr = sumESqr + (i * i);

            // Found
            if (nextLeft == nextSumESqr) {
                z.add(i);
                return;
            }

            // Changed direction
            if (nextLeft < nextSumESqr) {
                z.add(i);
                findNext(z, nextSumE, nextSumESqr, K - 1);
                return;
            }
        }
        throw new RuntimeException("impossible");
    }

    private static void findNextPositive(List<Long> z, long sumE, long sumESqr, int K) {

        long min = 1;
        long max = MAX;

        while ((max - min) > 0) {

            long next = (max - min) / 2 + min;

            long nextSumE = sumE + next;
            long nextLeft = nextSumE * nextSumE;
            long nextSumESqr = sumESqr + (next * next);

            // Found
            if (nextLeft == nextSumESqr) {
                z.add(next);
                return;
            }

            if (nextLeft < nextSumESqr) {
                max = next;
            } else {
                min = next;
            }

        }

        // Final value
        long nextSumE = sumE + min;
        long nextLeft = nextSumE * nextSumE;
        long nextSumESqr = sumESqr + (min * min);

        if (nextLeft > nextSumESqr) {
            z.add(min);
            findNext(z, nextSumE, nextSumESqr, K - 1);
            return;
        }
        throw new RuntimeException("impossible");

    }

}
