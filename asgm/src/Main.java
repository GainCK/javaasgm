import java.util.Scanner;


public class Main {
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
            scanner.nextLine();  // Consume newline character

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
                default:
                    System.out.println("Invalid choice! Please select again.");
            }
        }
    }

   
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
            System.out.println("2. Logout");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    Account.updateProfile(scanner, guest);
                    break;
                case 2:
                    guest.logout();
                    return;
                default:
                    System.out.println("Invalid choice! Please select again.");
            }
        }
    }
    

   

}
