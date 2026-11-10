import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;

public class Task2 {

    static class Song {
        int id;
        String title;

        Song(int id, String title) {
            this.id = id;
            this.title = title;
        }

        @Override
        public String toString() {
            return id + " - " + title;
        }
    }

    static ArrayList<Song> createArrayList(int size) {
        ArrayList<Song> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            list.add(new Song(i, "Song " + i));
        }

        return list;
    }

    static LinkedList<Song> createLinkedList(int size) {
        LinkedList<Song> list = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            list.add(new Song(i, "Song " + i));
        }

        return list;
    }

    static void measureArrayList(int size) {

        ArrayList<Song> list = createArrayList(size);
        int operations = Math.max(100, 1000000 / size);
        int middle = size / 2;

        System.out.println("\nArrayList - " + size + " songs");

        // Add at end and remove from end
        long start = System.nanoTime();

        for (int i = 0; i < operations; i++) {
            list.add(new Song(-1, "New Song"));
            list.remove(list.size() - 1);
        }

        printTime("Add/remove at end", start);

        // Insert and remove at front
        start = System.nanoTime();

        for (int i = 0; i < operations; i++) {
            list.add(0, new Song(-1, "Front Song"));
            list.remove(0);
        }

        printTime("Insert/remove front", start);

        // Insert and remove in middle
        start = System.nanoTime();

        for (int i = 0; i < operations; i++) {
            list.add(middle, new Song(-1, "Middle Song"));
            list.remove(middle);
        }

        printTime("Insert/remove middle", start);

        // Access k-th element
        start = System.nanoTime();

        long checksum = 0;

        for (int i = 0; i < operations; i++) {
            checksum += list.get(middle).id;
        }

        printTime("Access k-th song", start);

        // Iterate through complete list
        start = System.nanoTime();

        for (int i = 0; i < 10; i++) {
            for (Song song : list) {
                checksum += song.id;
            }
        }

        printTime("Full iteration (10 times)", start);

        // Prevent unused result elimination
        System.out.println("Checksum: " + checksum);
    }

    static void measureLinkedList(int size) {

        LinkedList<Song> list = createLinkedList(size);
        int operations = Math.max(100, 1000000 / size);
        int middle = size / 2;

        System.out.println("\nLinkedList - " + size + " songs");

        // Add at end and remove from end
        long start = System.nanoTime();

        for (int i = 0; i < operations; i++) {
            list.add(new Song(-1, "New Song"));
            list.removeLast();
        }

        printTime("Add/remove at end", start);

        // Insert and remove at front
        start = System.nanoTime();

        for (int i = 0; i < operations; i++) {
            list.addFirst(new Song(-1, "Front Song"));
            list.removeFirst();
        }

        printTime("Insert/remove front", start);

        // Insert and remove in middle by index
        start = System.nanoTime();

        for (int i = 0; i < operations; i++) {
            list.add(middle, new Song(-1, "Middle Song"));
            list.remove(middle);
        }

        printTime("Insert/remove middle", start);

        // Access k-th element
        start = System.nanoTime();

        long checksum = 0;

        for (int i = 0; i < operations; i++) {
            checksum += list.get(middle).id;
        }

        printTime("Access k-th song", start);

        // Iterate through complete list
        start = System.nanoTime();

        for (int i = 0; i < 10; i++) {
            for (Song song : list) {
                checksum += song.id;
            }
        }

        printTime("Full iteration (10 times)", start);

        System.out.println("Checksum: " + checksum);
    }

    static void printTime(String operation, long start) {
        long end = System.nanoTime();

        System.out.printf(
            "%-30s %.3f ms%n",
            operation,
            (end - start) / 1_000_000.0
        );
    }

    // Demonstrate ArrayDeque for an Up Next queue
    static void demonstrateArrayDeque() {

        ArrayDeque<Song> upNext = new ArrayDeque<>();

        upNext.addLast(new Song(1, "Song A"));
        upNext.addLast(new Song(2, "Song B"));
        upNext.addFirst(new Song(3, "Urgent Song"));

        System.out.println("\nArrayDeque - Up Next Queue");

        System.out.println("Next song: " + upNext.peekFirst());

        System.out.println("Playing: " + upNext.removeFirst());

        upNext.addLast(new Song(4, "Song D"));

        System.out.println("Remaining songs:");

        for (Song song : upNext) {
            System.out.println(song);
        }
    }

    public static void main(String[] args) {

        int[] sizes = {100, 10000, 100000};

        for (int size : sizes) {

            System.out.println("\n================================");
            System.out.println("PLAYLIST SIZE: " + size);
            System.out.println("================================");

            measureArrayList(size);
            measureLinkedList(size);
        }

        demonstrateArrayDeque();
    }
}