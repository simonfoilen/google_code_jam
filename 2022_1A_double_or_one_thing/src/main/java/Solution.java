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

        // Skip to end of line
        sc.nextLine();

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

        // the string
        var line = inputScanner.nextLine();

        out.print("Case #" + caseNumber + ": ");

        boolean noMore = false;
        for (int i = 0; i < line.length(); ++i) {
            var current = line.charAt(i);

            // If no more, don't double
            if (noMore) {
                out.print(current);
                continue;
            }

            // If last, never double
            if (i == line.length() - 1) {
                out.print(current);
                continue;
            }
            var next = line.charAt(i + 1);

            // If next is bigger double current
            if (next > current) {
                out.print(current);
                out.print(current);
                continue;
            }

            // If next is equal, double if future change is bigger
            if (next == current) {
                var futureIsBigger = false;
                for (int y = i + 1; y < line.length(); ++y) {
                    var future = line.charAt(y);
                    if (future > current) {
                        futureIsBigger = true;
                        break;
                    }
                    if (future < current) {
                        break;
                    }
                }

                if (futureIsBigger) {
                    out.print(current);
                    out.print(current);
                    continue;
                }
            }

            out.print(current);

        }

        out.println();
    }

}
