
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
        this.paymentMethod = 0;
        this.totalPrice = 0.0;
        this.paymentStatus = "Pending";
        this.booking = null;
    }

    public Payment(double amount, int paymentMethod, Booking booking) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.booking = booking;
        this.totalPrice = booking.getTotalPrice();
        this.paymentStatus = "Pending";
        paymentId++;
    }

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

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void processPayment(Scanner scanner, ArrayList<Payment> paymentList) {
        System.out.println("=== Payment Processing ===");

        if (paymentStatus.equalsIgnoreCase("Completed")) {
            System.out.println("This payment has already been completed. You cannot pay again.");
            return;
        } else if (paymentStatus.equalsIgnoreCase("Cancelled")) {
            System.out.println("This payment has been cancelled. Please create a new booking to pay.");
            return;
        }

        // ✅ Fetch and apply latest room service fee before processing
        RoomService roomService = booking.getRoomService();
        if (roomService != null) {
            roomService.addFeeToPayment();  // updates totalPrice and amount
        }

        // Show updated amount after service fee
        System.out.println("Amount needs to be paid: RM " + totalPrice);

        if (totalPrice == 0.0) {
            System.out.println("No payment can be processed. Please book a room first.");
            return;
        }

        System.out.println("Select your payment method:");
        System.out.println("1. Cash");
        System.out.println("2. E-wallet");
        System.out.println("3. Card");
        System.out.print("Enter your choice (1-3): ");
        int paymentMethod = scanner.nextInt();
        scanner.nextLine();

        this.paymentMethod = paymentMethod;

        switch (paymentMethod) {
            case 1:
                Cash cash = new Cash(amount, paymentMethod, booking, 0.0);
                cash.validateAmount();
                break;
            case 2:
                EWallet eWallet = new EWallet(amount, paymentMethod, booking, "", 0);
                eWallet.validateEWallet(scanner);
                break;
            case 3:
                Card card = new Card(amount, paymentMethod, booking, "", "", "", "");
                card.validateCard(scanner);
                break;
            default:
                System.out.println("Invalid payment method! Please select again.");
                return;
        }

        paymentStatus = "Completed";
        booking.setPaymentStatus("Completed");
        booking.setBookingStatus("Confirmed");

        System.out.println("Payment details:");
        viewPaymentDetails();
    }

    public void cancelPayment() {
        System.out.println("=== Cancel Payment ===");
        if (booking == null) {
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

    public void viewPaymentDetails() {
        System.out.println("=== Payment Details ===");
        System.out.println("Payment ID: " + getPaymentId());
        System.out.println("Amount: RM " + amount);
        System.out.println("Payment Method: " + (paymentMethod == 1 ? "Cash" : paymentMethod == 2 ? "E-wallet" : "Card"));
        System.out.println("Total Price: RM " + totalPrice);
        System.out.println("Payment Status: " + paymentStatus);
        System.out.println("Booking ID: " + booking.getBookingID());
    }

    public void viewPaymentsHistory(ArrayList<Payment> paymentList) {
        System.out.println("=== Payments History ===");
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
