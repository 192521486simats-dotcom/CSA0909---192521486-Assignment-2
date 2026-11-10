A. Impact Analysis 
The new requirements introduce e-bikes with a flat fee, delivery vans charged at double the car rate between 9 AM and 5 PM, a one-day visitor pass, and five floors with different numbers of parking slots.
•	Vehicle classes: New EBike and DeliveryVan subclasses are required.
•	Pricing: The fee calculation must support flat fees and time-based rules.
•	Pass system: A OneDayPass class is needed for visitors.
•	ParkingFacility: The slot structure must support unequal numbers of slots on different floors.
A normal rectangular array is unsuitable because every floor must have the same number of slots. 
A jagged array can represent different slot counts per floor.

B. Evaluation of the Original Design 
The original design can be retained partly, but it needs modification.
•	Inheritance allows new vehicle categories to extend the Vehicle class.
•	If pricing conditions are written inside multiple classes, adding new rules requires changing existing code.
•	A separate pricing abstraction allows new pricing rules to be added without modifying tested vehicle classes.
•	A jagged array supports floors with unequal slot capacities.
Therefore, modifying the design is better than completely replacing it.
C. Adaptation or Redesign (2 Marks)
Revised class structure
Vehicle (abstract class)
Car
EBike
DeliveryVan
PricingRule (interface)
HourlyPricing
FlatFeePricing
TimeBasedPricing
ParkingFacility — jagged array of slots
OneDayPass — visitor pass validation

Java implementation example
abstract class Vehicle {
    String regNo;

    Vehicle(String regNo) {
        this.regNo = regNo;
    }

    abstract double calculateFee(double hours);
}

class Car extends Vehicle {
    Car(String regNo) {
        super(regNo);
    }

    double calculateFee(double hours) {
        return Math.ceil(hours) * 30;
    }
}

class EBike extends Vehicle {
    EBike(String regNo) {
        super(regNo);
    }

    double calculateFee(double hours) {
        return 20; // Flat fee
    }
}

class DeliveryVan extends Vehicle {
    DeliveryVan(String regNo) {
        super(regNo);
    }

    double calculateFee(double hours) {
        return Math.ceil(hours) * 60;
    }
}

class OneDayPass {
    private String visitorName;
    private boolean valid;

    OneDayPass(String visitorName) {
        this.visitorName = visitorName;
        this.valid = true;
    }

    boolean isValid() {
        return valid;
    }

    void usePass() {
        valid = false;
    }
}
The van method above illustrates double the car rate of ₹30 per hour. The complete system must apply that rate only to the time parked between 9 AM and 5 PM; outside that period, the normal applicable rate should be used.
For unequal floor capacities, use:
String[][] slots = new String[5][];
slots[0] = new String[20];
slots[1] = new String[30];
slots[2] = new String[40];
slots[3] = new String[50];
slots[4] = new String[60];
Each floor can now have a different number of slots.

D. Evidence-Based Defence 

Requirement	Test case	Expected result
E-bike flat fee	Park for 3 hours	₹20
Delivery van	Park for 2 hours entirely between 9 AM and 5 PM	₹120
One-day visitor pass	Valid pass is presented	Parking fee waived according to pass rules
Unequal floor capacities	Allocate a slot on each configured floor	Allocation respects each floor's capacity

Final decision: Modify the original design. Retain the existing Vehicle and ParkingFacility concepts, add new subclasses and a pass class, separate pricing rules, and replace the rectangular slot array with a jagged array. This improves extensibility and avoids rewriting existing tested classes when further vehicle types or pricing policies are introduced.
