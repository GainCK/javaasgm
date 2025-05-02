//Author : Chuh Zhe Yong / Gain Chen Keat / Trisha Mae Low Kar Mun
//Module : Menu Management
//System : Hotel Management System
//Group  : DFT1G11

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Main {
    // Declare globally so all methods can use it
    static final String[] ROOM_TYPES = { "Single", "Double", "Deluxe" };
    static final int[] ROOM_PRICES = { 200, 350, 500 };
    static final int ROOM_COUNT = 3;
    static ArrayList<Booking> bookingList = new ArrayList<>();
    static ArrayList<Room> roomList = new ArrayList<>();
    static ArrayList<Payment> paymentList = new ArrayList<>();

    public static void main(String[] args) {
        initializeRooms();
        Scanner scanner = new Scanner(System.in);

        // Main Menu Loop
        while (true) {
            clearScreen();
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number (1-3).");
                continue; // back to the top of the loop
            }

            switch (choice) {
                case 1:
                    Account.startRegistration(scanner);
                    break;

                case 2:
                    Account.login(scanner);
                    break;

                case 3:
                    System.out.println("Exiting program. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice! Please select again.");
                    continue;
            }
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    //  menu
    public static void staffMenu(Scanner scanner, Staff staff) {
        while (true) {
            System.out.println("\n=== Staff Menu ===");
            System.out.println("1. Check In Room");
            System.out.println("2. Check Out Room");
            System.out.println("3. View All Room Status");
            System.out.println("4. Housekeeping");
            System.out.println("5. Generate Report");
            System.out.println("6. Update Profile");
            System.out.println("7. Logout");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number (1-5).");
                continue;
            }

            switch (choice) {
                case 1:
                    staff.checkInRoom(scanner);
                    break;
                case 2:
                    staff.checkOutRoom(scanner);
                    break;
                case 3:
                    staff.viewAllRoomStatus();
                    break;
                case 4:
                    staff.updateRoomCleanliness(scanner);
                    break;
                case 5:
                    staff.generateReport();
                    break;
                case 6:
                    Account.updateProfile(scanner, staff);
                    break;
                case 7:
                    staff.logout();
                    return;
                default:
                    System.out.println("Invalid choice! Please select again.");
            }
        }
    }

    // Guest menu
    public static void guestMenu(Scanner scanner, Guest guest) {
        while (true) {
            System.out.println("\n=== Guest Menu ===");
            System.out.println("1. Update Profile");
            System.out.println("2. Booking Menu");
            System.out.println("3. Room Services");
            System.out.println("4. Payment Menu");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number (1-5).");
                continue;
            }

            switch (choice) {
                case 1:
                    Account.updateProfile(scanner, guest);
                    break;
                case 2:
                    bookingMenu(scanner, guest); // Booking menu only accessible for guests
                    break;

                case 3:
                    // Fetch bookings for the logged-in guest
                    List<Booking> bookings = getBookingsForGuest(guest);
                    if (bookings.isEmpty()) {
                        System.out.println("No bookings found. Please create a booking first.");
                        break;
                    }
                    // Allow guest to select a booking for room service
                    Booking selectedBooking = promptUserToSelectBooking(scanner, bookings);
                    roomServiceMenu(scanner, selectedBooking); // Pass selected booking here
                    break;

                case 4:
                    paymentMenu(scanner, guest); // Payment menu only accessible for guests
                    break;
                case 5:
                    guest.logout();
                    return;
                default:
                    System.out.println("Invalid choice! Please select again.");
                    continue;
            }
        }
    }

    public static void initializeRooms() {
        for (int i = 1; i <= ROOM_COUNT; i++) {
            for (int j = 0; j < ROOM_TYPES.length; j++) {
                String roomID = ROOM_TYPES[j].substring(0, 1) + (j == 2 ? "L" : "") + i; // S1, D1, DL1
                roomList.add(new Room(ROOM_TYPES[j], ROOM_PRICES[j], roomID));
            }
        }
    }

    // Booking menu (Guest only)
    public static void bookingMenu(Scanner scanner, Guest guest) {
        while (true) {
            System.out.println("\n=== Booking Menu ===");
            System.out.println("1. Create Booking");
            System.out.println("2. Update Booking");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View All Bookings");
            System.out.println("5. Back to Guest Menu");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number (1-5).");
                continue;
            }

            switch (choice) {
                case 1:
                    Booking.createBooking(scanner, bookingList, guest, roomList);
                    break;
                case 2:
                    Booking.updateBooking(scanner, bookingList, guest);
                    break;
                case 3:
                    Booking.cancelBooking(scanner, bookingList);
                    break;
                case 4:
                    Booking.viewAllBookings(bookingList, guest);
                    break;
                case 5:
                    return; // Back to Guest Menu
                default:
                    System.out.println("Invalid choice!");
                    continue;
            }
        }
    }

    public static void paymentMenu(Scanner scanner, Guest guest) {
        while (true) {
            System.out.println("\n=== Payment Menu ===");
            System.out.println("1. Process Payment");
            System.out.println("2. View Payments History");
            System.out.println("3. Back to Guest Menu");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number (1-3).");
                continue;
            }

            switch (choice) {
                case 1:
                    // Ensure the logged-in user has bookings to pay for
                    List<Booking> guestBookings = getBookingsForGuest(guest);
                    if (guestBookings.isEmpty()) {
                        System.out.println("You have no bookings to pay for. Please create a booking first.");
                        break;
                    }

                    System.out.println("\nYour Bookings:");
                    for (Booking b : guestBookings) {
                        System.out.println("- Booking ID: " + b.getBookingID());
                    }

                    // Process payment for a booking tied to the logged-in guest
                    System.out.print("Enter Booking ID to pay for: ");
                    String bid = scanner.nextLine();
                    Booking toPay = null;
                    for (Booking b : guestBookings) { // Only check the logged-in guest's bookings
                        if (b.getBookingID().equalsIgnoreCase(bid)) {
                            toPay = b;
                            break;
                        }
                    }
                    if (toPay == null) {
                        System.out.println("Booking ID not found.");
                        break;
                    }
                    // Check if the booking already has a payment
                    Payment existingPayment = toPay.getPayment();
                    if (existingPayment != null && existingPayment.getPaymentStatus().equalsIgnoreCase("Completed")) {
                        System.out.println("This booking has already been paid. Payment cannot be made again.");
                        return;
                    }

                    // Proceed if payment hasn't been made or is still pending
                    Payment newPayment = new Payment(0.0, 0, toPay);
                    toPay.setPayment(newPayment); // update booking
                    newPayment.processPayment(scanner, paymentList);
                    paymentList.add(newPayment);
                    break;

                case 2:
                    new Payment().viewPaymentsHistory(paymentList);
                    break;

                case 3:
                    return; // Back to Guest Menu
                default:
                    System.out.println("Invalid choice!");
                    continue;
            }
        }
    }

    // Modified Room Service menu
    public static void roomServiceMenu(Scanner scanner, Booking booking) {
        if (booking == null || booking.getPayment() == null) {
            System.out.println("No booking or payment found. Cannot access Room Service Menu.");
            return;
        }

        Payment payment = booking.getPayment();
        if (payment.getPaymentStatus().equalsIgnoreCase("Completed")) {
            System.out.println("Payment is already completed. Room service options are no longer available.");
            return;
        }

        RoomService roomService = booking.getRoomService(); // Get existing RoomService object

        while (true) {
            System.out.println("\n=== Room Service Menu ===");
            System.out.println("1. Order Room Service");
            System.out.println("2. Cancel Room Service");
            System.out.println("3. Order Breakfast");
            System.out.println("4. Cancel Breakfast");
            System.out.println("5. View Room Service Status");
            System.out.println("6. Back to Guest Menu");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number (1-6).");
                continue;
            }

            switch (choice) {
                case 1:
                    roomService.callRoomService();
                    break;
                case 2:
                    roomService.cancelRoomService();
                    break;
                case 3:
                    roomService.buyBreakfast();
                    break;
                case 4:
                    roomService.cancelBreakfast();
                    break;
                case 5:
                    roomService.displayServiceStatus();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice!");
                    continue;
            }
        }
    }

    // Helper method to allow guest to select a booking
    public static Booking promptUserToSelectBooking(Scanner scanner, List<Booking> bookings) {
        System.out.println("Select a booking (1-" + bookings.size() + "):");
        for (int i = 0; i < bookings.size(); i++) {
            System.out.println((i + 1) + ". " + bookings.get(i).getBookingID());
        }
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                continue;
            }
            // Validate the user's choice
            if (choice >= 1 && choice <= bookings.size()) {
                break; // Valid choice
            } else {
                System.out.println("Invalid choice! Please select a number between 1 and " + bookings.size() + ".");
            }
        }

        return bookings.get(choice - 1); // Return the selected booking
    }

    // Method to fetch bookings for the guest (for simplicity, it's a stub)
    public static List<Booking> getBookingsForGuest(Guest guest) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : bookingList) {
            if (b.getGuest().equals(guest)) {
                result.add(b);
            }
        }
        return result;
    }
}
