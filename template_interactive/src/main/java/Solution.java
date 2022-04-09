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

        // xxxx: XXXXX
        // TODO var xxxx = sc.nextInt();

    }

    private int[] receive() {
        var result = new int[2];

        for (int i = 0; i < 2; ++i) {
            int r = sc.nextInt();
            if (r == -1) {
                throw new RuntimeException("Judge said no");
            }
            result[i] = r;
        }

        return result;
    }

    private int[] send() {
        out.println("W");
        out.flush();

        return receive();
    }

}
