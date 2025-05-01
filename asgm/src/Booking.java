import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
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
    private Payment payment;
    private Guest guest;
    private RoomService roomService;
    static Queue<String> reusableBookingIDs = new LinkedList<>();
    private static int bookingCounter = 1;

    public Booking(String bookingID, Guest guest, Room roomType, String checkInDate, String checkOutDate, double totalPrice) {
        this.bookingID = bookingID;
        this.guest = guest;
        this.custName = guest.getName();
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.paymentStatus = "Pending";
        this.bookingStatus = "Confirmed";
        this.roomService = new RoomService(this);
    }

    public void callRoomService() { roomService.callRoomService(); }
    public void cancelRoomService() { roomService.cancelRoomService(); }
    public void orderBreakfast() { roomService.buyBreakfast(); }
    public void cancelBreakfast() { roomService.cancelBreakfast(); }
    public void addRoomServiceFeeToPayment() { roomService.addFeeToPayment(); }
    public void displayRoomServiceStatus() { roomService.displayServiceStatus(); }

    public Guest getGuest() { return guest; }
    public String getBookingID() { return bookingID; }
    public String getCustName() { return custName; }
    public Room getRoomType() { return roomType; }
    public String getCheckInDate() { return checkInDate; }
    public String getCheckOutDate() { return checkOutDate; }
    public double getTotalPrice() { return totalPrice; }
    public String getPaymentStatus() { return paymentStatus; }
    public String getBookingStatus() { return bookingStatus; }
    public Payment getPayment() { return payment; }
    public RoomService getRoomService() { return roomService; }

    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }
    public void setCheckInDate(String checkInDate) { this.checkInDate = checkInDate; }
    public void setCheckOutDate(String checkOutDate) { this.checkOutDate = checkOutDate; }
    public void setPayment(Payment payment) {
        this.payment = payment;
        this.roomService.connectPayment(payment);
    }

    public static void createBooking(Scanner scanner, ArrayList<Booking> bookingList, Guest guest, ArrayList<Room> roomList) {
        System.out.println("\n--- Create Booking ---");
    
        Room room = null;
        while (room == null) {
            System.out.println("\nSelect Room Type:");
            System.out.println("1. Single Room - RM 200 per night");
            System.out.println("2. Double Room - RM 350 per night");
            System.out.println("3. Deluxe Room - RM 500 per night");
            System.out.print("Enter your choice (1-3): ");
            int roomChoice = scanner.nextInt();
            scanner.nextLine();
    
            String roomType = null;
            switch (roomChoice) {
                case 1 -> roomType = "Single";
                case 2 -> roomType = "Double";
                case 3 -> roomType = "Deluxe";
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
            }
    
            for (Room r : roomList) {
                // Check if the room is available and clean
                if (r.getRoomType().equalsIgnoreCase(roomType) && r.isAvailable() && r.getCleanlinessStatus().equalsIgnoreCase("Clean")) {
                    room = r;
                    break;
                }
            }
    
            if (room == null) {
                System.out.println("No available and clean rooms of the selected type. Please choose a different type.");
                return;
            }
        }
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate checkInDate, checkOutDate;
    
        while (true) {
            System.out.print("Enter check-in date (DD-MM-YYYY): ");
            String checkInStr = scanner.nextLine();
            System.out.print("Enter check-out date (DD-MM-YYYY): ");
            String checkOutStr = scanner.nextLine();
    
            try {
                checkInDate = LocalDate.parse(checkInStr, formatter);
                checkOutDate = LocalDate.parse(checkOutStr, formatter);
                long daysBooked = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    
                if (daysBooked <= 0) {
                    System.out.println("Error: Check-out date must be after check-in date. Try again.");

                } else if (daysBooked > 7) {
                        System.out.println("Error: Cannot book more than 7 days.");
                       
                    
                } else {
                    String newBookingID = reusableBookingIDs.isEmpty() ? "B" + bookingCounter++ : reusableBookingIDs.poll();
                    double totalPrice = room.getPrice() * daysBooked;
                    Booking newBooking = new Booking(newBookingID, guest, room, checkInStr, checkOutStr, totalPrice);
                    bookingList.add(newBooking);
    
                    Payment newPayment = new Payment(newBooking.getTotalPrice(), 0, newBooking);
                    newBooking.setPayment(newPayment);
    
                    // Update room status to "Occupied"
                    room.setAvailable(false); // Reserve the room
                    room.setStatus("Occupied"); // Mark room as occupied
    
                    System.out.println("\nBooking created successfully!");
                    System.out.println("Booking ID: " + newBookingID);
                    System.out.println("Room Type: " + room.getRoomType());
                    return;
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use DD-MM-YYYY.");
            }
        }
    }

    public static void cancelBooking(Scanner scanner, ArrayList<Booking> bookingList) {
        System.out.println("\n--- Cancel Booking ---");
        System.out.print("Enter booking ID to cancel: ");
        String bookingID = scanner.nextLine();
    
        Booking bookingToCancel = null;
        for (Booking b : bookingList) {
            if (b.getBookingID().equals(bookingID)) {
                bookingToCancel = b;
                break;
            }
        }
    
        if (bookingToCancel != null) {
            // Check if the booking has already been checked in
            if (bookingToCancel.getBookingStatus().equalsIgnoreCase("Checked-In")) {
                System.out.println("Error: Booking cannot be canceled as it has already been checked in.");
                return;
            }
    
            // Refund the total price (room + room service) if the payment has been completed
            Payment payment = bookingToCancel.getPayment();
            if (payment != null && payment.getPaymentStatus().equalsIgnoreCase("Completed")) {
                double totalRefund = payment.getTotalPrice();
                Guest guest = bookingToCancel.getGuest();
                System.out.println("Refund of RM " + totalRefund + " (Room + Room Service) has been issued to " + guest.getName() + ".");
            }
    
            // Remove the booking and mark the room as available
            bookingList.remove(bookingToCancel);
            reusableBookingIDs.add(bookingID); // Add the canceled booking ID to the reusable pool
            bookingToCancel.getRoomType().setAvailable(true); // Mark the room as available
            bookingToCancel.getRoomType().setStatus("Available"); // Reset room status to "Available"
            System.out.println("Booking " + bookingID + " has been canceled.");
        } else {
            System.out.println("Booking ID not found.");
        }
    }

    public static void updateBooking(Scanner scanner, ArrayList<Booking> bookingList, Guest guest) {
        System.out.println("\n--- Update Booking ---");
        System.out.print("Enter booking ID to update: ");
        String bookingID = scanner.nextLine();

        for (Booking booking : bookingList) {
            if (booking.getBookingID().equals(bookingID)) {
                if (!booking.getGuest().equals(guest)) { // Validate ownership
                    System.out.println("Error: You can only update your own bookings.");
                    return;
                }
    
                // Proceed with updating the booking
                System.out.print("Enter new check-in date (DD-MM-YYYY): ");
                String newCheckIn = scanner.nextLine();
                System.out.print("Enter new check-out date (DD-MM-YYYY): ");
                String newCheckOut = scanner.nextLine();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate newCheckInDate = LocalDate.parse(newCheckIn, formatter);
                LocalDate newCheckOutDate = LocalDate.parse(newCheckOut, formatter);
                long newDaysBooked = ChronoUnit.DAYS.between(newCheckInDate, newCheckOutDate);

                if (newDaysBooked <= 0) {
                    System.out.println("Error: Check-out date must be after check-in date.");
                    return;
                }

                if (newDaysBooked > 7) {
                    System.out.println("Error: Cannot book more than 7 days.");
                    return;
                }

                booking.setCheckInDate(newCheckIn);
                booking.setCheckOutDate(newCheckOut);
                booking.totalPrice = booking.getRoomType().getPrice() * newDaysBooked;
                System.out.println("Booking " + bookingID + " updated successfully.");
                return;
            }
        }
        System.out.println("Booking ID not found.");
    }

    public static void viewAllBookings(ArrayList<Booking> bookingList, Guest guest) {
        System.out.println("\n--- Your Bookings ---");
        boolean hasBookings = false;
    
        for (Booking booking : bookingList) {
            if (booking.getGuest().equals(guest)) { // Only display bookings for the logged-in guest
                booking.displayBookingDetail();
                System.out.println("--------------------------");
                hasBookings = true;
            }
        }
    
        if (!hasBookings) {
            System.out.println("No bookings found.");
        }
    }

    public void displayBookingDetail() {
        System.out.println("Booking ID: " + bookingID);
        System.out.println("Customer Name: " + custName);
        System.out.println("Room Type: " + roomType.getRoomType());
        System.out.println("Check-In Date: " + checkInDate);
        System.out.println("Check-Out Date: " + checkOutDate);
        System.out.println("Total Price: RM " + totalPrice);
        System.out.println("Payment Status: " + paymentStatus);
        System.out.println("Booking Status: " + bookingStatus);
    }

    // ✅ Fixed checkIn logic
    public void checkIn() {
        if (!this.bookingStatus.equalsIgnoreCase("Confirmed")) {
            System.out.println("Error: Booking is not in a state to be checked in.");
            return;
        }
    
        this.bookingStatus = "Checked-In"; // Update booking status
        Room room = this.roomType; // Get the associated room
        room.setAvailable(false); // Mark the room as unavailable
        room.setStatus("Inhouse"); // Update room status to "Inhouse"
    
        System.out.println("Room " + room.getRoomId() + " has been checked in. Status updated to Inhouse.");
        System.out.println("Booking " + this.bookingID + " has been checked in.");
    }
    public void checkOut() {
        Room room = this.roomType; // Get the associated room
    
        if (!room.isAvailable()) {
            room.setAvailable(true); // Mark the room as available
            room.setCleanlinessStatus("Dirty"); // Auto-set as dirty after checkout
            room.setStatus("Checked out"); // Update room status to "Checked out"
            this.bookingStatus = "Checked-Out"; // Update booking status
    
            System.out.println("Room " + room.getRoomId() + " has been checked out. Room marked as Dirty.");
            System.out.println("Booking " + this.bookingID + " has been checked out.");
        } else {
            System.out.println("Room " + room.getRoomId() + " is already available.");
        }
    }

    public static ArrayList<Booking> getBookingsForGuest(Guest guest, ArrayList<Booking> bookingList) {
        ArrayList<Booking> guestBookings = new ArrayList<>();
        for (Booking booking : bookingList) {
            if (booking.getCustName().equals(guest.getName())) {
                guestBookings.add(booking);
            }
        }
        return guestBookings;
    }
}
