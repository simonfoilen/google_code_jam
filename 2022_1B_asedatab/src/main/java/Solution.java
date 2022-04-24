import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        new Solution(System.in, System.out).execute();
    }

    private final Scanner sc;
    private final PrintStream out;

    public Solution(InputStream in, PrintStream out) {
        this.sc = new Scanner(in);
        this.out = out;
    }

    public void execute() {
        // 1: the number of test cases (T)
        int t = sc.nextInt();

        try {
            for (int i = 1; i <= t; ++i) {
                processNext(i);
            }
        } catch (NoSuchElementException e) {
            // End
        }
        out.flush();

    }

    public void processNext(int caseNumber) {

        boolean[] value = new boolean[8];

        int ones = send(value);
        int pOnes = 0;
        while (ones != 0) {

            var delta = ones - pOnes;
            if (delta == 0) {
                // Reset
                value = new boolean[8];
                delta = ones;
            }

            // Random
            while (delta > 0) {
                int pos = (int) (Math.random() * 8);
                if (!value[pos]) {
                    value[pos] = true;
                    --delta;
                }
            }
            while (delta < 0) {
                int pos = (int) (Math.random() * 8);
                if (value[pos]) {
                    value[pos] = false;
                    ++delta;
                }
            }

            pOnes = ones;
            ones = send(value);
        }
    }

    private int receive() {
        int r = sc.nextInt();
        if (r == -1) {
            throw new RuntimeException("Judge said no");
        }

        return r;
    }

    private int send(boolean[] value) {
        for (var v : value) {
            out.print(v ? '1' : '0');
        }
        out.println();
        out.flush();

        return receive();
    }

}
