
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

// ---------- Pricing Interface ----------
interface PricingRule {
    double calculateFee(LocalDateTime entry, LocalDateTime exit);
}

// ---------- Vehicle Hierarchy ----------
abstract class Vehicle {
    private final String regNo;

    Vehicle(String regNo) {
        this.regNo = regNo;
    }

    public String getRegNo() {
        return regNo;
    }

    public abstract double calculateFee(
            LocalDateTime entry, LocalDateTime exit);
}

class Car extends Vehicle {
    static final double RATE = 30.0;

    Car(String regNo) {
        super(regNo);
    }

    @Override
    public double calculateFee(LocalDateTime entry, LocalDateTime exit) {
        long minutes = Math.max(0,
                Duration.between(entry, exit).toMinutes());
        return Math.ceil(minutes / 60.0) * RATE;
    }
}

class EBike extends Vehicle {
    static final double FLAT_FEE = 20.0;

    EBike(String regNo) {
        super(regNo);
    }

    @Override
    public double calculateFee(LocalDateTime entry, LocalDateTime exit) {
        return FLAT_FEE;
    }
}

class DeliveryVan extends Vehicle {
    static final double CAR_RATE = 30.0;
    static final double VAN_RATE = CAR_RATE * 2;
    static final LocalTime RULE_START = LocalTime.of(9, 0);
    static final LocalTime RULE_END = LocalTime.of(17, 0);

    DeliveryVan(String regNo) {
        super(regNo);
    }

    @Override
    public double calculateFee(LocalDateTime entry, LocalDateTime exit) {
        if (exit.isBefore(entry)) {
            throw new IllegalArgumentException("Exit precedes entry");
        }

        double totalFee = 0;
        LocalDateTime cursor = entry;

        while (cursor.isBefore(exit)) {
            LocalDateTime nextDay =
                    cursor.toLocalDate().plusDays(1).atStartOfDay();
            LocalDateTime segmentEnd =
                    exit.isBefore(nextDay) ? exit : nextDay;

            LocalDateTime peakStart =
                    cursor.toLocalDate().atTime(RULE_START);
            LocalDateTime peakEnd =
                    cursor.toLocalDate().atTime(RULE_END);

            LocalDateTime overlapStart =
                    cursor.isAfter(peakStart) ? cursor : peakStart;
            LocalDateTime overlapEnd =
                    segmentEnd.isBefore(peakEnd) ? segmentEnd : peakEnd;

            long peakMinutes = 0;
            if (overlapEnd.isAfter(overlapStart)) {
                peakMinutes = Duration.between(
                        overlapStart, overlapEnd).toMinutes();
            }

            long totalMinutes =
                    Duration.between(cursor, segmentEnd).toMinutes();
            long normalMinutes = totalMinutes - peakMinutes;

            // Charge each started hour at the applicable rate.
            totalFee += Math.ceil(peakMinutes / 60.0) * VAN_RATE;
            totalFee += Math.ceil(normalMinutes / 60.0) * CAR_RATE;

            cursor = segmentEnd;
        }

        return totalFee;
    }
}

// ---------- Visitor One-Day Pass ----------
class OneDayPass {
    private final String visitorName;
    private final LocalDate validDate;

    OneDayPass(String visitorName, LocalDate validDate) {
        if (visitorName == null || visitorName.trim().isEmpty()
                || validDate == null) {
            throw new IllegalArgumentException("Invalid pass details");
        }

        this.visitorName = visitorName;
        this.validDate = validDate;
    }

    public boolean isValid(LocalDateTime entry, LocalDateTime exit) {
        // The pass covers one calendar day.
        return entry.toLocalDate().equals(validDate)
                && exit.toLocalDate().equals(validDate);
    }

    public String getVisitorName() {
        return visitorName;
    }
}

// ---------- Parking Ticket ----------
class ParkingTicket {
    private static int nextId = 1000;

    private final int ticketId;
    private final Vehicle vehicle;
    private final int floor;
    private final int slot;
    private final LocalDateTime entryTime;
    private final OneDayPass pass;

    ParkingTicket(Vehicle vehicle, int floor, int slot,
                  LocalDateTime entryTime, OneDayPass pass) {
        this.ticketId = ++nextId;
        this.vehicle = vehicle;
        this.floor = floor;
        this.slot = slot;
        this.entryTime = entryTime;
        this.pass = pass;
    }

    public int getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getFloor() {
        return floor;
    }

    public int getSlot() {
        return slot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public OneDayPass getPass() {
        return pass;
    }
}

// ---------- Parking Facility ----------
class ParkingFacility {
    private final String[][] slots;
    private final Map<String, ParkingTicket> activeTickets =
            new HashMap<>();

