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

        // Line 2: X and Y and a string S representing the two costs and the current state of the mural, respectively
        int x = inputScanner.nextInt();
        int y = inputScanner.nextInt();
        inputScanner.skip("\\s");
        String untilEndOfLine = inputScanner.nextLine();
        char[] s = new char[untilEndOfLine.length()];
        untilEndOfLine.getChars(0, untilEndOfLine.length(), s, 0);


        // Replace all "?" with lowest cost locally
        int startQM = 0;
        int endQM = -1;
        while (startQM < s.length) {
            // Find start of next "?"
            startQM = indexOf(s, '?', startQM);
            if (startQM == -1) {
                break;
            }

            // Find end of "?"
            endQM = indexLastOf(s, '?', startQM);

            replaceUnknowns(x, y, s, startQM, endQM);

            // Advance
            startQM = endQM + 1;
        }

        out.println("Case #" + caseNumber + ": " + calculateCost(x, y, s));
    }

    /**
     * Search the index of the next character that is the desired character.
     *
     * @param s      the char array to search for occurrence
     * @param c      the char to search
     * @param offset the offset to start searching from
     * @return the index or -1
     */
    protected static int indexOf(char[] s, char c, int offset) {
        for (int i = offset; i < s.length; ++i) {
            if (s[i] == c) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Search the index of the next character that is NOT the desired character.
     *
     * @param s      the char array to search for occurrence
     * @param c      the char to search
     * @param offset the offset to start searching from
     * @return the index or -1
     */
    protected static int indexLastOf(char[] s, char c, int offset) {
        for (int i = offset; i < s.length; ++i) {
            if (s[i] != c) {
                return i - 1;
            }
        }
        return s.length - 1;
    }

    protected static void replaceUnknowns(int x, int y, char[] s, int startQM, int endQM) {

        boolean hasPrev = startQM > 0;
        boolean hasNext = endQM < s.length - 1;

        // Prepare local array
        var localAmountOfQM = endQM - startQM + 1;
        if (localAmountOfQM > 5) {
            localAmountOfQM = localAmountOfQM % 2 == 0 ? 4 : 5;
        }
        var localLength = localAmountOfQM;
        if (hasPrev) {
            ++localLength;
        }
        if (hasNext) {
            ++localLength;
        }

        var localS = new char[localLength];
        if (hasPrev) {
            localS[0] = s[startQM - 1];
        }
        if (hasNext) {
            localS[localS.length - 1] = s[endQM + 1];
        }

        // All C
        int lowestCost = calculateCost(x, y, fillWithPattern(localS, hasPrev ? 1 : 0, hasNext ? localS.length - 2 : localS.length - 1, new char[]{'C'}));
        char[] pattern = new char[]{'C'};

        // All J
        int nextCost = calculateCost(x, y, fillWithPattern(localS, hasPrev ? 1 : 0, hasNext ? localS.length - 2 : localS.length - 1, new char[]{'J'}));
        if (nextCost < lowestCost) {
            lowestCost = nextCost;
            pattern = new char[]{'J'};
        }

        // Alternate CJ
        nextCost = calculateCost(x, y, fillWithPattern(localS, hasPrev ? 1 : 0, hasNext ? localS.length - 2 : localS.length - 1, new char[]{'C', 'J'}));
        if (nextCost < lowestCost) {
            lowestCost = nextCost;
            pattern = new char[]{'C', 'J'};
        }

        // Alternate JC
        nextCost = calculateCost(x, y, fillWithPattern(localS, hasPrev ? 1 : 0, hasNext ? localS.length - 2 : localS.length - 1, new char[]{'J', 'C'}));
        if (nextCost < lowestCost) {
            lowestCost = nextCost;
            pattern = new char[]{'J', 'C'};
        }

        // Fill
        fillWithPattern(s, startQM, endQM, pattern);

    }

    /**
     * Fill some part of a char array with a repeating pattern.
     *
     * @param s     the char array to update
     * @param start the start offset (included)
     * @param end   the end offset (included)
     * @param r     the pattern that will be repeated
     * @return s
     */
    private static char[] fillWithPattern(char[] s, int start, int end, char[] r) {
        int nextR = 0;
        for (int i = start; i <= end; ++i) {
            s[i] = r[nextR];
            nextR = (nextR + 1) % r.length;
        }

        return s;
    }

    protected static int calculateCost(int x, int y, String s) {
        char[] c = new char[s.length()];
        s.getChars(0, s.length(), c, 0);
        return calculateCost(x, y, c);
    }

    protected static int calculateCost(int x, int y, char[] s) {
        int totalCost = 0;

        for (int i = 0; i < s.length - 1; ++i) {
            String nextTwo = "" + s[i] + s[i + 1];
            switch (nextTwo) {
                case "CJ":
                    totalCost += x;
                    break;
                case "JC":
                    totalCost += y;
                    break;
            }
        }

        return totalCost;
    }
}
