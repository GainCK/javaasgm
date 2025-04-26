import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // Declare globally so all methods can use it
    static ArrayList<Booking> bookingList = new ArrayList<>();

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
                    staff.checkInRoom();
                    break;
                case 2:
                    staff.checkOutRoom();
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
            System.out.println("2. Booking Menu"); // Added this
            System.out.println("3. Logout");
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
}
