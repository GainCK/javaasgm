//Author : Chuh Zhe Yong
//Module : Payment
//System : Hotel Management System
//Group  : DFT1G11

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
        System.out.println("\n=== Payment Processing ===");
        
        System.out.println("Booking ID: " + booking.getBookingID());

        if (paymentStatus.equalsIgnoreCase("Completed")) {
            System.out.println("This payment has already been completed. You cannot pay again.");
            return;
        } else if (paymentStatus.equalsIgnoreCase("Cancelled")) {
            System.out.println("This payment has been cancelled. Please create a new booking to pay.");
            return;
        }
    
        // ✅ Apply pending room service fees
        RoomService roomService = booking.getRoomService();
        if (roomService != null) {
            roomService.addFeeToPayment();  // Adds to totalPrice and amount
        }
    
        // ✅ Make sure amount reflects updated total
        this.amount = this.totalPrice;
    
        System.out.printf("Amount needs to be paid: RM %.2f%n", amount);
    
        if (amount == 0.0) {
            System.out.println("No payment can be processed. Please book a room first.");
            return;
        }
     
        System.out.println("\nSelect your payment method:");
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
    
        System.out.println("\nPayment details:");
        viewPaymentDetails();
    }



    public void viewPaymentDetails() {
        System.out.println("=== Payment Details ===");
        System.out.println("Payment ID: " + getPaymentId());
        System.out.printf("Amount: RM %.2f%n" , amount);
        System.out.println("Payment Method: " + (paymentMethod == 1 ? "Cash" : paymentMethod == 2 ? "E-wallet" : "Card"));
        System.out.printf("Total Price: RM %.2f%n " ,totalPrice);
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
                System.out.printf("Amount: RM %.2f%n" , payment.getAmount());
                System.out.println("Payment Method: " + (payment.getPaymentMethod() == 1 ? "Cash" : payment.getPaymentMethod() == 2 ? "E-wallet" : "Card"));
                System.out.printf("Total Price: RM %.2f%n", payment.getTotalPrice());
                System.out.println("Payment Status: " + payment.getPaymentStatus());
                System.out.println("Booking ID: " + payment.getBooking().getBookingID());
                System.out.println("-------------------------");
            }
        }
    }
}
