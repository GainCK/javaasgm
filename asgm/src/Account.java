import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account {
    private String name;
    private String password;
    private String birthday;
    private String IC;
    private String phoneNo;
    private String email;
    protected static List<Account> accountList = new ArrayList<>();

    // Constructors
    public Account() {}

    public Account(String name, String password, String birthday, String IC, String phoneNo, String email) {
        this.name = name;
        this.password = password;
        this.birthday = birthday;
        this.IC = IC;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }

    public String getIC() { return IC; }
    public void setIC(String IC) { this.IC = IC; }

    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Methods
    public void updateProfile(String name, String password, String birthday, String IC, String phoneNo, String email) {
        this.name = name;
        this.password = password;
        this.birthday = birthday;
        this.IC = IC;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    public void register(Account newAccount) {
        accountList.add(newAccount);
    }

    public Account login(String name, String password) {
        for (Account acc : accountList) {
            if (acc.loginValidation(name, password)) {
                return acc;
            }
        }
        return null;
    }

    public boolean loginValidation(String name, String password) {
        return this.name.equals(name) && this.password.equals(password);
    }

    public void deleteAccount() {
        accountList.remove(this);
    }

    public void logout() {
        System.out.println(this.name + " has logged out.");
    }
     // Method to validate IC (only numbers)
    public static boolean isValidIC(String ic) {
        String regex = "^[0-9]+$"; // Only numbers
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ic);
        return matcher.matches();
    }

    // Method to validate PhoneNo (only numbers)
    public static boolean isValidPhoneNo(String phoneNo) {
        String regex = "^[0-9]+$"; // Only numbers
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNo);
        return matcher.matches();
    }

    // Method to validate Birthday (dd/mm/yyyy format)
    public static boolean isValidBirthday(String birthday) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([0-9]{4})$"; // dd/mm/yyyy format
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(birthday);
        return matcher.matches();
    }

    // Method to validate Email (any valid email format)
    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // Valid email format
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Register method with validation
    public static void register(Scanner scanner) {
        System.out.println("\n=== Register ===");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Birthday (dd/mm/yyyy): ");
        String birthday = scanner.nextLine();
        while (!isValidBirthday(birthday)) {
            System.out.println("Invalid Birthday format! Please use dd/mm/yyyy.");
            System.out.print("Enter Birthday (dd/mm/yyyy): ");
            birthday = scanner.nextLine();
        }
        
        System.out.print("Enter IC (numbers only): ");
        String IC = scanner.nextLine();
        while (!isValidIC(IC)) {
            System.out.println("Invalid IC! Please enter numbers only.");
            System.out.print("Enter IC (numbers only): ");
            IC = scanner.nextLine();
        }
        
        System.out.print("Enter Phone No (numbers only): ");
        String phoneNo = scanner.nextLine();
        while (!isValidPhoneNo(phoneNo)) {
            System.out.println("Invalid Phone No! Please enter numbers only.");
            System.out.print("Enter Phone No (numbers only): ");
            phoneNo = scanner.nextLine();
        }
        
        System.out.print("Enter Email (e.g., username@domain.com): ");
        String email = scanner.nextLine();
        while (!isValidEmail(email)) {
            System.out.println("Invalid Email format! Please enter a valid email (e.g., username@domain.com).");
            System.out.print("Enter Email: ");
            email = scanner.nextLine();
        }

  // Determine if it's a Guest or Staff
    System.out.print("Enter Hidden Password (leave blank if Guest): ");
    String HP = scanner.nextLine();

// The hidden password to become Staff
    String hiddenStaffPassword = "5112155112"; 

    if (HP.isEmpty()) {
         Guest newGuest = new Guest(name, password, birthday, IC, phoneNo, email);
         newGuest.register(newGuest);
         System.out.println("Guest registration completed!");
    } else if (HP.equals(hiddenStaffPassword)) {
         Staff newStaff = new Staff(name, password, birthday, IC, phoneNo, email);
        newStaff.register(newStaff);
        System.out.println("Staff registration completed!");
    } else {
        System.out.println("Incorrect hidden password. Registering as Guest by default.");
        Guest newGuest = new Guest(name, password, birthday, IC, phoneNo, email);
        newGuest.register(newGuest);
        System.out.println("Guest registration completed!");
    }


        // Debugging: Print all accounts
        printAccountList();
    }
    public static void login(Scanner scanner) {
        System.out.println("\n=== Login ===");
        System.out.print("Enter your Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your Password: ");
        String password = scanner.nextLine();

        Account user = null;
        for (Account acc : Account.accountList) {
            if (acc.loginValidation(name, password)) {
                user = acc;
                break;
            }
        }
        if (user == null) {
            System.out.println("Invalid credentials. Returning to main menu.");
            return;
        }

        if (user instanceof Staff) {
            System.out.println("Login successful as Staff: " + user.getName());
            Main.staffMenu(scanner, (Staff) user);
        } else {
            System.out.println("Login successful as Guest: " + user.getName());
            Main.guestMenu(scanner, (Guest) user);
        }
    }
    public static void printAccountList() {
        System.out.println("\n=== Current Account List ===");
        for (Account acc : Account.accountList) {
            if (acc instanceof Staff) {
                System.out.println("Staff");
                System.out.println("---------------------------");
            } else {
                System.out.println("Guest");
                System.out.println("---------------------------");
            }
            System.out.println("Name: " + acc.getName());
            System.out.println("Password: " + acc.getPassword());
            System.out.println("Birthday: " + acc.getBirthday());
            System.out.println("IC: " + acc.getIC());
            System.out.println("Phone No: " + acc.getPhoneNo());
            System.out.println("Email: " + acc.getEmail());
            System.out.println("---------------------------");
        }
    }
     // Update profile method
     public static void updateProfile(Scanner scanner, Account loggedIn) {
        System.out.println("\n=== Update Your Profile ===");

        // Get and confirm new details
        System.out.print("New Name (leave blank to keep current): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) loggedIn.setName(newName);

        System.out.print("New Password (leave blank to keep current): ");
        String newPassword = scanner.nextLine();
        if (!newPassword.isEmpty()) loggedIn.setPassword(newPassword);

        System.out.print("New Birthday (dd/mm/yyyy, leave blank to keep current): ");
        String newBirthday = scanner.nextLine();
        if (!newBirthday.isEmpty() && isValidBirthday(newBirthday)) {
            loggedIn.setBirthday(newBirthday);
        }

        // Do not allow IC to be changed
        System.out.println("You cannot change your IC.");

        System.out.print("New Phone No (leave blank to keep current): ");
        String newPhoneNo = scanner.nextLine();
        if (!newPhoneNo.isEmpty() && isValidPhoneNo(newPhoneNo)) {
            loggedIn.setPhoneNo(newPhoneNo);
        }

        System.out.print("New Email (leave blank to keep current): ");
        String newEmail = scanner.nextLine();
        if (!newEmail.isEmpty() && isValidEmail(newEmail)) {
            loggedIn.setEmail(newEmail);
        }

        System.out.println("Profile updated successfully!");

        // Debugging: Print updated account list
        printAccountList();
    }
}
