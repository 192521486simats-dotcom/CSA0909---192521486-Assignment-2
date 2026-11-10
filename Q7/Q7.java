
import java.util.*;

class Medicine {
    final String code;
    final String name;
    final int quantity;

    Medicine(String code, String name, int quantity) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return code + " - " + name + " - " + quantity;
    }
}

// Original implementation: sorts every time a report is requested.
class OriginalInventory {
    private final Map<String, Medicine> stock = new HashMap<>();

    OriginalInventory(List<Medicine> medicines) {
        for (Medicine medicine : medicines) {
            stock.put(medicine.code, medicine);
        }
    }

    List<Medicine> getCodeOrderedReport() {
        List<Medicine> report = new ArrayList<>(stock.values());
        report.sort(Comparator.comparing(m -> m.code));
        return report;
    }
}

// Redesigned implementation: caches the sorted report.
class CachedInventory {
    private final Map<String, Medicine> stock = new HashMap<>();
    private List<Medicine> sortedCache = null;

    CachedInventory(List<Medicine> medicines) {
        for (Medicine medicine : medicines) {
            stock.put(medicine.code, medicine);
        }
    }

    void addOrUpdateMedicine(Medicine medicine) {
        stock.put(medicine.code, medicine);
        sortedCache = null;
    }

    List<Medicine> getCodeOrderedReport() {
        if (sortedCache == null) {
            List<Medicine> report =
                new ArrayList<>(stock.values());

            report.sort(Comparator.comparing(m -> m.code));

            sortedCache = Collections.unmodifiableList(report);
        }

        return sortedCache;
    }
}

public class InventoryRedesign {

    static final int REPORT_REQUESTS = 20;
    static final int REPEATS = 5;

    static List<Medicine> createData(int size) {
        List<Medicine> data = new ArrayList<>();

        // Reverse order makes sorting meaningful.
        for (int i = size; i >= 1; i--) {
            String code = String.format("MED%06d", i);
            data.add(new Medicine(
                code, "Medicine" + i, 100
            ));
        }

        return data;
    }

    static long runOriginal(List<Medicine> data) {
        OriginalInventory inventory =
            new OriginalInventory(data);

        long checksum = 0;
        long start = System.nanoTime();

        for (int i = 0; i < REPORT_REQUESTS; i++) {
            List<Medicine> report =
                inventory.getCodeOrderedReport();

            checksum += report.get(0).code.hashCode();
            checksum += report.get(report.size() - 1).code.hashCode();
        }

        long end = System.nanoTime();

        // Consume result so the work is observable.
        if (checksum == 0) {
            System.out.println("Checksum: " + checksum);
        }

        return end - start;
    }

    static long runRedesigned(List<Medicine> data) {
        CachedInventory inventory =
            new CachedInventory(data);

        long checksum = 0;
        long start = System.nanoTime();

        for (int i = 0; i < REPORT_REQUESTS; i++) {
            List<Medicine> report =
                inventory.getCodeOrderedReport();

            checksum += report.get(0).code.hashCode();
            checksum += report.get(report.size() - 1).code.hashCode();
        }

        long end = System.nanoTime();

        if (checksum == 0) {
            System.out.println("Checksum: " + checksum);
        }

        return end - start;
    }

    static void testCacheInvalidation() {
        List<Medicine> data = createData(3);
        CachedInventory inventory = new CachedInventory(data);

        System.out.println("\nCache correctness check:");

        System.out.println(
            "Before update: " + inventory.getCodeOrderedReport()
        );

        inventory.addOrUpdateMedicine(
            new Medicine("MED000000", "NewMedicine", 50)
        );

        System.out.println(
            "After update: " + inventory.getCodeOrderedReport()
        );
    }

    public static void main(String[] args) {

        int[] sizes = {100, 10000, 100000};

        System.out.println("Report requests per run: " + REPORT_REQUESTS);
        System.out.println("Measured repetitions: " + REPEATS);

        for (int size : sizes) {

            List<Medicine> data = createData(size);

            // Warm-up runs
            runOriginal(data);
            runRedesigned(data);

            long originalTotal = 0;
            long redesignedTotal = 0;

            for (int r = 0; r < REPEATS; r++) {
                originalTotal += runOriginal(data);
                redesignedTotal += runRedesigned(data);
            }

            double originalAverage =
                originalTotal / (double) REPEATS / 1_000_000.0;

            double redesignedAverage =
                redesignedTotal / (double) REPEATS / 1_000_000.0;

            System.out.println("\nInventory size: " + size);
            System.out.printf(
                "Original average: %.3f ms%n", originalAverage
            );
            System.out.printf(
                "Redesigned average: %.3f ms%n", redesignedAverage
            );
        }

        testCacheInvalidation();
    }
}
