public class Payment {
    private double amount;
    private String paymentMethod;
    private double totalPrice;
    private String paymentStatus;
    private Booking booking;
    private static int paymentId = 1001;

    public Payment(){}

    public Payment(double amount, String paymentMethod, Booking booking) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.booking = booking;
        this.totalPrice = booking.getTotalPrice();
        this.paymentStatus = "Pending";
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
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
        return paymentId++;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void processPayment() {
        if (amount >= totalPrice) {
            paymentStatus = "Completed";
            booking.setPaymentStatus("Completed");
            booking.setBookingStatus("Confirmed");
            System.out.println("Payment successful! Booking confirmed.");
        } else {
            paymentStatus = "Failed";
            System.out.println("Payment failed! Insufficient amount.");
        }
    }

    public void cancelPayment() {
        paymentStatus = "Cancelled";
        booking.setPaymentStatus("Cancelled");
        booking.setBookingStatus("Cancelled");
        System.out.println("Payment cancelled! Booking cancelled.");
    }

    public void viewPaymentDetails() {
        System.out.println("Payment ID: " + getPaymentId());
        System.out.println("Amount: " + amount);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Payment Status: " + paymentStatus);
        System.out.println("Booking ID: " + booking.getBookingId());
    }
}
