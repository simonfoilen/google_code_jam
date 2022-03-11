import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static void processNext(int caseNumber, Scanner inputScanner, PrintStream out) {

        // Line 2: the number of elements in the input list (N)
        int n = inputScanner.nextInt();

        // Line 3: the desired cost (C)
        int c = inputScanner.nextInt();

        // Check min
        var cost = n - 1;
        if (c < cost) {
            out.println("Case #" + caseNumber + ": IMPOSSIBLE");
            return;
        }

        // Find needed reverses
        var costLeft = c - cost;
        var reversesToApply = new boolean[n - 1];
        for (int i = 0; i < n - 1; ++i) {
            var reverseCost = n - i - 1;
            if (costLeft >= reverseCost) {
                reversesToApply[i] = true;
                costLeft -= reverseCost;
            }
        }

        // Over the max?
        if (costLeft > 0) {
            out.println("Case #" + caseNumber + ": IMPOSSIBLE");
            return;
        }

        // Compute the reversals
        var s = new int[n];
        for (int i = 0; i < n; ++i) {
            s[i] = i + 1;
        }
        for (int i = n - 2; i >= 0; --i) {
            if (reversesToApply[i]) {
                reverse(s, i, n - 1);
            }
        }

        out.println("Case #" + caseNumber + ": " + intArrayToString(s));
    }

    protected static int reverse(int[] l, int start, int end) {
        var subLength = end - start;
        var midPart = subLength / 2;
        for (int i = 0; i <= midPart; ++i) {
            var tmp = l[i + start];
            l[i + start] = l[subLength - i + start];
            l[subLength - i + start] = tmp;
        }

        return end - start + 1;
    }

    private static String intArrayToString(int[] l) {
        return IntStream.of(l).mapToObj(i -> String.valueOf(i)).collect(Collectors.joining(" "));
    }
}
