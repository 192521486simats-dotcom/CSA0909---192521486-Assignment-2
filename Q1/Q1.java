public class Task1 {

    static volatile long resultHolder;

    // Method 1: Using String concatenation
    static long usingString(int tickets, int fields) {

        long checksum = 0;

        for (int i = 0; i < tickets; i++) {

            String ticket = "";

            for (int j = 1; j <= fields; j++) {
                ticket = ticket + "Field" + j + ":Value" + j + "\n";
            }

            checksum += ticket.length();
        }

        return checksum;
    }

    // Method 2: Using StringBuilder
    static long usingStringBuilder(int tickets, int fields) {

        long checksum = 0;

        for (int i = 0; i < tickets; i++) {

            StringBuilder ticket = new StringBuilder();

            for (int j = 1; j <= fields; j++) {
                ticket.append("Field")
                      .append(j)
                      .append(":Value")
                      .append(j)
                      .append('\n');
            }

            checksum += ticket.length();
        }

        return checksum;
    }

    // Method 3: Using StringBuffer
    static long usingStringBuffer(int tickets, int fields) {

        long checksum = 0;

        for (int i = 0; i < tickets; i++) {

            StringBuffer ticket = new StringBuffer();

            for (int j = 1; j <= fields; j++) {
                ticket.append("Field")
                      .append(j)
                      .append(":Value")
                      .append(j)
                      .append('\n');
            }

            checksum += ticket.length();
        }

        return checksum;
    }

    // Measure execution time over repeated runs
    static void measure(String method, int tickets, int fields) {

        int repetitions = 5;
        long totalTime = 0;
        long expectedResult = -1;

        // Warm-up
        for (int i = 0; i < 2; i++) {
            runMethod(method, tickets, fields);
        }

        for (int i = 0; i < repetitions; i++) {

            long start = System.nanoTime();

            long result = runMethod(method, tickets, fields);

            long end = System.nanoTime();

            if (expectedResult == -1) {
                expectedResult = result;
            } else if (expectedResult != result) {
                throw new IllegalStateException("Results do not match");
            }

            totalTime += (end - start);
            resultHolder = result;
        }

        double averageTime =
                totalTime / (double) repetitions / 1_000_000.0;

        System.out.printf(
                "%-18s Average Time: %.3f ms%n",
                method,
                averageTime
        );
    }

    static long runMethod(String method, int tickets, int fields) {

        switch (method) {
            case "String":
                return usingString(tickets, fields);

            case "StringBuilder":
                return usingStringBuilder(tickets, fields);

            case "StringBuffer":
                return usingStringBuffer(tickets, fields);

            default:
                return 0;
        }
    }

    // Approximate memory measurement
    static long getUsedMemory() {

        Runtime runtime = Runtime.getRuntime();

        return runtime.totalMemory() - runtime.freeMemory();
    }

    static void runWorkload(
            String name, int tickets, int fields) {

        System.out.println("\n" + name);
        System.out.println("Tickets: " + tickets);
        System.out.println("Fields per ticket: " + fields);

        System.gc();

        long memoryBefore = getUsedMemory();

        measure("String", tickets, fields);
        measure("StringBuilder", tickets, fields);
        measure("StringBuffer", tickets, fields);

        long memoryAfter = getUsedMemory();

        System.out.printf(
                "Approximate memory change: %.2f KB%n",
                (memoryAfter - memoryBefore) / 1024.0
        );
    }

    public static void main(String[] args) {

        runWorkload("WORKLOAD 1", 1, 6);

        runWorkload("WORKLOAD 2", 10000, 50);

        runWorkload("WORKLOAD 3", 5000, 100);
    }
}