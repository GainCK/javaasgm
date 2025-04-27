import java.util.Scanner;

public class Cash extends Payment {

    private double cashAmount;
    private Scanner scanner = new Scanner(System.in);

    public Cash() {
        super();
        cashAmount = 0.0;
    }

    public Cash(double amount, int paymentMethod, Booking booking, double cashAmount) {
        super(amount, paymentMethod, booking);
        this.cashAmount = cashAmount;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public void validateAmount() {
        System.out.println("Amount to be paid: RM " + getTotalPrice());

        while (true) {
            System.out.print("Enter payment amount: ");
            // Check if the entered value is a valid number
            if (scanner.hasNextDouble()) {
                cashAmount = scanner.nextDouble();
                scanner.nextLine(); // Consume leftover newline

                if (cashAmount >= getTotalPrice()) {
                    setPaymentStatus("Completed");
                    getBooking().setPaymentStatus("Completed");
                    getBooking().setBookingStatus("Confirmed");
                    System.out.println("Cash payment successful! Booking confirmed.");
                    calculateChange(); // Call change calculation after success
                    break; // Exit the loop
                } else {
                    System.out.println("Insufficient amount. Please re-enter.");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    public void calculateChange() {
        double change = cashAmount - getTotalPrice();
        if (change > 0) {
            System.out.println("Change to be returned: RM " + change);
        } else if (change == 0) {
            System.out.println("Exact amount received. No change to be returned.");
        }
    }
}
