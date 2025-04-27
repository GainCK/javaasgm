import java.util.ArrayList;
import java.util.Scanner;

public class Payment {
    private double amount;
    private int paymentMethod;
    private double totalPrice;
    private String paymentStatus;
    private Booking booking;
    private static int paymentId = 1001;

    public Payment() {
        this.amount = 0.0;
        this.paymentMethod = 0; // Default payment method
        this.totalPrice = 0.0;
        this.paymentStatus = "Pending"; // Initial payment status is "Pending"
        this.booking = null; // No booking associated initially
    }

    // Constructor
    public Payment(double amount, int paymentMethod, Booking booking) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.booking = booking;
        this.totalPrice = booking.getTotalPrice();  // Payment amount based on booking's total price
        this.paymentStatus = "Pending";  // Initial payment status is "Pending"
        paymentId++;  // Auto-increment payment ID
    }

    // Getters
    public double getAmount() {
        return amount;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public Booking getBooking() {
        return booking;
    }

    public int getPaymentId() {
        return paymentId;
    }

    // Setters
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Process payment function
    public void processPayment(Scanner scanner, ArrayList<Payment> paymentList) {
        System.out.println("\n=== Payment Processing ===");
        System.out.println("Amount needs to be paid: RM " + totalPrice);
        
        if (totalPrice == 0.0) {
            System.out.println("No payment can be processed. Please book a room first.");
            return;
        }

        System.out.println("\nSelect your payment method:");
        System.out.println("1. Cash");
        System.out.println("2. E-wallet");
        System.out.println("3. Card");
        System.out.print("Enter your choice (1-3): ");
        int paymentMethod = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        this.paymentMethod = paymentMethod;

        switch (paymentMethod) {
            case 1:
                Cash cash = new Cash();
                cash.validateAmount();
                cash.calculateChange();
                break;
            case 2:
                EWallet eWallet = new EWallet();
                eWallet.validateEWallet(scanner);
                break;
            case 3:
                Card card = new Card();
                card.validateCard(scanner);
                break;
            default:
                System.out.println("Invalid payment method! Please select again.");
                return;  // Return early in case of invalid choice
        }

        // After successful payment, update payment status and booking status
        paymentStatus = "Completed";
        booking.setPaymentStatus("Completed");
        booking.setBookingStatus("Confirmed");
        System.out.println("\nPayment details:");
        viewPaymentDetails();
    }

    // Cancel payment function
    public void cancelPayment() {
        System.out.println("\n=== Cancel Payment ===");
        if(booking == null) {
            System.out.println("No booking found. Cannot cancel payment.");
            return;
        }
        if (paymentStatus.equals("Completed")) {
            System.out.println("Payment already completed. Cannot cancel.");
            return;
        }
        paymentStatus = "Cancelled";
        booking.setPaymentStatus("Cancelled");
        booking.setBookingStatus("Cancelled");
        System.out.println("Payment cancelled! Booking cancelled.");
    }

    // View Payment Details
    public void viewPaymentDetails() {
        System.out.println("\n=== Payment Details ===");
        System.out.println("Payment ID: " + getPaymentId());
        System.out.println("Amount: RM " + amount);
        System.out.println("Payment Method: " + (paymentMethod == 1 ? "Cash" : paymentMethod == 2 ? "E-wallet" : "Card"));
        System.out.println("Total Price: RM " + totalPrice);
        System.out.println("Payment Status: " + paymentStatus);
        System.out.println("Booking ID: " + booking.getBookingID());
    }

    // View Payments History
    public void viewPaymentsHistory(ArrayList<Payment> paymentList) {
        System.out.println("\n=== Payments History ===");
        if (paymentList.isEmpty()) {
            System.out.println("No payments found.");
        } else {
            for (Payment payment : paymentList) {
                System.out.println("Payment ID: " + payment.getPaymentId());
                System.out.println("Amount: RM " + payment.getAmount());
                System.out.println("Payment Method: " + (payment.getPaymentMethod() == 1 ? "Cash" : payment.getPaymentMethod() == 2 ? "E-wallet" : "Card"));
                System.out.println("Total Price: RM " + payment.getTotalPrice());
                System.out.println("Payment Status: " + payment.getPaymentStatus());
                System.out.println("Booking ID: " + payment.getBooking().getBookingID());
                System.out.println("-------------------------");
            }
        }
    }
}
