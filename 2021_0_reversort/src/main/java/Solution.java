import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

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

        // Line 3: contains N distinct integers L1, L2, ..., LN
        int[] l = new int[n];
        for (int i = 0; i < n; ++i) {
            l[i] = inputScanner.nextInt();
        }

        // Reversort
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
            int cost = reverse(l, i, currentMinIndex);

            // Cost
            totalCost += cost;
        }

        out.println("Case #" + caseNumber + ": " + totalCost);
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
}
