1. Data Types
Data	Java data type	Reason
Slot number	int	Stores an integer index.
Vehicle registration number	String	Contains letters and digits.
Entry time	LocalDateTime	Stores date and time.
Duration	long	Stores parking duration in minutes.
Parking fee	double	Stores decimal values.
Vehicle type	String or enum	Identifies the vehicle category.

2. Array Representation
A jagged two-dimensional array, String[][] slots, is suitable because each floor can have a different number of slots.
Example:
String[][] slots = new String[3][];
slots[0] = new String[20];
slots[1] = new String[50];
slots[2] = new String[100];
Each array element stores a vehicle registration number. A null value indicates that the slot is free.

3. Type Conversion and Precision
Integer division can produce an incorrect parking fee.
Example:
double hours = 130 / 60;
The result is 2.0 because integer division removes the fractional part. If every started hour is charged, 130 minutes must be charged as 3 hours.
Correct approach:
long hours = (130 + 59) / 60;
double fee = hours * 30.0;
Output: 90.0
This avoids integer division errors and correctly charges three hours at ₹30 per hour.

4. Input Method
The Scanner class can read registration numbers, vehicle types, and other user inputs from the keyboard.

5. Alternative Concept and Complexity Analysis
•	Jagged array: Supports different slot counts per floor. Locating a slot by scanning takes (O(FS)) time in the worst case, where (F) is the number of floors and (S) is the maximum slots per floor.
•	One-dimensional array: Uses the index floor * slotsPerFloor + slot. It works directly when every floor has the same number of slots. Different floor sizes require offset calculations.
•	ArrayList of arrays: ArrayList<String[]> supports floors with different slot counts and allows the floor list to grow.
All representations require (O(N)) space for (N) total slots.
