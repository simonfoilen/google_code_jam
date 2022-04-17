import com.foilen.smalltools.tools.ExecutorsTools;
import com.foilen.smalltools.tools.StreamsTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.*;

class SolutionTest {

    private void executeTest(List<List<Integer>> solutions) {
        executeTest(solutions, 1000);
    }

    private void executeTest(List<List<Integer>> solutions, int maxQuestions) {
        // Create the pipes
        var appToJudge = StreamsTools.createPipe();
        var judgeToApp = StreamsTools.createPipe();

        // Prepare repeaters to show the discussion on the console
        var repeaterAppToJudgeIn = new RepeaterInputStream(appToJudge.getA());
        repeaterAppToJudgeIn.add(new PrefixLineOutputStream("A2J> ", System.out));

        var repeaterJudgeToAppOut = new RepeaterOutputStream();
        repeaterJudgeToAppOut.add(judgeToApp.getB());
        repeaterJudgeToAppOut.add(new PrefixLineOutputStream("J2A> ", System.out));

        // Start the solution
        ExecutorsTools.getCachedDaemonThreadPool().submit(() -> {
            try {
                new Solution(judgeToApp.getA(), new PrintStream(appToJudge.getB())).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Start the judge
        var sortingJudge = new SortingJudge(solutions, maxQuestions, new Scanner(repeaterAppToJudgeIn), new PrintStream(repeaterJudgeToAppOut));
        sortingJudge.run();

        // Assert Judge
        Assertions.assertTrue(sortingJudge.isSuccess());
    }

    @Test
    void testSample_1() throws Exception {
        executeTest(Collections.singletonList(Arrays.asList(4, 2, 1, 3, 5)));
    }

    @Test
    void testSample_2() throws Exception {
        executeTest(Collections.singletonList(Arrays.asList(5, 4, 3, 2, 1)));
    }

    @Test
    void testSample_3() throws Exception {
        executeTest(Collections.singletonList(Arrays.asList(1, 3, 5, 4, 2)));
    }

    @Test
    void test5_1() throws Exception {
        executeTest(Collections.singletonList(Arrays.asList(4, 1, 5, 2, 3)));
    }

    @Test
    void test10_1() throws Exception {
        executeTest(Collections.singletonList(Arrays.asList(6, 8, 10, 5, 2, 1, 4, 3, 9, 7)));
    }

    @Test
    void testRandom_5_50() throws Exception {
        var solutions = new ArrayList<List<Integer>>();
        for (int i = 0; i < 50; ++i) {
            var solution = new ArrayList<Integer>();
            for (int j = 1; j <= 5; ++j) {
                solution.add(j);
            }
            Collections.shuffle(solution);
            solutions.add(solution);
        }

        executeTest(solutions);
    }

    @Test
    void testRandom_10_50() throws Exception {
        var solutions = new ArrayList<List<Integer>>();
        for (int i = 0; i < 50; ++i) {
            var solution = new ArrayList<Integer>();
            for (int j = 1; j <= 10; ++j) {
                solution.add(j);
            }
            Collections.shuffle(solution);
            solutions.add(solution);
        }

        executeTest(solutions);
    }

    @Test
    void testRandom_100_50_300() throws Exception {
        var solutions = new ArrayList<List<Integer>>();
        for (int i = 0; i < 100; ++i) {
            var solution = new ArrayList<Integer>();
            for (int j = 1; j <= 50; ++j) {
                solution.add(j);
            }
            Collections.shuffle(solution);
            solutions.add(solution);
        }

        executeTest(solutions, 300 * 100);
    }

    @Test
    void testRandom_100_50_170() throws Exception {
        var solutions = new ArrayList<List<Integer>>();
        for (int i = 0; i < 100; ++i) {
            var solution = new ArrayList<Integer>();
            for (int j = 1; j <= 50; ++j) {
                solution.add(j);
            }
            Collections.shuffle(solution);
            solutions.add(solution);
        }

        executeTest(solutions, 170 * 100);
    }

}