import com.foilen.smalltools.tuple.Tuple2;
import com.google.common.base.Joiner;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

public class SortingJudge implements Runnable {

    private List<List<Integer>> solutions;
    private int maxQuestions;

    private Scanner scanner;
    private PrintStream out;

    private boolean success;

    public SortingJudge(List<List<Integer>> solutions, int maxQuestions, Scanner scanner, PrintStream out) {
        this.solutions = solutions;
        this.maxQuestions = maxQuestions;
        this.scanner = scanner;
        this.out = out;
    }

    @Override
    public void run() {

        System.out.println("Run starting");

        // Send first line
        int itemsPerSolution = solutions.get(0).size();
        out.println(solutions.size() + " " + itemsPerSolution + " " + maxQuestions);
        out.flush();

        for (var solution : solutions) {
            System.out.println("Next test case: " + Joiner.on(", ").join(solution));

            var solutionReversed = new ArrayList<>(solution);
            Collections.reverse(solutionReversed);

            int questionsAskedInRound = 0;

            try {
                boolean completed = false;
                while (!completed) {
                    var line = scanner.nextLine();
                    System.out.println("<< " + line);
                    var parts = Arrays.asList(line.split(" ")).stream()
                            .map(it -> Integer.valueOf(it))
                            .collect(Collectors.toList());

                    if (parts.size() == 3) {

                        ++questionsAskedInRound;
                        if (maxQuestions-- < 0) {
                            out.println("-1");
                            System.out.println(">> -1");
                            throw new RuntimeException("Out of allowed questions");
                        }

                        // Tell the median
                        var indexesAndValues = new ArrayList<Tuple2<Integer, Integer>>();
                        indexesAndValues.add(new Tuple2<>(solution.indexOf(parts.get(0)), parts.get(0)));
                        indexesAndValues.add(new Tuple2<>(solution.indexOf(parts.get(1)), parts.get(1)));
                        indexesAndValues.add(new Tuple2<>(solution.indexOf(parts.get(2)), parts.get(2)));
                        indexesAndValues.sort((a, b) -> a.getA().compareTo(b.getA()));

                        var median = indexesAndValues.get(1).getB();
                        out.println(median);
                        System.out.println(">> " + median);

                    } else if (parts.size() == solution.size()) {
                        // Check solutions both orders
                        if (parts.equals(solution) || parts.equals(solutionReversed)) {
                            out.println("1");
                            System.out.println(">> 1");
                            completed = true;
                        } else {
                            out.println("-1");
                            System.out.println(">> -1");
                            throw new RuntimeException("Wrong solution provided");
                        }

                    } else {
                        // Problem
                        out.println("-1");
                        System.out.println(">> -1");
                        throw new RuntimeException("Wrong request");
                    }

                    out.flush();
                }
            } finally {
                System.out.println("Asked " + questionsAskedInRound + " questions for this round");
            }
        }

        success = true;

        System.out.println("Run completed");

    }

    public boolean isSuccess() {
        return success;
    }

}
