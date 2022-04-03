import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class Solution {

    private static final boolean SHOW_INTERNALS_EACH_ACTION = false;
    private static final boolean SHOW_INTERNALS_END = false;

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

        try {
            for (int i = 1; i <= t; ++i) {
                processNext(i);
            }
        } catch (NoSuchElementException e) {
            // End
        }
        out.flush();

    }

    static class Room {
        int id;
        int amountOFPassages = -1;
        Set<Integer> knownPassages = new HashSet<>();

        Room(int id) {
            this.id = id;
        }

        int unknownPassages() {
            return amountOFPassages - knownPassages.size();
        }
    }

    private List<Room> rooms;
    private Queue<Room> visitedRoomsWithUnknowns;
    private LinkedList<Room> neverVisitedRoomsQueue;
    Room currentRoom;

    public void processNext(int caseNumber) {

        // N: number of rooms in the cave
        var N = sc.nextInt();
        rooms = new ArrayList<>(N + 1);
        neverVisitedRoomsQueue = new LinkedList<>();
        for (int i = 0; i <= N; ++i) {
            Room room = new Room(i);
            rooms.add(room);
            if (i > 0) {
                neverVisitedRoomsQueue.add(room);
            }
        }
        rooms.get(0).amountOFPassages = 0;
        visitedRoomsWithUnknowns = new LinkedList<>();
        Collections.shuffle(neverVisitedRoomsQueue);


        // K: maximum number of room operations you are allowed
        var K = sc.nextInt();

        // Get the initial room
        {
            var roomAndPassages = getRoomAndPassages();
            update(roomAndPassages);
            currentRoom = rooms.get(roomAndPassages[0]);
        }

        while (K > 0) {
            --K;

            if (SHOW_INTERNALS_EACH_ACTION) {
                showInternal();
            }

            // Stop if all visited
            var allVisited = !rooms.stream().anyMatch(r -> r.amountOFPassages == -1 || r.unknownPassages() > 0);
            if (allVisited) {
                break;
            }

            // Choose what to do
            if (currentRoom.unknownPassages() > 0) {
                walk();
            } else {
                teleportRandomNever();
            }

        }

        if (SHOW_INTERNALS_END) {
            showInternal();
        }

        // Send answer
        var exactLinks = 0;
        var unknownLinks = 0;
        var neverVisitedRooms = 0;
        for (var room : rooms) {
            if (room.amountOFPassages == -1) {
                ++neverVisitedRooms;
            } else {
                exactLinks += room.knownPassages.size();
                unknownLinks += room.unknownPassages();
            }
        }

        var exactPassages = exactLinks / 2;
        var unknownPassages = unknownLinks / 2;
        double avgPassagesByRoom = ((double) (exactPassages + unknownPassages)) / (N - neverVisitedRooms);
        if (avgPassagesByRoom == 0) {
            avgPassagesByRoom = 0.5;
        }
        var minConnectedPassages = N - 1;
        int minDisonnectedPassages = (N + 1) / 2;
        var maxPassages = 0;
        for (int i = 1; i <= N; ++i) {
            maxPassages += i;
        }

        System.err.println("---");
        System.err.println("N " + N + " neverVisitedRooms " + neverVisitedRooms);
        System.err.println("exactPassages " + exactPassages + " unknownPassages " + unknownPassages + " avgPassagesByRoom " + avgPassagesByRoom);
        System.err.println("minDisonnectedPassages " + minDisonnectedPassages + " minConnectedPassages " + minConnectedPassages + " maxPassages " + maxPassages);
        long totalPassages = exactPassages + unknownPassages + ((long) (avgPassagesByRoom * neverVisitedRooms));
        sendEstimate(totalPassages);

    }

    private void showInternal() {
        for (var room : rooms) {
            if (room.id == 0) continue;
            System.err.println("Room " + room.id + " (" + room.amountOFPassages + ") -> " + join(room.knownPassages) + " [" + room.unknownPassages() + "]");
        }
    }

    private void walk() {
        var roomAndPassages = sendWalkRandom();
        update(roomAndPassages);
        var previousRoom = currentRoom;
        currentRoom = rooms.get(roomAndPassages[0]);

        // Add a passage between them
        previousRoom.knownPassages.add(currentRoom.id);
        currentRoom.knownPassages.add(previousRoom.id);

    }

    private void teleportVisitedWithMostUnknowns() {
        // Choose room with the most unknown passages
        var teleportToRoomOptional = rooms.stream()
                .filter(r -> r.id != 0)
                .filter(r -> r.amountOFPassages != -1)
                .sorted((a, b) -> {
                    var aUnknowns = a.unknownPassages();
                    var bUnknowns = b.unknownPassages();
                    return bUnknowns - aUnknowns;
                })
                .findFirst();
        if (teleportToRoomOptional.isEmpty()) {
            throw new RuntimeException("Nowhere to teleport");
        }

        var roomAndPassages = sendTeleport(teleportToRoomOptional.get().id);
        update(roomAndPassages);
        currentRoom = rooms.get(roomAndPassages[0]);
    }

    private void teleportVisitedWithUnknowns() {
        Room room;
        while ((room = visitedRoomsWithUnknowns.poll()) != null) {
            if (room.unknownPassages() != 0) {
                break;
            }
        }

        if (room == null) {
            teleportRandomNever();
            return;
        }

        var roomAndPassages = sendTeleport(room.id);
        update(roomAndPassages);
        currentRoom = rooms.get(roomAndPassages[0]);
    }

    private void teleportRandomNever() {

        Room nextRoom;
        while ((nextRoom = neverVisitedRoomsQueue.poll()) != null) {
            if (nextRoom.amountOFPassages == -1) {
                break;
            }
        }
        if (nextRoom == null) {
            walk();
            return;
        }

        var roomAndPassages = sendTeleport(nextRoom.id);
        update(roomAndPassages);
        currentRoom = rooms.get(roomAndPassages[0]);
    }

    private void update(int[] roomAndPassages) {
        var room = rooms.get(roomAndPassages[0]);
        room.amountOFPassages = roomAndPassages[1];

        if (room.unknownPassages() > 0) {
            visitedRoomsWithUnknowns.add(room);
        }
    }

    private int[] getRoomAndPassages() {
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

    private int[] sendWalkRandom() {
        out.println("W");
        out.flush();

        return getRoomAndPassages();
    }

    private int[] sendTeleport(int toRoom) {
        out.println("T " + toRoom);
        out.flush();

        return getRoomAndPassages();
    }

    private void sendEstimate(long passages) {
        out.println("E " + passages);
        out.flush();
    }

    private String join(Collection<?> items) {
        String result = "";
        for (var item : items) {
            result += item + ", ";
        }
        return result;
    }
}
