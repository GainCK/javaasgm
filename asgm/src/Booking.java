import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    private Payment payment;
    private Guest guest;
    private RoomService roomService; // Will be linked properly
    private static int bookingCounter = 1;

    public Booking(String bookingID, Guest guest, Room roomType, String checkInDate, String checkOutDate,
            double totalPrice) {
        this.bookingID = bookingID;
        this.guest = guest;
        this.custName = guest.getName(); // keep custName in sync for backward compatibility
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice; // use calculated price
        this.paymentStatus = "Pending";
        this.bookingStatus = "Confirmed";
        this.roomService = new RoomService(this);
    }

    // Call room service for this booking
    public void callRoomService() {
        roomService.callRoomService();
    }

    public void cancelRoomService() {
        roomService.cancelRoomService();
    }

    public void orderBreakfast() {
        roomService.buyBreakfast();
    }

    public void cancelBreakfast() {
        roomService.cancelBreakfast();
    }

    public void addRoomServiceFeeToPayment() {
        roomService.addFeeToPayment();
    }

    public void displayRoomServiceStatus() {
        roomService.displayServiceStatus();
    }

    // Getters and Setters

    public Guest getGuest() {
        return guest;
    }

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

    public RoomService getRoomService() {
        return roomService;
    }

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
        this.roomService.connectPayment(payment); // Connect the payment to RoomService
    }

    // Booking Operations

  


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
            scanner.nextLine(); // consume newline
    
            String roomType = null;
            switch (roomChoice) {
                case 1:
                    roomType = "Single";
                    break;
                case 2:
                    roomType = "Double";
                    break;
                case 3:
                    roomType = "Deluxe";
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }
    
            // Find an available room of the selected type
            for (Room r : roomList) {
                if (r.getRoomType().equalsIgnoreCase(roomType) && r.isAvailable()) {
                    room = r;
                    break;
                }
            }
    
            if (room == null) {
                System.out.println("No available rooms of the selected type. Please choose a different type.");
                return;
            }
        }
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate checkInDate = null;
        LocalDate checkOutDate = null;
    
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
                } else {
                    double totalPrice = room.getPrice() * daysBooked;
                    String newBookingID = "B" + bookingCounter++;
    
                    Booking newBooking = new Booking(newBookingID, guest, room, checkInStr, checkOutStr, totalPrice);
                    bookingList.add(newBooking);
    
                    Payment newPayment = new Payment(newBooking.getTotalPrice(), 0, newBooking);
                    newBooking.setPayment(newPayment);
    
                    // Mark the room as unavailable
                    room.setAvailable(false);
    
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

        boolean found = bookingList.removeIf(b -> b.getBookingID().equals(bookingID));
        if (found) {
            System.out.println("Booking " + bookingID + " has been cancelled.");
        } else {
            System.out.println("Booking ID not found.");
        }
    }

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

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate newCheckInDate = LocalDate.parse(newCheckIn, formatter);
                LocalDate newCheckOutDate = LocalDate.parse(newCheckOut, formatter);
                long newDaysBooked = ChronoUnit.DAYS.between(newCheckInDate, newCheckOutDate);

                if (newDaysBooked <= 0) {
                    System.out.println("Error: Check-out date must be after check-in date.");
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

    public void checkIn() {
        if (roomType.isAvailable()) {
            roomType.checkIn();
            bookingStatus = "Checked-In";
            System.out.println("Booking " + bookingID + " has been checked in.");
        } else {
            System.out.println("Room " + roomType.getRoomId() + " is not available for check-in.");
        }
    }

    public void checkOut() {
        if (!roomType.isAvailable()) {
            roomType.checkOut();
            bookingStatus = "Checked-Out";
            System.out.println("Booking " + bookingID + " has been checked out.");
        } else {
            System.out.println("Room " + roomType.getRoomId() + " is already available.");
        }
    }

    // New method to get bookings for a guest
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
