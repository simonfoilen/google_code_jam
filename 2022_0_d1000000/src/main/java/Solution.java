import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
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

        // N
        var N = inputScanner.nextInt();
        var s = new ArrayList<Integer>(N);
        for (int i = 0; i < N; ++i) {
            s.add(inputScanner.nextInt());
        }

        // Sort
        Collections.sort(s);

        // Ignore if next smaller
        var next = 1;
        var it = s.iterator();
        while (it.hasNext()) {
            if (it.next() >= next) {
                ++next;
            }
        }

        out.println("Case #" + caseNumber + ": " + (next - 1));
    }

}
