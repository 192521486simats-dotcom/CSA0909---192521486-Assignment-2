1. Fields and Methods
Class	Fields	Methods
Vehicle	Registration number, type	calculateFee()
ParkingSlot	Slot number, availability	allocate(), release()
ParkingTicket	Ticket number, entry time	getDuration(), getTicketDetails()
ParkingFacility	Floors, slots	allocateSlot(), releaseSlot()
FeeCalculator	Parking rates	calculateFee()

2. Types of Constructors
•	Default constructor: Initializes an object with default values.
•	Parameterized constructor: Initializes an object with specified values, such as a registration number and vehicle type.
•	Overloaded constructors: Allow objects to be created with different sets of arguments.
•	Constructor chaining: Uses this() to call another constructor in the same class and avoid repeated code.
Example:
class ParkingTicket {
    int ticketNo;
    String regNo;

    ParkingTicket(int ticketNo) {
        this(ticketNo, "UNKNOWN");
    }

    ParkingTicket(int ticketNo, String regNo) {
        this.ticketNo = ticketNo;
        this.regNo = regNo;
    }
}

3. Use of this and Copy Constructor
The this keyword refers to the current object and distinguishes instance variables from constructor parameters.
A copy constructor can create a new ticket using the details of an existing ticket. However, the new ticket must receive a unique ticket number rather than copying the old number.

4. Object Memory
•	Stack: Stores local variables and object references.
•	Heap: Stores the actual objects created using new.
•	When a ticket is no longer referenced by the program, it becomes eligible for garbage collection.

5. Alternative Concept and Complexity Analysis
•	Constructors: Initialize objects immediately and can validate required values.
•	Setters: Allow values to be changed after creation, but may temporarily leave an object invalid.
•	Static factory methods: Can control object creation and return only valid objects.
Creating (n) tickets takes (O(n)) total time and (O(n)) space with any of these approaches, assuming constant-time initialization per ticket. Constructors and factory methods provide better control over object validity.
