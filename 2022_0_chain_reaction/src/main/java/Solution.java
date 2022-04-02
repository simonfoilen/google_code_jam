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

    private static int F[];
    private static int cachedMaxFun[];
    private static Queue<Integer> fromAbyss;
    private static LinkedList<Integer>[] childrens;
    private static int depth = 0;

    public static void processNext(int caseNumber, Scanner inputScanner, PrintStream out) {

        // N: Number of modules
        var N = inputScanner.nextInt();

        // F1, F2, ... FN: Fun of each modules
        F = new int[N];
        for (int i = 0; i < N; ++i) {
            F[i] = inputScanner.nextInt();
        }

        // P1, P2, ... PN: Pointer to module or 0
        fromAbyss = new LinkedList<>();
        childrens = new LinkedList[N];
        cachedMaxFun = new int[N];
        for (int i = 0; i < N; ++i) {
            childrens[i] = new LinkedList<>();
            var pointTo = inputScanner.nextInt();
            if (pointTo == 0) {
                fromAbyss.add(i);
            } else {
                childrens[pointTo - 1].add(i);
            }
            cachedMaxFun[i] = -1;
        }

        long totalFun = 0;
        Integer nodeFromAbyss;
        while ((nodeFromAbyss = fromAbyss.poll()) != null) {
            totalFun += processNode(nodeFromAbyss);
        }
        out.println("Case #" + caseNumber + ": " + totalFun);

        // Internals
        for (int i = 0; i < N; ++i) {
            System.err.println("Cached max for " + (i + 1) + " : " + cachedMaxFun[i]);
        }
    }

    private static int processNode(Integer nodeFromAbyss) {
        ++depth;
        System.err.println(depth() + "IN " + (nodeFromAbyss+1));

        try {
            var nodeChildrens = childrens[nodeFromAbyss];

            // Get the max of each child branch
            int smallestMax = Integer.MAX_VALUE;
            int smallestMaxIndex = -1;
            for (var nodeChild : nodeChildrens) {

                int childMax = findMax(nodeChild);
                if (childMax < smallestMax) {
                    // Switch
                    if (smallestMaxIndex != -1) {
                        fromAbyss.add(smallestMaxIndex);
                    }
                    smallestMaxIndex = nodeChild;
                    smallestMax = childMax;
                } else {
                    // Put in Abyss
                    fromAbyss.add(nodeChild);
                }
            }

            // The one with the smallest max is triggered (fun += max(f,childMax))
            if (smallestMaxIndex == -1) {
                return F[nodeFromAbyss];
            } else {
                return Math.max(F[nodeFromAbyss], processNode(smallestMaxIndex));
            }

        }finally {
            System.err.println(depth() + "OUT " + (nodeFromAbyss+1));
            --depth;
        }
    }

    private static String depth() {
        var result = "";
        for (int i = 0; i < depth; ++i) {
            result += " ";
        }
        return result;
    }

    private static int findMax(int nodeChild) {

        if (cachedMaxFun[nodeChild] != -1) {
            return cachedMaxFun[nodeChild];
        }

        var max = F[nodeChild];
        for (var child : childrens[nodeChild]) {
            var childMax = findMax(child);
            if (childMax > max) {
                max = childMax;
            }
        }

        cachedMaxFun[nodeChild] = max;
        return max;
    }

}
