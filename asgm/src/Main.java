import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // Declare globally so all methods can use it
    static ArrayList<Booking> bookingList = new ArrayList<>();
    static ArrayList<Room> roomList = new ArrayList<>();
    static ArrayList<Payment> paymentList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        // Main Menu Loop
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    Account.register(scanner);
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
            }
        }
    }

    // Staff menu
    public static void staffMenu(Scanner scanner, Staff staff) {
        while (true) {
            System.out.println("\n=== Staff Menu ===");
            System.out.println("1. Check In Room");
            System.out.println("2. Check Out Room");
            System.out.println("3. Generate Report");
            System.out.println("4. Update Profile");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    staff.checkInRoom(scanner);
                    break;
                case 2:
                    staff.checkOutRoom(scanner);
                    break;
                case 3:
                    staff.generateReport();
                    break;
                case 4:
                    Account.updateProfile(scanner, staff);
                    break;
                case 5:
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
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    Account.updateProfile(scanner, guest);
                    break;
                case 2:
                    bookingMenu(scanner); // Booking menu only accessible for guests
                    break;

                case 3:
                    roomServiceMenu(scanner); // Room service menu only accessible for guests
                break;

                case 4:
                    paymentMenu(scanner); // Payment menu only accessible for guests
                    break;
                case 5:
                    guest.logout();
                    return;
                default:
                    System.out.println("Invalid choice! Please select again.");
            }
        }
    }

    // Booking menu (Guest only)
    public static void bookingMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n=== Booking Menu ===");
            System.out.println("1. Create Booking");
            System.out.println("2. Update Booking");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View All Bookings");
            System.out.println("5. Back to Guest Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    Booking.createBooking(scanner, bookingList);
                    break;
                case 2:
                    Booking.updateBooking(scanner, bookingList);
                    break;
                case 3:
                    Booking.cancelBooking(scanner, bookingList);
                    break;
                case 4:
                    Booking.viewAllBookings(bookingList);
                    break;
                case 5:
                    return; // Back to Guest Menu
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static {
        // Add 3 Single Rooms
        for (int j = 0; j < 3; j++) {
            roomList.add(new Room("Single", 200));
        }

        // Add 3 Double Rooms
        for (int k = 0; k < 3; k++) {
            roomList.add(new Room("Double", 350));
        }

        // Add 3 Deluxe Rooms
        for (int l = 0; l < 3; l++) {
            roomList.add(new Room("Deluxe", 500));
        }
    }

    public static void paymentMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n=== Payment Menu ===");
            System.out.println("1. Process Payment");
            System.out.println("2. Cancel Payment");
            System.out.println("3. View Payments History");
            System.out.println("4. Back to Guest Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    // Ensure the logged-in user has bookings to pay for
                    if (bookingList.isEmpty()) {
                        System.out.println("You have no bookings to pay for. Please create a booking first.");
                        break;
                    }
                    // Process payment for a booking tied to the logged-in guest
                    System.out.print("Enter Booking ID to pay for: ");
                    String bid = scanner.nextLine();
                    Booking toPay = null;
                    for (Booking b : bookingList) {
                        if (b.getBookingID().equalsIgnoreCase(bid)) {
                            toPay = b;
                            break;
                        }
                    }
                    if (toPay == null) {
                        System.out.println("Booking ID not found.");
                        break;
                    }
                    // Create Payment for that booking
                    Payment payment = new Payment(0.0, 0, toPay);
                    payment.processPayment(scanner, paymentList);
                    // Store payment
                    paymentList.add(payment);
                    break;

                case 2:
                    new Payment().cancelPayment();
                    break;

                case 3:
                    new Payment().viewPaymentsHistory(paymentList);
                    break;

                case 4:
                    return; // Back to Guest Menu
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public static void roomServiceMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n=== Room Service Menu ===");
            System.out.println("1. Order Room Service");
            System.out.println("2. Cancel Room Service");
            System.out.println("3. Buy Breakfast Package");
            System.out.println("4. Cancel Lunch Package");
            System.out.println("5. View Room Service Status");
            System.out.println("6. Back to Guest Menu");
            System.out.print("Enter your choice: ");
            RoomService roomService = new RoomService();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

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
            }
        }
    }
}
