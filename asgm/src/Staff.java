//Author : Trisha Mae Low Kar Mun
//Module : Check in and Check out (Staff) Management
//System : Hotel Management System
//Group  : DFT1G11

import java.util.ArrayList;
import java.util.Scanner;

public class Staff extends Account {

    private Report report;

    public Staff() {
        super();
        this.report = new Report(Main.bookingList);
    }

    public Staff(String name, String password, String birthday, String IC, String phoneNo, String email) {
        super(name, password, birthday, IC, phoneNo, email);
        this.report = new Report(Main.bookingList);
    }

    @Override
    public void displayAccountType() {
        System.out.println("Account Type: Staff");
    }

    public void checkInRoom(Scanner scanner) {
        System.out.println("\n--- Check-In Room ---");

        ArrayList<Booking> eligible = new ArrayList<>();
        for (Booking b : Main.bookingList) {
            if (b.getBookingStatus().equalsIgnoreCase("Confirmed")) {
                eligible.add(b);
            }
        }

        if (eligible.isEmpty()) {
            System.out.println("No confirmed bookings available for check-in.");
            return;
        }

        for (int i = 0; i < eligible.size(); i++) {
            Booking b = eligible.get(i);
            System.out.printf("%d. Booking ID: %s | Guest: %s | Room: %s | Status: %s\n",
                    i + 1, b.getBookingID(), b.getCustName(), b.getRoomType().getRoomType(), b.getBookingStatus());
        }

        int choice = -1;
        while (true) {
            System.out.print("Select a booking to check in (1-" + eligible.size() + "): ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= eligible.size())
                    break;
                else
                    System.out.println("Invalid range. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }

        if (choice < 1 || choice > eligible.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Booking selected = eligible.get(choice - 1);

        Payment payment = selected.getPayment();
        if (payment == null || !payment.getPaymentStatus().equalsIgnoreCase("Completed")) {
            System.out.println("Error: Payment for this booking has not been completed. Check-in denied.");
            return;
        }
        selected.checkIn();
        System.out.println("Check-in successful for Booking ID: " + selected.getBookingID());

    }

    public void checkOutRoom(Scanner scanner) {
        System.out.println("\n--- Check-Out Room ---");

        ArrayList<Booking> eligible = new ArrayList<>();
        for (Booking b : Main.bookingList) {
            if (b.getBookingStatus().equalsIgnoreCase("Checked-In")) {
                eligible.add(b);
            }
        }

        if (eligible.isEmpty()) {
            System.out.println("No checked-in bookings available for check-out.");
            return;
        }

        for (int i = 0; i < eligible.size(); i++) {
            Booking b = eligible.get(i);
            System.out.printf("%d. Booking ID: %s | Guest: %s | Room: %s | Status: %s\n",
                    i + 1, b.getBookingID(), b.getCustName(), b.getRoomType().getRoomType(), b.getBookingStatus());
        }

        int choice = -1;
        while (true) {
            System.out.print("Select a booking to check in (1-" + eligible.size() + "): ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= eligible.size())
                    break;
                else
                    System.out.println("Invalid range. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }

        if (choice < 1 || choice > eligible.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Booking selected = eligible.get(choice - 1);
        selected.checkOut();
    }

    public void generateReport() {
        System.out.println(getName() + " is generating a report.");
        report.generateDailyReport();
        report.generateRoomTypeAnalysis();
        report.generateGuestReport(Main.paymentList);
    }

    public void viewAllRoomStatus() {
        System.out.println("\n--- All Room Status ---");

        if (Main.roomList.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        for (Room room : Main.roomList) {
            String roomStatus = room.getStatus(); // Start with the room's actual status

            // Check if the room is associated with any booking
            boolean isAssociatedWithBooking = false;
            for (Booking booking : Main.bookingList) {
                if (booking.getRoomType().getRoomId().equals(room.getRoomId())) {
                    if (booking.getBookingStatus().equalsIgnoreCase("Confirmed")) {
                        roomStatus = "Occupied"; // Room is booked but not yet checked in
                    } else if (booking.getBookingStatus().equalsIgnoreCase("Checked-In")) {
                        roomStatus = "Inhouse"; // Room is currently occupied by a guest
                    }
                    break;
                }
            }

            // If the room is clean and available, override the status to "Available"
            if (!isAssociatedWithBooking && room.getCleanlinessStatus().equalsIgnoreCase("Clean")
                    && room.isAvailable()) {
                roomStatus = "Available";
            }

            System.out.printf("Room ID: %s | Type: %s | Status: %s | Cleanliness: %s\n",
                    room.getRoomId(),
                    room.getRoomType(),
                    roomStatus,
                    room.getCleanlinessStatus());
        }
    }

    public void updateRoomCleanliness(Scanner scanner) {
        System.out.println("\n--- Update Room Cleanliness ---");

        if (Main.roomList.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        for (int i = 0; i < Main.roomList.size(); i++) {
            Room room = Main.roomList.get(i);
            System.out.printf("%d. Room ID: %s | Type: %s | Status: %s | Cleanliness: %s\n",
                    i + 1, room.getRoomId(), room.getRoomType(), room.getStatus(), room.getCleanlinessStatus());
        }

        int choice = -1;
        while (true) {
            System.out.print("Select a room to update cleanliness (1-" + Main.roomList.size() + "): ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= Main.roomList.size())
                    break;
                else
                    System.out.println("Invalid room number. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }

        if (choice < 1 || choice > Main.roomList.size()) {
            System.out.println("Invalid room selection.");
            return;
        }

        Room selectedRoom = Main.roomList.get(choice - 1);

        System.out.print("Set cleanliness status (Clean / Dirty): ");
        String status = scanner.nextLine();

        if (status.equalsIgnoreCase("Clean")) {
            selectedRoom.setCleanlinessStatus("Clean");
            selectedRoom.setAvailable(true); // Mark the room as available
            selectedRoom.setStatus("Available"); // Reset room status to "Available"
            System.out.println("Room cleanliness updated to Clean. Room is now Available.");
        } else if (status.equalsIgnoreCase("Dirty")) {
            selectedRoom.setCleanlinessStatus("Dirty");
            selectedRoom.setAvailable(false); // Mark the room as unavailable
            selectedRoom.setStatus("Unavailable"); // Update status to "Dirty"
            System.out.println("Room cleanliness updated to Dirty.");
        } else {
            System.out.println("Invalid status. Please enter 'Clean' or 'Dirty'.");
        }
    }

}
