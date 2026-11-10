import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;

class Patient {

    final String name;
    final int severity;
    final long arrival;

    Patient(String name, int severity, long arrival) {
        this.name = name;
        this.severity = severity;
        this.arrival = arrival;
    }

    @Override
    public String toString() {
        return name + "(S" + severity + ")";
    }
}

public class Triage {

    private long counter = 0;

    // Lowest severity number first.
    // For equal severity, earliest arrival first.
    private final PriorityQueue<Patient> queue =
        new PriorityQueue<>(
            Comparator.comparingInt((Patient p) -> p.severity)
                .thenComparingLong(p -> p.arrival)
        );

    void admit(String name, int severity) {

        if (severity < 1 || severity > 5) {
            throw new IllegalArgumentException(
                "Severity must be between 1 and 5"
            );
        }

        queue.add(new Patient(name, severity, counter++));
    }

    Patient next() {
        return queue.poll();
    }

    // Display patients in actual treatment order
    void displayWaitingList() {

        PriorityQueue<Patient> copy =
            new PriorityQueue<>(queue);

        System.out.println("Waiting list in treatment order:");

        while (!copy.isEmpty()) {
            System.out.print(copy.poll() + " ");
        }

        System.out.println();
    }

    // Performance experiment
    static void runExperiment(int size) {

        int repetitions = 5;

        long totalAddTime = 0;
        long totalRemoveTime = 0;

        for (int r = 0; r < repetitions; r++) {

            PriorityQueue<Patient> testQueue =
                new PriorityQueue<>(
                    Comparator.comparingInt(
                        (Patient p) -> p.severity
                    ).thenComparingLong(p -> p.arrival)
                );

            // Measure insertion
            long startAdd = System.nanoTime();

            for (int i = 0; i < size; i++) {

                int severity = (i % 5) + 1;

                testQueue.add(
                    new Patient(
                        "Patient" + i,
                        severity,
                        i
                    )
                );
            }

            long endAdd = System.nanoTime();

            // Measure removal
            long startRemove = System.nanoTime();

            while (!testQueue.isEmpty()) {
                testQueue.poll();
            }

            long endRemove = System.nanoTime();

            totalAddTime += endAdd - startAdd;
            totalRemoveTime += endRemove - startRemove;
        }

        double averageAdd =
            totalAddTime / (repetitions * 1_000_000.0);

        double averageRemove =
            totalRemoveTime / (repetitions * 1_000_000.0);

        System.out.println("\nQueue size: " + size);

        System.out.printf(
            "Average insertion time: %.3f ms%n",
            averageAdd
        );

        System.out.printf(
            "Average removal time: %.3f ms%n",
            averageRemove
        );
    }

    public static void main(String[] args) {

        Triage er = new Triage();

        // Eight-patient correctness test
        er.admit("Ravi", 3);
        er.admit("Meena", 1);
        er.admit("John", 3);
        er.admit("Asha", 5);
        er.admit("Kumar", 1);
        er.admit("Lata", 2);
        er.admit("Priya", 3);
        er.admit("Dev", 5);

        System.out.println("Eight-patient triage test:");

        er.displayWaitingList();

        System.out.println("\nTreatment order:");

        Patient p;

        while ((p = er.next()) != null) {
            System.out.print(p + " ");
        }

        System.out.println();

        // Growth investigation
        System.out.println("\nPERFORMANCE EXPERIMENT");

        int[] sizes = {100, 10000, 100000};

        for (int size : sizes) {
            runExperiment(size);
        }
    }
}