    ParkingFacility(int[] slotsPerFloor) {
        if (slotsPerFloor == null || slotsPerFloor.length == 0) {
            throw new IllegalArgumentException("Floors are required");
        }

        slots = new String[slotsPerFloor.length][];
        for (int i = 0; i < slotsPerFloor.length; i++) {
            if (slotsPerFloor[i] <= 0) {
                throw new IllegalArgumentException(
                        "Each floor needs at least one slot");
            }
            slots[i] = new String[slotsPerFloor[i]];
        }
    }

    public ParkingTicket park(Vehicle vehicle, LocalDateTime entry,
                             OneDayPass pass) {
        if (vehicle == null || entry == null) {
            throw new IllegalArgumentException(
                    "Vehicle and entry time are required");
        }

        String regNo = vehicle.getRegNo();
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid registration");
        }
        if (activeTickets.containsKey(regNo)) {
            throw new IllegalArgumentException(
                    "Vehicle is already parked: " + regNo);
        }

        for (int f = 0; f < slots.length; f++) {
            for (int s = 0; s < slots[f].length; s++) {
                if (slots[f][s] == null) {
                    slots[f][s] = regNo;
                    ParkingTicket ticket =
                            new ParkingTicket(vehicle, f, s, entry, pass);
                    activeTickets.put(regNo, ticket);
                    return ticket;
                }
            }
        }

        System.out.println("Parking full for " + regNo);
        return null;
    }

    public double leave(String regNo, LocalDateTime exit) {
        ParkingTicket ticket = activeTickets.get(regNo);

        if (ticket == null) {
            throw new IllegalArgumentException(
                    "No active ticket for " + regNo);
        }

        if (exit == null || exit.isBefore(ticket.getEntryTime())) {
            throw new IllegalArgumentException("Invalid exit time");
        }

        double fee;
        OneDayPass pass = ticket.getPass();

        if (pass != null && pass.isValid(ticket.getEntryTime(), exit)) {
            fee = 0;
        } else {
            fee = ticket.getVehicle().calculateFee(
                    ticket.getEntryTime(), exit);
        }

        slots[ticket.getFloor()][ticket.getSlot()] = null;
        activeTickets.remove(regNo);

        System.out.println("Ticket: " + ticket.getTicketId());
        System.out.println("Vehicle: " + regNo);
        System.out.println("Slot: Floor " + ticket.getFloor()
                + ", Slot " + ticket.getSlot());
        System.out.printf("Parking fee: Rs. %.2f%n", fee);

        return fee;
    }

    public void displaySlots() {
        System.out.println("\nParking Slot Status:");
        for (int f = 0; f < slots.length; f++) {
            int occupied = 0;
            for (String regNo : slots[f]) {
                if (regNo != null) {
                    occupied++;
                }
            }
            System.out.println("Floor " + f + ": "
                    + occupied + "/" + slots[f].length + " occupied");
        }
    }
}

// ---------- Main Program ----------
public class Main {
    public static void main(String[] args) {
        ParkingFacility parking =
                new ParkingFacility(new int[]{20, 30, 40, 50, 60});

        LocalDate date = LocalDate.of(2026, 10, 25);

        Vehicle car = new Car("TN01AB1234");
        Vehicle bike = new EBike("TN02CD5678");
        Vehicle van = new DeliveryVan("TN03EF9012");
        Vehicle visitorCar = new Car("TN04GH3456");

        OneDayPass visitorPass =
                new OneDayPass("Visitor", date);

        ParkingTicket t1 = parking.park(
                car, date.atTime(10, 0), null);
        ParkingTicket t2 = parking.park(
                bike, date.atTime(11, 0), null);
        ParkingTicket t3 = parking.park(
                van, date.atTime(10, 0), null);
        ParkingTicket t4 = parking.park(
                visitorCar, date.atTime(12, 0), visitorPass);

        parking.displaySlots();

        System.out.println("\n--- Vehicle Exit ---");
        double total = 0;

        total += parking.leave("TN01AB1234",
                date.atTime(12, 0)); // Car: 2 hours = Rs. 60

        total += parking.leave("TN02CD5678",
                date.atTime(14, 0)); // E-bike: flat Rs. 20

        total += parking.leave("TN03EF9012",
                date.atTime(12, 0)); // Van: 2 hours at double rate

        total += parking.leave("TN04GH3456",
                date.atTime(16, 0)); // Valid one-day pass: Rs. 0

        parking.displaySlots();
        System.out.printf("%nTotal collection: Rs. %.2f%n", total);
    }
}
