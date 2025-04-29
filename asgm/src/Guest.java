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
