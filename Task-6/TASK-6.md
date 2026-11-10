1. Inheritance Hierarchy
Vehicle is the superclass. TwoWheeler and Car inherit from it, while ElectricCar inherits from Car.
             Vehicle
             /     \
    TwoWheeler      Car
                      |
                 ElectricCar
This uses hierarchical inheritance from Vehicle and multilevel inheritance from Vehicle to Car to ElectricCar.

2. Inherited and Added Features
Class	Features
Vehicle	Registration number and common vehicle methods
TwoWheeler	Inherits vehicle details; uses ₹10 per hour
Car	Inherits vehicle details; uses ₹30 per hour
ElectricCar	Inherits car features and adds charging capability

3. Use of super
The super keyword calls the parent class constructor and accesses parent class methods when required.
class Vehicle {
    String regNo;

    Vehicle(String regNo) {
        this.regNo = regNo;
    }
}

class Car extends Vehicle {
    Car(String regNo) {
        super(regNo);
    }
}

class ElectricCar extends Car {
    ElectricCar(String regNo) {
        super(regNo);
    }
}

4. Constructor Execution Trace
When new ElectricCar("TN03C3") is executed:
1.	ElectricCar constructor starts.
2.	Car constructor is called using super().
3.	Vehicle constructor initializes the registration number.
4.	Control returns to the Car constructor.
5.	Control returns to the ElectricCar constructor.
Order: Vehicle → Car → ElectricCar.

5. Alternative Concept and Complexity Analysis
Using composition, a single Vehicle class could contain a VehicleType object that stores the vehicle category and hourly rate.
•	Inheritance: Each vehicle type has its own subclass and fee behaviour.
•	Composition: A new vehicle type can be added by creating a new type configuration without necessarily creating a subclass.
•	Time complexity: Fee calculation is \(O(1)\) in both approaches when the rate is directly available.
•	Space complexity: Both require \(O(1)\) space per vehicle, excluding shared type objects.
Inheritance is suitable for the current design because vehicle categories have distinct behaviours. Composition is more flexible when vehicle types and pricing rules change frequently.
