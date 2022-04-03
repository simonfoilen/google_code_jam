import com.foilen.smalltools.tuple.Tuple2;

import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

public class CavernJudge implements Runnable {

    private List<List<Integer>> roomsAndPassages;
    private int maxActions;

    private Scanner scanner;
    private PrintStream out;

    private boolean success = true;

    public CavernJudge(List<List<Integer>> roomsAndPassages, int maxActions, Scanner scanner, PrintStream out) {
        this.roomsAndPassages = roomsAndPassages;
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
            var N = roomsAndPassages.size() - 1;
            out.println(N + " " + maxActions);
            out.flush();

            int actions = maxActions;

            boolean completed = false;
            var currentRoom = ((int) (Math.random() * N)) + 1;
            while (!completed) {

                // Show the current room
                showRoom(currentRoom);

                // Action
                var line = scanner.nextLine();
                var parts = Arrays.asList(line.split(" ")).stream().collect(Collectors.toCollection(() -> new LinkedList<>()));

                var command = parts.poll();
                if (command == null) {
                    panic("No command provided");
                }

                switch (command) {
                    case "T":
                        if (--actions < 0) {
                            panic("Did too many actions");
                        }

                        var TText = parts.poll();
                        if (TText == null) {
                            panic("No room provided");
                        }
                        var T = Integer.valueOf(TText);
                        currentRoom = T;
                        break;

                    case "W":
                        if (--actions < 0) {
                            panic("Did too many actions");
                        }

                        var connectedPassages = roomsAndPassages.get(currentRoom);
                        var passageIndex = ((int) (Math.random() * connectedPassages.size()));
                        currentRoom = connectedPassages.get(passageIndex);
                        break;

                    case "E":
                        completed = true;

                        var passages = roomsAndPassages.stream().collect(Collectors.summingInt(it -> it.size())) / 2;
                        var minP = passages * 2 / 3;
                        var maxP = passages * 4 / 3;
                        System.err.println("E must be between " + minP + " / " + passages + " / " + maxP);

                        var EText = parts.poll();
                        if (EText == null) {
                            panic("No estimate provided");
                        }
                        var E = Integer.valueOf(EText);
                        System.err.println("E is " + E);
                        if (E < minP || E > maxP) {
                            success = false;
                        }

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

    private void showRoom(int room) {
        var passages = roomsAndPassages.get(room).size();
        out.println(room + " " + passages);
        out.flush();
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
