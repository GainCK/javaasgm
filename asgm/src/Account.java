import java.util.ArrayList;
import java.util.List;

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
}
