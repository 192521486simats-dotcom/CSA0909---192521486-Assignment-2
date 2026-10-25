1. Static Variables
A static ticket counter is shared by all ParkingTicket objects. It ensures that each newly issued ticket receives a unique number.
class ParkingTicket {
    static int counter = 0;
    int ticketNo;

    ParkingTicket() {
        ticketNo = ++counter;
    }
}
The counter is static because it belongs to the class rather than to individual tickets.

2. Static Methods and Static Blocks
•	Static method: Can generate ticket numbers or display facility-wide statistics without requiring an individual ticket object.
•	Static block: Can initialize shared configuration when the class is loaded.

3. Object Life Cycle
When a vehicle leaves, its active parking ticket is closed and removed from the active-ticket collection. If no references to the ticket remain, it becomes eligible for garbage collection. Historical records can be retained separately.
Objects can be passed as method arguments and returned from methods using their references.

4. Alternative Concept and Complexity Analysis
If the ticket counter were an instance variable of ParkingFacility, two facilities could generate duplicate ticket numbers because each facility would have its own counter.
A static counter provides one shared sequence across both facilities.
•	Static counter: (O(1)) space for the counter, regardless of the number of facilities.
•	Instance counter: (O(k)) space for (k) facility objects.
•	Generating each ticket takes (O(1)) time in either approach.
A shared static counter is suitable when ticket numbers must be unique across the entire university.
