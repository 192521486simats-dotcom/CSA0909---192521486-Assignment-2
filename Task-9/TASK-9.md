1. Abstract Class
Vehicle should be an abstract class because all vehicles share common data and behaviour, but the hourly rate differs by vehicle type.
abstract class Vehicle {
    String regNo;

    Vehicle(String regNo) {
        this.regNo = regNo;
    }

    abstract double ratePerHour();
}
The abstract method ratePerHour() is implemented by subclasses such as Car and TwoWheeler.

2. Interface
The Chargeable interface represents vehicles that support charging.
interface Chargeable {
    double chargingFee(int kWh);
}
ElectricCar implements this interface to calculate its charging fee.

3. Abstract Class vs Interface
•	Abstract class: Stores common state, provides constructors and shared methods, and defines abstract behaviour.
•	Interface: Defines a capability that different classes can implement, even when they have different parent classes.
A class can implement multiple interfaces, allowing it to support several capabilities.




4. Default and Static Interface Methods
•	A default method provides a common implementation that implementing classes can inherit.
•	A static method provides a utility operation associated with the interface itself.

5. Alternative Concept and Complexity Analysis
If Vehicle became an interface and Chargeable became an abstract class:
•	Vehicle could not have ordinary instance fields or constructors.
•	Classes could implement multiple interfaces but extend only one class.
•	Moving Chargeable to an abstract class would prevent a class already extending another class from extending it as well.
•	Both approaches generally have O(1) method-call time for these operations.
•	Object storage remains O(1) per vehicle, excluding additional referenced objects.
Therefore, keeping Vehicle as an abstract class and Chargeable as an interface is more suitable.
