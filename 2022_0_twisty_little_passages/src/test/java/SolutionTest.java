import com.foilen.smalltools.tools.ExecutorsTools;
import com.foilen.smalltools.tools.StreamsTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

class SolutionTest {

    private void executeTest(List<List<Integer>> roomsAndPassages, int maxQuestions) {
        executeTest(roomsAndPassages, maxQuestions, false);
    }

    private void executeTest(List<List<Integer>> roomsAndPassages, int maxQuestions, boolean quiet) {

        if (!quiet) {
            System.err.println("--[ Cavern ]--");
            for (int i = 1; i < roomsAndPassages.size(); ++i) {
                var p = roomsAndPassages.get(i);
                System.err.println("Room " + i + " => " + p.stream().map(it -> it + " ").collect(Collectors.joining()));
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
        ExecutorsTools.getCachedDaemonThreadPool().submit(() -> {
            try {
                new Solution(judgeToApp.getA(), new PrintStream(appToJudge.getB())).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Start the judge
        var judge = new CavernJudge(roomsAndPassages, maxQuestions, new Scanner(repeaterAppToJudgeIn), new PrintStream(repeaterJudgeToAppOut));
        judge.run();

        // Assert Judge
        Assertions.assertTrue(judge.isSuccess());
    }

    private void executeCircle(int rooms, int maxQuestions) {
        List<List<Integer>> roomsAndPassages = new ArrayList<>();
        roomsAndPassages.add(Collections.emptyList()); // 0

        for (int i = 1; i <= rooms; ++i) {
            int previous = i - 1;
            int next = i + 1;
            if (previous == 0) {
                previous = rooms;
            }
            if (next > rooms) {
                next = 1;
            }
            roomsAndPassages.add(Arrays.asList(previous, next));
        }
        executeTest(roomsAndPassages, maxQuestions);
    }

    private void executeRandom(int rooms, int maxQuestions) {

        var extraPassages = ((int) (Math.random() * rooms * 10));

        List<Set<Integer>> roomsAndPassages = new ArrayList<>();
        roomsAndPassages.add(Collections.emptySet()); // 0

        // Initialize
        for (int i = 1; i <= rooms; ++i) {
            roomsAndPassages.add(new HashSet<>());
        }

        // Connect each to at least 1 that is lower
        for (int i = 1; i <= rooms; ++i) {
            var connectTo = ((int) (Math.random() * (i - 1))) + 1;
            connect(roomsAndPassages, connectTo, i);
        }

        // Add extra passages
        for (int i = 0; i < extraPassages; ++i) {
            var connectFrom = ((int) (Math.random() * rooms)) + 1;
            var connectTo = ((int) (Math.random() * rooms)) + 1;
            if (connectFrom != connectTo) {
                connect(roomsAndPassages, connectFrom, connectTo);
            }
        }

        // Transform and execute
        List<List<Integer>> roomsAndPassagesLists = roomsAndPassages.stream()
                .map(it -> it.stream().collect(Collectors.toList()))
                .collect(Collectors.toList());
        executeTest(roomsAndPassagesLists, maxQuestions, true);
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

    @Test
    void testSample_5_3() throws Exception {
        executeTest(Arrays.asList(
                Arrays.asList(), // 0
                Arrays.asList(2, 3, 5), // 1
                Arrays.asList(1, 3), // 2
                Arrays.asList(1, 2), // 3
                Arrays.asList(5), // 4
                Arrays.asList(1, 4) // 5
        ), 3);
    }

    @Test
    void testSample_4_100() throws Exception {
        executeTest(Arrays.asList(
                Arrays.asList(), // 0
                Arrays.asList(2, 3, 5), // 1
                Arrays.asList(1), // 2
                Arrays.asList(1), // 3
                Arrays.asList(5), // 4
                Arrays.asList(1, 4) // 5
        ), 100);
    }

    @Test
    void testSample_5_100() throws Exception {
        executeTest(Arrays.asList(
                Arrays.asList(), // 0
                Arrays.asList(2, 3, 5), // 1
                Arrays.asList(1, 3), // 2
                Arrays.asList(1, 2), // 3
                Arrays.asList(5), // 4
                Arrays.asList(1, 4) // 5
        ), 100);
    }

    @Test
    void testSample_disconnectCaves_10_3() throws Exception {
        executeTest(Arrays.asList(
                Arrays.asList(), // 0
                Arrays.asList(2), // 1
                Arrays.asList(1), // 2
                Arrays.asList(4), // 3
                Arrays.asList(3), // 4
                Arrays.asList(6), // 5
                Arrays.asList(5), // 6
                Arrays.asList(8), // 7
                Arrays.asList(7), // 8
                Arrays.asList(10), // 9
                Arrays.asList(9) // 10
        ), 3);
    }

    @Test
    void testSample_disconnectCaves_10_100() throws Exception {
        executeTest(Arrays.asList(
                Arrays.asList(), // 0
                Arrays.asList(2), // 1
                Arrays.asList(1), // 2
                Arrays.asList(4), // 3
                Arrays.asList(3), // 4
                Arrays.asList(6), // 5
                Arrays.asList(5), // 6
                Arrays.asList(8), // 7
                Arrays.asList(7), // 8
                Arrays.asList(10), // 9
                Arrays.asList(9) // 10
        ), 100);
    }

    @Test
    void testCircle_10_10() throws Exception {
        executeCircle(10, 10);
    }

    @Test
    void testCircle_1000_1000() throws Exception {
        executeCircle(1000, 1000);
    }

    @Test
    void testRandom_10_5() throws Exception {
        executeRandom(10,  5);
    }

    @Test
    void testRandom_10_100() throws Exception {
        executeRandom(10,  100);
    }

    @Test
    void testRandom_100000_8000() throws Exception {
        executeRandom(100000,  8000);
    }

    private void connect(List<Set<Integer>> roomsAndPassages, int one, int two) {
        roomsAndPassages.get(one).add(two);
        roomsAndPassages.get(two).add(one);
    }

}