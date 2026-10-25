1. Test Cases
Test case	Expected result
Different vehicle types enter and exit	Correct slot allocation and release
Last free slot is occupied	Vehicle gets the last available slot
Zero-minute parking	Parking fee is ₹0
Parking for exactly 60 minutes	One hour's fee is charged
Unknown vehicle type	Operation is rejected
Duplicate registration number	Duplicate entry is rejected
Invalid slot number	Error is displayed or operation is rejected






2. Boundary and Invalid Case Testing

Input	Expected result
Only one slot is free	Vehicle is allocated that slot
0 minutes at ₹30/hour	₹0
60 minutes at ₹30/hour	₹30
61 minutes at ₹30/hour	₹60, if every started hour is charged
Unknown vehicle type	Error message
Duplicate registration number	Rejected
Invalid slot number	Rejected

3. Daily Collection Calculation
Assume every started hour is charged.
Vehicle	Duration	Rate/hour	Fee
Two-wheeler	90 minutes	₹10	₹20
Car	130 minutes	₹30	₹90
Electric car	60 minutes	₹30	₹30
Total collection			₹140

Total daily collection = ₹20 + ₹90 + ₹30 = ₹140.

4. Evaluation of Design
•	Encapsulation: Private fields protect vehicle and parking data.
•	Inheritance: Common vehicle properties are defined in a parent class.
•	Polymorphism: Different vehicle types can calculate parking fees differently.
•	Abstraction: Essential operations are exposed while implementation details are hidden.
•	Limitation: Frequent changes to pricing rules may require modifying the existing code if pricing is not separated into its own component.




6.	Alternative Data Structures

Data structure	Ticket search	Space
Array	O(n)	O(n)
ArrayList	O(n)	O(n)
HashMap using registration number	Average O(1)	O(n)

A HashMap is preferable when tickets must be found quickly using vehicle registration numbers.
