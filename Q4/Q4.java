import java.util.*;

class Medicine {

    String code;
    String name;
    int quantity;
    double price;

    Medicine(String code, String name, int quantity, double price) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return code + " | " + name +
               " | Stock: " + quantity +
               " | Price: " + price;
    }
}

public class PharmacyInventory {

    static final int LOOKUPS = 100000;

    static List<Medicine> createMedicines(int size) {

        List<Medicine> medicines = new ArrayList<>();

        for (int i = 1; i <= size; i++) {

            String code = String.format("MED%06d", i);

            medicines.add(
                new Medicine(
                    code,
                    "Medicine " + i,
                    100 + i,
                    25.50 + i
                )
            );
        }

        return medicines;
    }

    static Map<String, Medicine> createHashMap(
            List<Medicine> medicines) {

        Map<String, Medicine> map = new HashMap<>();

        for (Medicine medicine : medicines) {
            map.put(medicine.code, medicine);
        }

        return map;
    }

    static Map<String, Medicine> createTreeMap(
            List<Medicine> medicines) {

        Map<String, Medicine> map = new TreeMap<>();

        for (Medicine medicine : medicines) {
            map.put(medicine.code, medicine);
        }

        return map;
    }

    static Map<String, Medicine> createLinkedHashMap(
            List<Medicine> medicines) {

        Map<String, Medicine> map = new LinkedHashMap<>();

        for (Medicine medicine : medicines) {
            map.put(medicine.code, medicine);
        }

        return map;
    }

    static void measureLookups(
            String name,
            Map<String, Medicine> map,
            List<Medicine> medicines) {

        long checksum = 0;

        long start = System.nanoTime();

        for (int i = 0; i < LOOKUPS; i++) {

            int index = i % medicines.size();

            String code = medicines.get(index).code;

            Medicine medicine = map.get(code);

            if (medicine != null) {
                checksum += medicine.quantity;
            }
        }

        long end = System.nanoTime();

        System.out.printf(
            "%-18s Lookup Time: %.3f ms%n",
            name,
            (end - start) / 1_000_000.0
        );

        System.out.println("Checksum: " + checksum);
    }

    static void measureCodeReport(
            String name,
            Map<String, Medicine> map) {

        long start = System.nanoTime();

        List<String> codes = new ArrayList<>(map.keySet());

        // Sort only when the map does not maintain code order
        if (!(map instanceof TreeMap)) {
            Collections.sort(codes);
        }

        long end = System.nanoTime();

        System.out.printf(
            "%-18s Code Report: %.3f ms%n",
            name,
            (end - start) / 1_000_000.0
        );

        System.out.println(
            "First medicine code: " + codes.get(0)
        );

        System.out.println(
            "Last medicine code: " + codes.get(codes.size() - 1)
        );
    }

    static void measureInsertionReport(
            String name,
            Map<String, Medicine> map,
            List<Medicine> medicines) {

        long start = System.nanoTime();

        List<String> codes = new ArrayList<>();

        if (map instanceof LinkedHashMap) {

            codes.addAll(map.keySet());

        } else {

            // Reconstruct original insertion order
            for (Medicine medicine : medicines) {
                if (map.containsKey(medicine.code)) {
                    codes.add(medicine.code);
                }
            }
        }

        long end = System.nanoTime();

        System.out.printf(
            "%-18s Insertion Report: %.3f ms%n",
            name,
            (end - start) / 1_000_000.0
        );

        System.out.println(
            "First inserted code: " + codes.get(0)
        );

        System.out.println(
            "Last inserted code: " + codes.get(codes.size() - 1)
        );
    }

    static void runExperiment(int size) {

        System.out.println("\n==================================");
        System.out.println("INVENTORY SIZE: " + size);
        System.out.println("==================================");

        List<Medicine> medicines = createMedicines(size);

        Map<String, Medicine> hashMap =
                createHashMap(medicines);

        Map<String, Medicine> treeMap =
                createTreeMap(medicines);

        Map<String, Medicine> linkedHashMap =
                createLinkedHashMap(medicines);

        System.out.println("\nLOOKUP PERFORMANCE");

        measureLookups("HashMap", hashMap, medicines);
        measureLookups("TreeMap", treeMap, medicines);
        measureLookups("LinkedHashMap", linkedHashMap, medicines);

        System.out.println("\nCODE-ORDER REPORT");

        measureCodeReport("HashMap", hashMap);
        measureCodeReport("TreeMap", treeMap);
        measureCodeReport("LinkedHashMap", linkedHashMap);

        System.out.println("\nINSERTION-ORDER REPORT");

        measureInsertionReport(
            "HashMap", hashMap, medicines
        );

        measureInsertionReport(
            "TreeMap", treeMap, medicines
        );

        measureInsertionReport(
            "LinkedHashMap", linkedHashMap, medicines
        );
    }

    public static void main(String[] args) {

        int[] sizes = {100, 10000, 100000};

        for (int size : sizes) {
            runExperiment(size);
        }
    }
}