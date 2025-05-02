//Author : Gain Chen Keat
//Module : User Management
//System : Hotel Management System
//Group  : DFT1G11

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Account {
    private String name;
    private String password;
    private String birthday;
    private String IC;
    private String phoneNo;
    private String email;
    protected static List<Account> accountList = new ArrayList<>();

    public Account() {
    }

    public Account(String name, String password, String birthday, String IC, String phoneNo, String email) {
        this.name = name;
        this.password = password;
        this.birthday = birthday;
        this.IC = IC;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    public abstract void displayAccountType();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIC() {
        return IC;
    }

    public void setIC(String IC) {
        this.IC = IC;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public static boolean isValidIC(String ic) {
        String regex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ic);
        return matcher.matches();
    }

    public static boolean isValidPhoneNo(String phoneNo) {
        String regex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNo);
        return matcher.matches();
    }

    public static boolean isValidBirthday(String birthday) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([0-9]{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(birthday);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void startRegistration(Scanner scanner) {
        System.out.println("\n=== Account Type Selection ===");
        System.out.println("1. Guest");
        System.out.println("2. Staff");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            registerGuest(scanner);
        } else if (choice.equals("2")) {
            registerStaff(scanner);
        } else {
            System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void registerGuest(Scanner scanner) {
        System.out.println("\n=== Guest Registration ===");
        String[] details = collectUserDetails(scanner);
        Guest guest = new Guest(details[0], details[1], details[2], details[3], details[4], details[5]);
        guest.register(guest);
        System.out.println("Guest registration completed!");
        printAccountList();
    }

    private static void registerStaff(Scanner scanner) {
        System.out.println("\n=== Staff Registration ===");
        String[] details = collectUserDetails(scanner);
        Staff staff = new Staff(details[0], details[1], details[2], details[3], details[4], details[5]);
        staff.register(staff);
        System.out.println("Staff registration completed!");
        printAccountList();
    }

    private static boolean isNameAlreadyUsed(String name) {
        for (Account acc : accountList) {
            if (acc.getName().equalsIgnoreCase(name)) {
                return true; // Name is already in use
            }
        }
        return false; // Name is not in use
    }
    
    private static String[] collectUserDetails(Scanner scanner) {
        // Validate Name
        System.out.print("Enter Name (letters only): ");
        String name = scanner.nextLine();
        while (!name.matches("[a-zA-Z ]+") || isNameAlreadyUsed(name)) { // Check if name is valid and not already used
            if (!name.matches("[a-zA-Z ]+")) {
                System.out.println("Invalid Name! Name can only contain letters.");
            } else {
                System.out.println("This name is already in use. Please choose a different name.");
            }
            System.out.print("Enter Name (letters only): ");
            name = scanner.nextLine();
        }
    
        // Validate Password
        System.out.print("Enter Password (more than 8 characters): ");
        String password = scanner.nextLine();
        while (password.length() < 8) { // Password must be more than 8 characters
            System.out.println("Invalid Password! Password must be more than 8 characters.");
            System.out.print("Enter Password (more than 8 characters): ");
            password = scanner.nextLine();
        }
    
        // Validate Birthday
        System.out.print("Enter Birthday (dd/mm/yyyy): ");
        String birthday = scanner.nextLine();
        while (!isValidBirthday(birthday)) {
            System.out.println("Invalid Birthday format! Please use dd/mm/yyyy.");
            System.out.print("Enter Birthday (dd/mm/yyyy): ");
            birthday = scanner.nextLine();
        }
    
        // Validate IC
        System.out.print("Enter IC (must be 12 digits): ");
        String IC = scanner.nextLine();
        while (!IC.matches("^\\d{12}$" )) { 
            System.out.println("Invalid IC! IC must contain must be 12 digits.");
            System.out.print("Enter IC (must be 12 digits): ");
            IC = scanner.nextLine();
        }
    
        // Validate Phone Number
        System.out.print("Enter Phone No (must be 10 digits): ");
        String phoneNo = scanner.nextLine();
        while (!phoneNo.matches("^\\d{10}$" )) { 
            System.out.println("Invalid Phone No! Phone number must contain must be 10 digits.");
            System.out.print("Enter Phone No (must be 10 digits): ");
            phoneNo = scanner.nextLine();
        }
    
        // Validate Email
        System.out.print("Enter Email (e.g., username@domain.com): ");
        String email = scanner.nextLine();
        while (!isValidEmail(email)) {
            System.out.println("Invalid Email format! Please enter a valid email (e.g., username@domain.com).");
            System.out.print("Enter Email (e.g., username@domain.com): ");
            email = scanner.nextLine();
        }
    
        return new String[] { name, password, birthday, IC, phoneNo, email };
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
            acc.displayAccountType();
            System.out.println("---------------------------");
            System.out.println("Name: " + acc.getName());
            System.out.println("Password: " + acc.getPassword());
            System.out.println("Birthday: " + acc.getBirthday());
            System.out.println("IC: " + acc.getIC());
            System.out.println("Phone No: " + acc.getPhoneNo());
            System.out.println("Email: " + acc.getEmail());
            System.out.println("---------------------------");
        }
    }

    public static void updateProfile(Scanner scanner, Account loggedIn) {
        System.out.println("\n=== Update Your Profile ===");

        System.out.print("New Name (leave blank to keep current): ");
        String newName = scanner.nextLine();
        while (!newName.isEmpty() && (isNameAlreadyUsed(newName) && !newName.equalsIgnoreCase(loggedIn.getName()))) {
            System.out.println("This name is already in use. Please choose a different name.");
            System.out.print("New Name (leave blank to keep current): ");
            newName = scanner.nextLine();
        }
        if (!newName.isEmpty()) {
            loggedIn.setName(newName);
        }

        System.out.print("New Password (leave blank to keep current): ");
        String newPassword = scanner.nextLine();
        while (!newPassword.isEmpty() && newPassword.length() < 8) { // Validate password length
            System.out.println("Invalid Password! Password must be more than 8 characters.");
            System.out.print("New Password (leave blank to keep current): ");
            newPassword = scanner.nextLine();
        }
        if (!newPassword.isEmpty()) {
            loggedIn.setPassword(newPassword);
        }

        System.out.print("New Birthday (dd/mm/yyyy, leave blank to keep current): ");
        String newBirthday = scanner.nextLine();
        while (!newBirthday.isEmpty() && !isValidBirthday(newBirthday)) {
            System.out.println("Invalid Birthday format! Please use dd/mm/yyyy.");
            System.out.print("New Birthday (dd/mm/yyyy, leave blank to keep current): ");
            newBirthday = scanner.nextLine();
        }
        if (!newBirthday.isEmpty()) {
            loggedIn.setBirthday(newBirthday);
        }

        System.out.println("You cannot change your IC.");

        System.out.print("New Phone No (leave blank to keep current): ");
        String newPhoneNo = scanner.nextLine();
        while (!newPhoneNo.isEmpty() && (!isValidPhoneNo(newPhoneNo) || newPhoneNo.length() != 10)) {
            if (!isValidPhoneNo(newPhoneNo)) {
                System.out.println("Invalid Phone No! Phone number must contain only digits.");
            } else if (newPhoneNo.length() != 10) {
                System.out.println("Invalid Phone No! Phone number must be exactly 10 digits.");
            }
            System.out.print("New Phone No (leave blank to keep current): ");
            newPhoneNo = scanner.nextLine();
        }
        if (!newPhoneNo.isEmpty()) {
            loggedIn.setPhoneNo(newPhoneNo);
        }

        System.out.print("New Email (leave blank to keep current): ");
        String newEmail = scanner.nextLine();
        while (!newEmail.isEmpty() && !isValidEmail(newEmail)) {
            System.out.println("Invalid Email format! Please enter a valid email (e.g., username@domain.com).");
            System.out.print("New Email (leave blank to keep current): ");
            newEmail = scanner.nextLine();
        }
        if (!newEmail.isEmpty()) {
            loggedIn.setEmail(newEmail);
        }

        System.out.println("Profile updated successfully!");
        printAccountList();
    }
}
