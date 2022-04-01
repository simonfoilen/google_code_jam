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

        // Line 2: Number of rows (R) ; Number of columns (C)
        int r = inputScanner.nextInt();
        int c = inputScanner.nextInt();

        out.println("Case #" + caseNumber + ":");

        // Show lines
        for (int x = 0; x < r; ++x) {


            // Show top
            if (x == 0) {
                for (int y = 0; y < c; ++y) {
                    if (y == 0) {
                        out.print("..");
                    } else {
                        out.print("+-");
                    }
                }
            } else {
                for (int y = 0; y < c; ++y) {
                    out.print("+-");
                }
            }
            out.println("+");

            // Show punching part
            if (x == 0) {
                for (int y = 0; y < c; ++y) {
                    if (y == 0) {
                        out.print("..");
                    } else {
                        out.print("|.");
                    }
                }
            } else {
                for (int y = 0; y < c; ++y) {
                    out.print("|.");
                }
            }
            out.println("|");


        }

        // Show bottom
        for (int y = 0; y < c; ++y) {
            out.print("+-");
        }
        out.println("+");

    }

}
