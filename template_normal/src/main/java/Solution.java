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

        // xxxx: XXXXX
        // TODO var xxxx = inputScanner.nextInt();

        out.println("Case #" + caseNumber + ": ");
    }

}
