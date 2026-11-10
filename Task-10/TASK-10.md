
1. Package Structure
parking/
    model/
        Vehicle.java
        ParkingTicket.java
        ParkingSlot.java
    service/
        ParkingFacility.java
        FeeCalculator.java
    app/
        Main.java
•	parking.model contains data classes.
•	parking.service contains parking operations and fee calculation.
•	parking.app contains the main application.

2. Access Modifiers
Modifier	Usage
private	Protects fields from direct external access.
Default	Restricts access to the same package.
protected	Allows subclasses and same-package classes to access members.
public	Makes required constructors and methods accessible from other packages.

3. Getters and Setters
Private fields protect the object's data. Getters provide controlled access, while setters validate values before updating them.
class ParkingSlot {
    private int slotNo;

    public int getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(int slotNo) {
        if (slotNo <= 0) {
            throw new IllegalArgumentException(
                "Invalid slot number"
            );
        }
        this.slotNo = slotNo;
    }
}
This prevents invalid slot numbers from being stored.

4. Wrapper Classes and Autoboxing
Wrapper classes such as Integer represent primitive values as objects. Autoboxing automatically converts an int to an Integer.
Integer a = 100;
Integer b = 100;
System.out.println(a.equals(b));
Output:
true
Using == to compare two Integer objects checks reference identity in many situations rather than numerical equality. Therefore, equals() should be used to compare their values.

5. Alternative Concept and Complexity Analysis
If fields were public, any class could modify them directly without validation. This could introduce invalid slot numbers, negative durations, or incorrect fees.
•	Private fields with validation: Each update requires O(1) time for simple validation.
•	Public fields: Direct assignment takes O(1) time but does not guarantee valid data.
•	Maintenance: Encapsulation centralizes validation in one place, while public fields require repeated checks across calling methods.
Encapsulation is therefore safer and easier to maintain.
