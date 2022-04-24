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

        // N, the number of pancakes in the pancake deque
        var N = sc.nextInt();

        // D1,D2,DN, where Di is the deliciousness level of the i-th pancake from the left in the deque
        var deque = new LinkedList<Integer>();
        for (int i=0; i<N;++i) {
            deque.add(sc.nextInt());
        }

        // Process
        int paid = 0;
        int last = 0;
        while (!deque.isEmpty()) {
            int left = deque.peekFirst();
            int right = deque.peekLast();

            int side =  right-left;
            int picked;
            if (side <0) {
                // Pick right
                picked = right;
                deque.removeLast();
            } else {
                // Pick left
                picked = left;
                deque.removeFirst();
            }

            if (picked >= last) {
                ++paid;
                last = picked;
            }
        }

        out.println("Case #" + caseNumber + ": " + paid);
    }

}
