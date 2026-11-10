1. Method Overriding
Method overriding allows a subclass to provide its own implementation of a method inherited from its superclass.
For example, TwoWheeler, Car, and ElectricCar can provide different hourly rates by overriding ratePerHour().
class Vehicle {
    protected double ratePerHour() {
        return 0;
    }
}

class TwoWheeler extends Vehicle {
    @Override
    protected double ratePerHour() {
        return 10;
    }
}

class Car extends Vehicle {
    @Override
    protected double ratePerHour() {
        return 30;
    }
}

2. Rules of Overriding
•	The method name and parameter list must match.
•	The return type must be the same or compatible.
•	The overriding method cannot reduce the access level of the inherited method.
•	The @Override annotation helps identify incorrect overrides.
3. Use of final
•	Final variable: Prevents reassignment of a value, such as a registration number.
•	Final method: Prevents subclasses from overriding that method.
•	Final class: Prevents inheritance.
4. Overriding toString(), equals() and hashCode()
@Override
public String toString() {
    return "Vehicle: " + regNo;
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
These methods provide readable vehicle details and compare vehicles by registration number. However, this equality rule treats a car and an electric car with the same registration number as equal, so the system must enforce unique active registrations.
5. Alternative Concept and Complexity Analysis
Instead of overriding methods, a single calculateFee() method could use a switch statement on the vehicle type.
•	Overriding: Adding a vehicle type generally requires a new subclass.
•	Switch statement: Adding a type requires modifying the central fee-calculation method.
•	Time complexity: Both approaches take \(O(1)\) time for a fixed number of vehicle types.
•	Maintenance: Overriding keeps each vehicle's behaviour separate, while a switch statement becomes harder to maintain as types increase.
