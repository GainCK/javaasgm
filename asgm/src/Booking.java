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
    private Payment payment; // New field to store payment
    private RoomService roomService; // New field to store room service
    private static int bookingCounter = 1;

    public Booking(String bookingID, String custName, Room roomType, String checkInDate, String checkOutDate) {
        this.bookingID = bookingID;
        this.custName = custName;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = roomType.getPrice();
        this.paymentStatus = "Pending";
        this.bookingStatus = "Confirmed";
        this.payment = new Payment(0.0, 0, this); // Initialize Payment object with zero amount and payment method 0
         
        //this.roomService = new RoomService(new Guest(custName), roomType, 0.0);
    }

    // Call room service for this booking
    public void callRoomService() {
        roomService.callRoomService();
    }

    // Cancel room service for this booking
    public void cancelRoomService() {
        roomService.cancelRoomService();
    }

    // Order breakfast for the booking
    public void orderBreakfast() {
        roomService.buyBreakfast();
    }

    // Cancel breakfast for the booking
    public void cancelBreakfast() {
        roomService.cancelBreakfast();
    }

    // Add room service fee to payment
    public void addRoomServiceFeeToPayment() {
        roomService.addFeeToPayment();
    }

    // Display room service status for this booking
    public void displayRoomServiceStatus() {
        roomService.displayServiceStatus();
    }
    
    // Getters
    public String getBookingID() {
        return bookingID;
    }

    public String getCustName() {
        return custName;
    }

    public Room getRoomType() {
        return roomType;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public Payment getPayment() {
        return payment;
    }

    // Setters
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    // === Booking Functions ===

    // Create Booking (with room selection and payment)
    public static void createBooking(Scanner scanner, ArrayList<Booking> bookingList) {
        System.out.println("\n--- Create Booking ---");
        System.out.print("Enter customer name: ");
        String custName = scanner.nextLine();

        // Show room options
        System.out.println("\nSelect Room Type:");
        System.out.println("1. Single Room - RM 200 per night");
        System.out.println("2. Double Room - RM 350 per night");
        System.out.println("3. Deluxe Room - RM 500 per night");
        System.out.print("Enter your choice (1-3): ");
        int roomChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Room room;
        switch (roomChoice) {
            case 1:
                room = new Room("Single", 200);
                break;
            case 2:
                room = new Room("Double", 350);
                break;
            case 3:
                room = new Room("Deluxe", 500);
                break;
            default:
                System.out.println("Invalid choice! Defaulting to Single Room.");
                room = new Room("Single", 200);
        }

        System.out.print("Enter check-in date (DD-MM-YYYY): ");
        String checkInDate = scanner.nextLine();
        System.out.print("Enter check-out date (DD-MM-YYYY): ");
        String checkOutDate = scanner.nextLine();

        String newBookingID = "B" + bookingCounter++;
        Booking newBooking = new Booking(newBookingID, custName, room, checkInDate, checkOutDate);
        bookingList.add(newBooking);

        // Link a payment object to the newly created booking
        Payment newPayment = new Payment(newBooking.getTotalPrice(), 0, newBooking);
        newBooking.setPayment(newPayment);

        System.out.println("\nBooking created successfully!");
        System.out.println("Booking ID: " + newBookingID);
        System.out.println("Room Type: " + room.getRoomType());
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
        System.out.println("Room Type: " + roomType.getRoomType());  // Correct usage
        System.out.println("Check-In Date: " + checkInDate);
        System.out.println("Check-Out Date: " + checkOutDate);
        System.out.println("Total Price: RM " + totalPrice);
        System.out.println("Payment Status: " + paymentStatus);
        System.out.println("Booking Status: " + bookingStatus);
    }

    // Check-In for the booking (Set room status to occupied)
    public void checkIn() {
        if (roomType.isAvailable()) {
            roomType.checkIn();
            bookingStatus = "Checked-In";
            System.out.println("Booking " + bookingID + " has been checked in.");
        } else {
            System.out.println("Room " + roomType.getRoomId() + " is not available for check-in.");
        }
    }

    // Check-Out for the booking (Set room status to available)
    public void checkOut() {
        if (!roomType.isAvailable()) {
            roomType.checkOut();
            bookingStatus = "Checked-Out";
            System.out.println("Booking " + bookingID + " has been checked out.");
        } else {
            System.out.println("Room " + roomType.getRoomId() + " is already available.");
        }
    }
}