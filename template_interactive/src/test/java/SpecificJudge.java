import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

public class SpecificJudge implements Runnable {

    private final List<List<Integer>> world;
    private final int maxActions;

    private final Scanner scanner;
    private final PrintStream out;

    private boolean success = true;

    public SpecificJudge(List<List<Integer>> world, int maxActions, Scanner scanner, PrintStream out) {
        this.world = world;
        this.maxActions = maxActions;
        this.scanner = scanner;
        this.out = out;
    }

    @Override
    public void run() {

        System.out.println("Run starting");

        try {

            // Single test case
            out.println(1);

            // Send first line: N ; K
            var N = world.size() - 1;
            out.println(N + " " + maxActions);
            out.flush();

            int actions = maxActions;

            boolean completed = false;
            while (!completed) {

                // Action
                var line = scanner.nextLine();
                var parts = Arrays.asList(line.split(" ")).stream().collect(Collectors.toCollection(() -> new LinkedList<>()));

                var command = parts.poll();
                if (command == null) {
                    panic("No command provided");
                }

                switch (command) {
                    case "X": // TODO Action
                        if (--actions < 0) {
                            panic("Did too many actions");
                        }

                        break;

                    case "Y": // TODO Final answer
                        completed = true;
                        success = true;
                        break;
                    default:
                        panic("Unknown command");
                }

                out.flush();
            }

        } catch (Exception e) {
            success = false;
            throw e;
        }


        System.out.println("Run completed");

    }

    private void panic(String errorMessage) {
        // Problem
        out.println("-1");
        throw new RuntimeException(errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

}
