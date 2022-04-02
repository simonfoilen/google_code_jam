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

        // The 3 printers colors
        var printers = new int[3][4];
        for (int p = 0; p < 3; ++p) {
            for (int c = 0; c < 4; ++c) {
                printers[p][c] = inputScanner.nextInt();
            }
        }

        // Find the minimum of each printer
        var minOfColors = new int[4];
        for (int c = 0; c < 4; ++c) {
            minOfColors[c] = 1_000_000;
            for (int p = 0; p < 3; ++p) {
                minOfColors[c] = Math.min(minOfColors[c], printers[p][c]);
            }
        }

        // Choose
        var left = 1_000_000;
        var colors = new int[4];
        for (int c = 0; c < 4; ++c) {
            var pick = Math.min(left, minOfColors[c]);
            left -= pick;
            colors[c] = pick;
        }

        if (left == 0) {
            out.println("Case #" + caseNumber + ": " + colors[0] + " " + colors[1] + " " + colors[2] + " " + colors[3]);
        } else {
            out.println("Case #" + caseNumber + ": IMPOSSIBLE");
        }
    }

}
