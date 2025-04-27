import java.util.Scanner;

public class Staff extends Account {

    // Constructors
    public Staff() {
        super();
    }

    public Staff(String name, String password, String birthday, String IC, String phoneNo, String email) {
        super(name, password, birthday, IC, phoneNo, email);
    }

    // Staff-specific methods
    public void checkInRoom(Scanner scanner) {
        System.out.println(getName() + " is checking in a room.");
        System.out.print("Enter Booking ID to check in: ");
        String bookingID = scanner.nextLine();
    
        // Find the booking with the given ID
        Booking booking = findBookingByID(bookingID);
        if (booking == null) {
            System.out.println("Booking not found!");
            return;
        }
    
        // Display booking details before checking in
        System.out.println("Booking Details: ");
        System.out.println("Booking ID: " + booking.getBookingID() + " | Guest Name: " + booking.getCustName() + " | Room Type: " + booking.getRoomType());
    
        // Ensure the room is available for check-in
        if (booking.getBookingStatus().equals("Confirmed")) {
            booking.setBookingStatus("Checked In");
            booking.getRoomType().setAvailable(false); // Set room to not available (Occupied)
            System.out.println("Guest checked in successfully!");
        } else {
            System.out.println("Booking is already checked in or cancelled.");
        }
    }
    public void checkOutRoom(Scanner scanner) {
        System.out.println(getName() + " is checking out a room.");
        System.out.print("Enter Booking ID to check out: ");
        String bookingID = scanner.nextLine();

        // Find the booking with the given ID
        Booking booking = findBookingByID(bookingID);
        if (booking == null) {
            System.out.println("Booking not found!");
            return;
        }

        // Ensure the guest is checked in before checking out
        if (booking.getBookingStatus().equals("Checked In")) {
            booking.setBookingStatus("Checked Out");
            booking.getRoomType().setAvailable(true); // Set room to available (Available)
            System.out.println("Guest checked out successfully!");
        } else {
            System.out.println("Booking is not checked in yet.");
        }
    }

    public void generateReport() {
        System.out.println(getName() + " is generating a report.");
    }

    public void updateStaffProfile() {
        System.out.println(getName() + " has updated their staff profile.");
    }

    // Utility method to find booking by booking ID
    private Booking findBookingByID(String bookingID) {
        for (Booking booking : Main.bookingList) {
            if (booking.getBookingID().equals(bookingID)) {
                return booking;
            }
        }
        return null;
    }
}
