import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        new Solution(System.in, System.out).execute();
    }

    private Scanner sc;
    private PrintStream out;

    public Solution(InputStream in, PrintStream out) {
        this.sc = new Scanner(in);
        this.out = out;
    }

    public void execute() {
        // 1: the number of test cases (T)
        int t = sc.nextInt();

        // 2: the number of elements in the input list (N)
        int n = sc.nextInt();

        // 3: the number of questions in total we can send (Q)
        int q = sc.nextInt();
        int qPerTest = q / t;

        try {
            for (int i = 1; i <= t; ++i) {
                processNext(n, qPerTest);
            }
        } catch (NoSuchElementException e) {
            // End
        }
        out.flush();

    }

    public void processNext(int n, int q) {

        // Send the first question
        var solution = new LinkedList<Integer>();
        int median = sendQ(1, 2, 3);
        List<Integer> items = new ArrayList<>(Arrays.asList(1, 2, 3));
        items.remove((Integer) median);

        solution.add(median);
        solution.addFirst(items.remove(0));
        solution.add(items.remove(0));

        // Send the next questions
        for (int i = 4; i <= n; ++i) {
            insertNext(solution, i, 0, solution.size() - 1, true);
        }

        // Send answer
        sendA(solution);

    }

    private void insertNext(LinkedList<Integer> solution, int nextItem, int wallLeftIdx, int wallRightIdx, boolean sure) {

        // At position in between
        if (wallRightIdx - wallLeftIdx == 1) {
            if (sure) {
                solution.add(wallRightIdx, nextItem);
            } else {
                var wallLeft = solution.get(wallLeftIdx);
                var wallRight = solution.get(wallRightIdx);
                var median = sendQ(wallLeft, wallRight, nextItem);


                if (median == wallLeft) { // Before wall left
                    solution.add(wallLeftIdx, nextItem);
                } else if (median == wallRight) {// After wall right
                    solution.add(nextItem);
                } else { // In the middle
                    solution.add(wallRightIdx, nextItem);
                }
            }
            return;
        }

        // Ask the judge for the left side of the middle
        var middleIdx = (wallRightIdx - wallLeftIdx) / 2 + wallLeftIdx;
        var wallLeft = solution.get(wallLeftIdx);
        var middle = solution.get(middleIdx);
        var median = sendQ(wallLeft, middle, nextItem);

        // It is on the left of the left side
        if (median == wallLeft) {
            solution.add(wallLeftIdx, nextItem);
            return;
        }

        // It is on the left side of the middle
        if (median == nextItem) {
            insertNext(solution, nextItem, wallLeftIdx, middleIdx, true);
            return;
        }

        // It is on the right side of the middle, not sure
        insertNext(solution, nextItem, middleIdx, wallRightIdx, false);

    }

    private int sendQ(int i, int j, int k) {
        out.println(i + " " + j + " " + k);
        out.flush();

        int r = sc.nextInt();
        if (r == -1) {
            throw new RuntimeException("Judge said no");
        }
        return r;
    }

    private void sendA(LinkedList<Integer> solution) {
        boolean first = true;
        for (Integer next : solution) {
            if (first) {
                first = false;
            } else {
                out.print(" ");
            }
            out.print(next);
        }
        out.println();

        out.flush();

        int r = sc.nextInt();
        if (r == -1) {
            throw new RuntimeException("Judge said no");
        }
    }
}
