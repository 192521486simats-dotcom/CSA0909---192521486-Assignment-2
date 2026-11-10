1. Compile-Time Polymorphism
Method overloading allows methods to share a name but have different parameter lists.
double calculateFee(int minutes) {
    return Math.ceil(minutes / 60.0) * ratePerHour();
}

double calculateFee(java.time.LocalTime in,
                    java.time.LocalTime out) {
    long minutes = java.time.Duration.between(in, out).toMinutes();
    return calculateFee((int) minutes);
}
The compiler selects the appropriate method based on the arguments passed.

2. Runtime Polymorphism
A Vehicle reference can store objects of different subclasses.
Vehicle[] vehicles = {
    new TwoWheeler("TN01A1"),
    new Car("TN02B2"),
    new ElectricCar("TN03C3")
};
When calculateFee() is called, Java executes the implementation appropriate to the actual object.

3. Dynamic Method Dispatch Trace
Vehicle v = new Car("TN02B2");
double fee = v.calculateFee(130);
•	Declared reference type: Vehicle
•	Actual object type: Car
•	Billable hours: 3
•	Rate: ₹30 per hour
•	Fee: ₹90
The calculation uses the car's rate through runtime method dispatch.

4. Advantages
•	Reduces long if-else chains.
•	Supports different vehicle types through a common reference.
•	Makes the system easier to extend and maintain.

5. Complete the Code
interface Chargeable {
    double chargingFee(int kWh);
}

abstract class Vehicle {
    private final String regNo;

    protected Vehicle(String regNo) {
        this.regNo = regNo;
    }

    public String getRegNo() {
        return regNo;
    }

    protected abstract double ratePerHour();

    public double calculateFee(int minutes) {
        if (minutes <= 0) return 0;
        long hours = (minutes + 59L) / 60;
        return hours * ratePerHour();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Vehicle
                && regNo.equals(((Vehicle) o).regNo);
    }

    @Override
    public int hashCode() {
        return regNo.hashCode();
    }
}

class TwoWheeler extends Vehicle {
    TwoWheeler(String regNo) {
        super(regNo);
    }

    @Override
    protected double ratePerHour() {
        return 10;
    }
}

class Car extends Vehicle {
    Car(String regNo) {
        super(regNo);
    }

    @Override
    protected double ratePerHour() {
        return 30;
    }
}

class ElectricCar extends Car implements Chargeable {
    ElectricCar(String regNo) {
        super(regNo);
    }

    @Override
    public double chargingFee(int kWh) {
        return kWh * 12.0;
    }
}

public class FeeDemo {
    public static void main(String[] args) {
        Vehicle[] parked = {
            new TwoWheeler("TN01A1"),
            new Car("TN02B2"),
            new ElectricCar("TN03C3")
        };

        for (Vehicle v : parked) {
            double fee = v.calculateFee(130);
            if (v instanceof Chargeable c) {
                fee += c.chargingFee(10);
            }

            System.out.println(v.getClass().getSimpleName()
                    + " " + v.getRegNo() + " -> Rs " + fee);
        }

        System.out.println(
            new Car("TN02B2").equals(new ElectricCar("TN02B2"))
        );
    }
}

Expected Output
TwoWheeler TN01A1 -> Rs 30.0
Car TN02B2 -> Rs 90.0
ElectricCar TN03C3 -> Rs 210.0
true

6. Explanation of the Last Output
The last line prints true because equals() compares only the registration number. Both objects have registration number TN02B2.
This is correct if registration numbers uniquely identify vehicles. However, the system should prevent duplicate registrations for active vehicles.

7. Alternative Concept and Complexity Analysis
Using instanceof checks and casting would require checking each vehicle type before calculating its fee.
•	Dynamic dispatch: Typically \(O(1)\) method selection.
•	Type checks: Up to \(O(k)\) checks for \(k\) vehicle types.
Maintenance: Dynamic dispatch is easier to extend because new subclasses can implement their own behaviour without changing the central calculation logic.
