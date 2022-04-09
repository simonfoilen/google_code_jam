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

    public static void processNext(int caseNumber, Scanner inputScanner, PrintStream out) {

        // TODO Not working - Not taking into account all the nexts

        // E: the number of exercises
        var E = inputScanner.nextInt();

        // W: the number of types of weights (1, 2, 3...)
        var W = inputScanner.nextInt();

        var Xcurrent = new int[W];
        var Xnext = new int[W];
        var stack = 0;
        var totalSteps = 0;

        for (int e = 0; e < E; ++e) {

            // Change the current
            if (e == 0) {
                // Read current
                for (int w = 0; w < W; ++w) {
                    // Amount of W
                    Xcurrent[w] = inputScanner.nextInt();
                }
            } else {
                Xcurrent = Xnext;
                Xnext = new int[W];
            }

            var isLast = e + 1 == E;
            if (!isLast) {
                // Read next
                for (int w = 0; w < W; ++w) {
                    // Amount of W
                    Xnext[w] = inputScanner.nextInt();
                }
            }

            // Put the current (total - stack)
            var currentTotal = 0;
            for (int i = 0; i < W; ++i) {
                currentTotal += Xcurrent[i];
            }
            totalSteps += (currentTotal - stack);
            stack = currentTotal;

            if (!isLast) {
                // Remove the diff
                var diff = 0;
                for (int i = 0; i < W; ++i) {
                    var toRemove = Xcurrent[i] - Xnext[i];
                    if (toRemove > 0) {
                        diff += toRemove;
                    }
                }
                totalSteps += diff;
                stack -= diff;
            }

        }

        // Lastly, remove the stack
        totalSteps += stack;

        out.println("Case #" + caseNumber + ": " + totalSteps);
    }

}
