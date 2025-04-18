public class Staff extends Account {

    // Constructors
    public Staff() {
        super();
    }

    public Staff(String name, String password, String birthday, String IC, String phoneNo, String email) {
        super(name, password, birthday, IC, phoneNo, email);
    }


    // Staff-specific methods
    public void checkInRoom() {
        System.out.println(getName() + " has checked in a room.");
    }

    public void checkOutRoom() {
        System.out.println(getName() + " has checked out a room.");
    }

    public void generateReport() {
        System.out.println(getName() + " is generating a report.");
    }

    public void updateStaffProfile() {
        System.out.println(getName() + " has updated their staff profile.");
    }
}
