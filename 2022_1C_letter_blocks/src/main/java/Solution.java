import jdk.jshell.spi.ExecutionControl;

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

    public static void processNext(int caseNumber, Scanner sc, PrintStream out) {

        // N, the number of towers that are currently built
        var N = sc.nextInt();

        var towers = new LinkedList<String>();
        sc.nextLine();
        var line = sc.nextLine();
        towers.addAll(Arrays.asList(line.split(" ")));

        String result = "IMPOSSIBLE";
        try {
            // Quick discard (if any not valid)
            towers.forEach(tower -> {
                if (!isValid(tower)) {
                    throw new RuntimeException("impossible");
                }
            });

            result = findTower("", towers);
        } catch (Exception e) {
        }

        out.println("Case #" + caseNumber + ": " + result);
    }

    private static String findTower(String megaTower, LinkedList<String> towers) {

        if (towers.isEmpty()) {
            return megaTower;
        }

        // Quick discard (if a next tower uses a letter already used [but the last on the mega as the start of the next], nothing is possible)
        Map<Character, Boolean> usedLetters = new HashMap<>();
        for (int i = 0; i < megaTower.length(); ++i) {
            char n = megaTower.charAt(i);
            usedLetters.put(n, true);
        }
        var megaLastChar = ' ';
        if (!megaTower.isEmpty()) {
            megaLastChar = megaTower.charAt(megaTower.length() - 1);
        }
        for (int i = 0; i < towers.size(); ++i) {
            var c = megaLastChar;
            var tower = towers.get(i);

            for (int y = 0; y < tower.length(); ++y) {
                char n = tower.charAt(y);
                if (n == c) {
                    continue;
                }

                var wasUsed = usedLetters.get(n);
                if (wasUsed != null) {
                    throw new RuntimeException("impossible");
                }
                c = n;
            }
        }

        // Test all
        for (int i = 0; i < towers.size(); ++i) {

            // Take
            var tower = towers.get(i);

            // Try
            var nextMegaTower = megaTower + tower;
            if (isValid(nextMegaTower)) {
                try {
                    towers.remove(i);
                    return findTower(nextMegaTower, towers);
                } catch (Exception e) {
                }

                // Put back
                towers.add(i, tower);
            }

        }

        // If none found, impossible
        throw new RuntimeException("impossible");
    }

    private static boolean isValid(String tower) {
        Map<Character, Boolean> usedLetters = new HashMap<>();
        char c = ' ';
        for (int i = 0; i < tower.length(); ++i) {
            char n = tower.charAt(i);
            if (n == c) {
                continue;
            }

            var wasUsed = usedLetters.put(n, true);
            if (wasUsed != null) {
                return false;
            }
            c = n;
        }

        return true;
    }

}
