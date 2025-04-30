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

        System.out.print("Select a booking to check in (1-" + eligible.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > eligible.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Booking selected = eligible.get(choice - 1);
        selected.checkIn();
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

        System.out.print("Select a booking to check out (1-" + eligible.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

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
        report.generateGuestReport();
    }

    public void viewAllRoomStatus() {
        System.out.println("\n--- All Room Status ---");

        if (Main.roomList.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        for (Room room : Main.roomList) {
            String roomStatus = "Available";

            Booking foundBooking = null;
            for (Booking booking : Main.bookingList) {
                if (booking.getRoomType().getRoomId().equals(room.getRoomId())) {
                    foundBooking = booking;
                }
            }

            if (foundBooking != null) {
                String bStatus = foundBooking.getBookingStatus();
                if (bStatus.equalsIgnoreCase("Checked-In")) {
                    roomStatus = "Inhouse";
                } else if (bStatus.equalsIgnoreCase("Checked-Out")) {
                    roomStatus = "Checked out";
                }
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
            System.out.printf("%d. Room ID: %s | Type: %s | Cleanliness: %s\n",
                    i + 1, room.getRoomId(), room.getRoomType(), room.getCleanlinessStatus());
        }

        System.out.print("Select a room to update cleanliness (1-" + Main.roomList.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > Main.roomList.size()) {
            System.out.println("Invalid room selection.");
            return;
        }

        Room selectedRoom = Main.roomList.get(choice - 1);

        System.out.print("Set cleanliness status (Clean / Dirty): ");
        String status = scanner.nextLine();

        if (status.equalsIgnoreCase("Clean") || status.equalsIgnoreCase("Dirty")) {
            selectedRoom.setCleanlinessStatus(status);
            System.out.println("Room cleanliness updated.");
        } else {
            System.out.println("Invalid status. Please enter 'Clean' or 'Dirty'.");
        }
    }

    public void updateStaffProfile() {
        System.out.println(getName() + " has updated their staff profile.");
    }

    private Booking findBookingByID(String bookingID) {
        for (Booking booking : Main.bookingList) {
            if (booking.getBookingID().equals(bookingID)) {
                return booking;
            }
        }
        return null;
    }
}
