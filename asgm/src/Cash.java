//Author : Chuh Zhe Yong
//Module : Cash Payment
//System : Hotel Management System
//Group  : DFT1G11

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
        System.out.printf("Amount to be paid: RM %.2f%n" , getAmount());

        while (true) {
            System.out.print("Enter payment amount: RM ");
            if (scanner.hasNextDouble()) {
                cashAmount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline

                if (cashAmount >= getAmount()) {
                    setPaymentStatus("Completed");
                    getBooking().setPaymentStatus("Completed");
                    getBooking().setBookingStatus("Confirmed");
                    System.out.println("Cash payment successful! Booking confirmed.");
                    calculateChange();
                    break;
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
        double change = cashAmount - getAmount();
        if (change > 0) {
            System.out.printf("Change to be returned: RM %.2f%n" , change);
        } else if (change == 0) {
            System.out.println("Exact amount received. No change to be returned.");
        }
    }
}
