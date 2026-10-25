1. Pseudocode for Slot Allocation
ALGORITHM AllocateSlot(vehicleNo)
    FOR each floor f
        FOR each slot s in floor f
            IF slots[f][s] is NULL THEN
                slots[f][s] ← vehicleNo
                RETURN (f, s)
            END IF
        END FOR
    END FOR
    RETURN NULL
END ALGORITHM

2. Pseudocode for Parking Fee Calculation
ALGORITHM CalculateFee(minutes, rate)
    IF minutes <= 0 THEN
        RETURN 0
    END IF

    hours ← (minutes + 59) / 60
    fee ← hours × rate

    IF fee > dailyMaximum THEN
        fee ← dailyMaximum
    END IF

    RETURN fee
END ALGORITHM

3. Use of Control Statements
•	if-else: Checks slot availability and applies fee rules.
•	break: Stops a loop when no further searching is required.
•	continue: Skips occupied slots.
•	return: Returns the allocated slot or calculated fee immediately.
•	No free slot: The allocation method returns null.

4. Complete the Code
public class ParkingFacility {
    private final String[][] slots;

    public ParkingFacility(int[] slotsPerFloor) {
        slots = new String[slotsPerFloor.length][];
        for (int f = 0; f < slotsPerFloor.length; f++) {
            slots[f] = new String[slotsPerFloor[f]];
        }
    }

    public int[] allocate(String vehicleNo) {
        for (int f = 0; f < slots.length; f++) {
            for (int s = 0; s < slots[f].length; s++) {
                if (slots[f][s] == null) {
                    slots[f][s] = vehicleNo;
                    return new int[]{f, s};
                }
            }
        }
        return null;
    }

    public boolean release(int floor, int slot) {
        if (floor < 0 || floor >= slots.length ||
            slot < 0 || slot >= slots[floor].length ||
            slots[floor][slot] == null) {
            return false;
        }

        slots[floor][slot] = null;
        return true;
    }
    public static void main(String[] args) {
        ParkingFacility p = new ParkingFacility(new int[]{2, 1});
        System.out.println(java.util.Arrays.toString(p.allocate("TN01AB1111")));
        System.out.println(java.util.Arrays.toString(p.allocate("TN02CD2222")));
        System.out.println(java.util.Arrays.toString(p.allocate("TN03EF3333")));
        System.out.println(java.util.Arrays.toString(p.allocate("TN04GH4444")));
        System.out.println(p.release(0, 1) + " " + p.release(0, 1) + " "
                + p.release(5, 0));
        System.out.println(java.util.Arrays.toString(p.allocate("TN04GH4444")));
    }
}
Expected Output
[0, 0]
[0, 1]
[1, 0]
null
true false false
[0, 1]

5. Alternative Concept and Complexity Analysis
Using a stack of free slot positions allows allocation and release to take (O(1)) time on average, compared with scanning slots, which takes (O(FS)) in the worst case.
A free-slot count for each floor helps identify floors with available space. The stack and counts require (O(N+F)) additional space, where (N) is the total number of slots and (F) is the number of floors. The scanning approach requires only O(1) extra space beyond the slot array.
