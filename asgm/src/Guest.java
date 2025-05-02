//Author : Gain Chen Keat
//Module : Guest Management
//System : Hotel Management System
//Group  : DFT1G11

public class Guest extends Account {

    public Guest() {
        super();
    }

    public Guest(String name, String password, String birthday, String IC, String phoneNo, String email) {
        super(name, password, birthday, IC, phoneNo, email);
    }

    @Override
    public void displayAccountType() {
        System.out.println("Account Type: Guest");
    }
}
