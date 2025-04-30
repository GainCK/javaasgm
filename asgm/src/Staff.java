import java.util.Scanner;

public class Staff extends Account {

    private Report report;

    public Staff() {
        super();
      this.report = new Report(Main.bookingList);
    }

    public Staff(String name, String password, String birthday, String IC, String phoneNo, String email) {
        super(name, password, birthday, IC, phoneNo, email);
        this.report = new Report(Main.bookingList);
    }

    @Override
    public void displayAccountType() {
        System.out.println("Account Type: Staff");
    }

    public void checkInRoom(Scanner scanner) {
        System.out.println(getName() + " is checking in a room.");
        System.out.print("Enter Booking ID to check in: ");
        String bookingID = scanner.nextLine();

        Booking booking = findBookingByID(bookingID);
        if (booking == null) {
            System.out.println("Booking not found!");
            return;
        }

        System.out.println("Booking Details: ");
        System.out.println("Booking ID: " + booking.getBookingID() + " | Guest Name: " + booking.getCustName() + " | Room Type: " + booking.getRoomType());

        if (booking.getBookingStatus().equals("Confirmed")) {
            booking.setBookingStatus("Checked In");
            booking.getRoomType().setAvailable(false);
            System.out.println("Guest checked in successfully!");
        } else {
            System.out.println("Booking is already checked in or cancelled.");
        }
    }

    public void checkOutRoom(Scanner scanner) {
        System.out.println(getName() + " is checking out a room.");
        System.out.print("Enter Booking ID to check out: ");
        String bookingID = scanner.nextLine();

        Booking booking = findBookingByID(bookingID);
        if (booking == null) {
            System.out.println("Booking not found!");
            return;
        }

        if (booking.getBookingStatus().equals("Checked In")) {
            booking.setBookingStatus("Checked Out");
            booking.getRoomType().setAvailable(true);
            System.out.println("Guest checked out successfully!");
        } else {
            System.out.println("Booking is not checked in yet.");
        }
    }

    public void generateReport() {
        System.out.println(getName() + " is generating a report.");
        report.generateDailyReport();
        report.generateRoomTypeAnalysis();
        report.generateGuestReport();

    }

    

   

    public void updateStaffProfile() {
        System.out.println(getName() + " has updated their staff profile.");
    }

    private Booking findBookingByID(String bookingID) {
        for (Booking booking : Main.bookingList) {
            if (booking.getBookingID().equals(bookingID)) {
                return booking;
            }
        }
        return null;
    }
}
