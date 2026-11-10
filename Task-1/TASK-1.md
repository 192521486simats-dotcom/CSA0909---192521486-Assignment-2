1. Inputs and Outputs
Inputs: Vehicle registration number, vehicle type, floor and slot details, entry and exit times, user category, and parking pass validity.
Outputs: Allocated parking slot, unique parking ticket, parking duration, parking fee, and slot availability.



2. Candidate Classes and Responsibilities
Class	Responsibility
Vehicle	Stores vehicle registration number and type.
TwoWheeler, Car, ElectricCar	Represent different vehicle types and their parking rates.
ParkingSlot	Stores slot number and availability.
ParkingTicket	Stores ticket number, vehicle details, and entry time.
ParkingFacility	Manages floors, slot allocation, and slot release.
ParkingPass	Checks whether a student or staff member has a valid monthly pass.
FeeCalculator	Calculates parking charges based on vehicle type and duration.

3. Business Rules
•	A slot can contain only one vehicle at a time.
•	Every ticket must have a unique number.
•	A vehicle must be assigned a suitable free slot.
•	A slot must be released when the vehicle leaves.
•	Students and staff with valid monthly passes park free.
•	Visitors must pay the applicable parking fee.
•	Electric vehicle charging fees are calculated separately.
4. Assumptions
•	Each floor has a fixed number of slots.
•	Parking duration is calculated using entry and exit times.
•	Every started hour is charged as a full hour.
•	Invalid registration numbers and duplicate active registrations are rejected.

5. Alternative Concept and Complexity Analysis
If vehicles are stored as objects, each Vehicle object keeps its registration number, type, and other details together. This improves readability and makes adding new attributes easier.
If parallel arrays such as regNo[], type[], and entryTime[] are used, the same vehicle's information must be maintained at matching indices.



Operation	Vehicle objects	Parallel arrays
Search by registration number	(O(n))	(O(n))
Space per vehicle	(O(1))	(O(1))

Both approaches require \(O(n)\) total storage for \(n\) vehicles. Objects are preferable because related data and behaviour are grouped together.

