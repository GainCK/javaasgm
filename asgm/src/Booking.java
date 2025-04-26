import java.util.ArrayList;
import java.util.Scanner;

public class Booking {
    private String bookingID;
    private String custName;
    private Room roomType;
    private String checkInDate;
    private String checkOutDate;
    private double totalPrice;
    private String paymentStatus;
    private String bookingStatus;
    private static int bookingCounter = 1; // Auto-increment counter

    public Booking(String bookingID, String custName, Room roomType, String checkInDate, String checkOutDate) {
        this.bookingID = bookingID;
        this.custName = custName;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = roomType.getPrice();
        this.paymentStatus = "Pending";
        this.bookingStatus = "Confirmed";
    }

    // Getters
    public String getBookingID() { return bookingID; }
    public String getCustName() { return custName; }
    public Room getRoomType() { return roomType; }
    public String getCheckInDate() { return checkInDate; }
    public String getCheckOutDate() { return checkOutDate; }
    public double getTotalPrice() { return totalPrice; }
    public String getPaymentStatus() { return paymentStatus; }
    public String getBookingStatus() { return bookingStatus; }

    // Setters
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }
    public void setCheckInDate(String checkInDate) { this.checkInDate = checkInDate; }
    public void setCheckOutDate(String checkOutDate) { this.checkOutDate = checkOutDate; }

    // === Booking Functions ===

    // Create Booking (user input)
    public static void createBooking(Scanner scanner, ArrayList<Booking> bookingList) {
        System.out.println("\n--- Create Booking ---");
        System.out.print("Enter customer name: ");
        String custName = scanner.nextLine();

        // For simplicity, hardcoding a Room. You can change this later to select Room.
        Room room = new Room("Standard Room", 100.0); // Example room
        System.out.println("Room selected: " + room.getRoomType() + " | Price: " + room.getPrice());

        System.out.print("Enter check-in date (DD-MM-YYYY): ");
        String checkInDate = scanner.nextLine();
        System.out.print("Enter check-out date (DD-MM-YYYY): ");
        String checkOutDate = scanner.nextLine();

        String newBookingID = "B" + bookingCounter++;
        Booking newBooking = new Booking(newBookingID, custName, room, checkInDate, checkOutDate);
        bookingList.add(newBooking);

        System.out.println("Booking created successfully with ID: " + newBookingID);
    }

    // Cancel Booking
    public static void cancelBooking(Scanner scanner, ArrayList<Booking> bookingList) {
        System.out.println("\n--- Cancel Booking ---");
        System.out.print("Enter booking ID to cancel: ");
        String bookingID = scanner.nextLine();

        boolean found = bookingList.removeIf(b -> b.getBookingID().equals(bookingID));
        if (found) {
            System.out.println("Booking " + bookingID + " has been cancelled.");
        } else {
            System.out.println("Booking ID not found.");
        }
    }

    // Update Booking
    public static void updateBooking(Scanner scanner, ArrayList<Booking> bookingList) {
        System.out.println("\n--- Update Booking ---");
        System.out.print("Enter booking ID to update: ");
        String bookingID = scanner.nextLine();

        for (Booking booking : bookingList) {
            if (booking.getBookingID().equals(bookingID)) {
                System.out.print("Enter new check-in date (DD-MM-YYYY): ");
                String newCheckIn = scanner.nextLine();
                System.out.print("Enter new check-out date (DD-MM-YYYY): ");
                String newCheckOut = scanner.nextLine();

                booking.setCheckInDate(newCheckIn);
                booking.setCheckOutDate(newCheckOut);
                System.out.println("Booking " + bookingID + " updated successfully.");
                return;
            }
        }
        System.out.println("Booking ID not found.");
    }

    // Display All Bookings
    public static void viewAllBookings(ArrayList<Booking> bookingList) {
        System.out.println("\n--- All Bookings ---");
        if (bookingList.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Booking booking : bookingList) {
                booking.displayBookingDetail();
                System.out.println("--------------------------");
            }
        }
    }

    // Display single booking detail
    public void displayBookingDetail() {
        System.out.println("Booking ID: " + bookingID);
        System.out.println("Customer Name: " + custName);
        System.out.println("Room Type: " + roomType.getRoomType());
        System.out.println("Check-In Date: " + checkInDate);
        System.out.println("Check-Out Date: " + checkOutDate);
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Payment Status: " + paymentStatus);
        System.out.println("Booking Status: " + bookingStatus);
    }
}
