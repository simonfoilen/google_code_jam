import com.foilen.smalltools.tools.ExecutorsTools;
import com.foilen.smalltools.tools.StreamsTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

class SolutionTest {

    private void executeTest(List<List<Integer>> world, int maxQuestions) {
        executeTest(world, maxQuestions, false);
    }

    private void executeTest(List<List<Integer>> world, int maxQuestions, boolean quiet) {

        if (!quiet) {
            System.err.println("--[ World ]--");
            for (int i = 1; i < world.size(); ++i) {
                var p = world.get(i);
                System.err.println("Part " + i + " => " + p.stream().map(it -> it + " ").collect(Collectors.joining()));
            }
            System.err.println("--------------");
        }

        // Create the pipes
        var appToJudge = StreamsTools.createPipe();
        var judgeToApp = StreamsTools.createPipe();

        // Prepare repeaters to show the discussion on the console
        var repeaterAppToJudgeIn = new RepeaterInputStream(appToJudge.getA());
        if (!quiet) {
            repeaterAppToJudgeIn.add(new PrefixLineOutputStream("A2J> ", System.out));
        }

        var repeaterJudgeToAppOut = new RepeaterOutputStream();
        repeaterJudgeToAppOut.add(judgeToApp.getB());
        if (!quiet) {
            repeaterJudgeToAppOut.add(new PrefixLineOutputStream("J2A> ", System.out));
        }

        // Start the solution
        ExecutorsTools.getCachedDaemonThreadPool().submit(() -> new Solution(judgeToApp.getA(), new PrintStream(appToJudge.getB())).execute());

        // Start the judge
        var judge = new SpecificJudge(world, maxQuestions, new Scanner(repeaterAppToJudgeIn), new PrintStream(repeaterJudgeToAppOut));
        judge.run();

        // Assert Judge
        Assertions.assertTrue(judge.isSuccess());
    }

    @Test
    void testSample_4_3() throws Exception {
        executeTest(Arrays.asList(
                Arrays.asList(), // 0
                Arrays.asList(2, 3, 5), // 1
                Arrays.asList(1), // 2
                Arrays.asList(1), // 3
                Arrays.asList(5), // 4
                Arrays.asList(1, 4) // 5
        ), 3);
    }
    
}