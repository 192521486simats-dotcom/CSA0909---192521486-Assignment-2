import java.util.HashSet;
import java.util.Set;

// Incorrect implementation: hashCode() is not overridden
class BrokenStudent {

    String regNo;
    String name;

    BrokenStudent(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof BrokenStudent)) {
            return false;
        }

        BrokenStudent other = (BrokenStudent) o;

        return regNo.equals(other.regNo);
    }
}

// Correct implementation
class Student {

    private final String regNo;
    private final String name;

    Student(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Student)) {
            return false;
        }

        Student other = (Student) o;

        return regNo.equals(other.regNo);
    }

    @Override
    public int hashCode() {
        return regNo.hashCode();
    }

    @Override
    public String toString() {
        return regNo + " " + name;
    }
}

// Additional test: mutable registration number
class MutableStudent {

    String regNo;

    MutableStudent(String regNo) {
        this.regNo = regNo;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof MutableStudent)) {
            return false;
        }

        MutableStudent other = (MutableStudent) o;

        return regNo.equals(other.regNo);
    }

    @Override
    public int hashCode() {
        return regNo.hashCode();
    }
}

public class Attendance {

    public static void main(String[] args) {

        // TEST 1: Same object scanned twice
        System.out.println("TEST 1: Same Object");

        Set<BrokenStudent> test1 = new HashSet<>();

        BrokenStudent s1 =
                new BrokenStudent("192311001", "Arun");

        test1.add(s1);
        test1.add(s1);

        System.out.println("Unique students: " + test1.size());


        // TEST 2: Different objects with same registration number
        System.out.println("\nTEST 2: Faulty Implementation");

        Set<BrokenStudent> faultySet = new HashSet<>();

        faultySet.add(
                new BrokenStudent("192311001", "Arun"));

        faultySet.add(
                new BrokenStudent("192311002", "Divya"));

        faultySet.add(
                new BrokenStudent("192311001", "Arun"));

        System.out.println(
                "Unique students before correction: "
                + faultySet.size());


        // TEST 2: Corrected implementation
        System.out.println("\nTEST 2: Corrected Implementation");

        Set<Student> correctedSet = new HashSet<>();

        correctedSet.add(
                new Student("192311001", "Arun"));

        correctedSet.add(
                new Student("192311002", "Divya"));

        correctedSet.add(
                new Student("192311001", "Arun"));

        System.out.println(
                "Unique students after correction: "
                + correctedSet.size());

        for (Student student : correctedSet) {
            System.out.println(student);
        }


        // TEST 3: Changing a field used in hashCode()
        System.out.println("\nTEST 3: Mutable Registration Number");

        Set<MutableStudent> mutableSet = new HashSet<>();

        MutableStudent student =
                new MutableStudent("REG-100");

        mutableSet.add(student);

        System.out.println(
                "Before modification: "
                + mutableSet.contains(student));

        student.regNo = "REG-900";

        System.out.println(
                "After modification: "
                + mutableSet.contains(student));
    }
}