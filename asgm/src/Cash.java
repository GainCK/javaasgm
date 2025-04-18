public class Cash extends Payment {

    private double cashAmount;
    
    public Cash() {
        super();
        cashAmount = 0.0;
    }
    
    public Cash(double amount, String paymentMethod, Booking booking, double cashAmount) {
        super(amount, paymentMethod, booking);
        this.cashAmount = cashAmount;
    }
    
    public double getCashAmount() {
        return cashAmount;
    }
    
    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }
    
    public void validateAmount(){
        if (cashAmount >= getTotalPrice()) {
            setPaymentStatus("Completed");
            getBooking().setPaymentStatus("Completed");
            getBooking().setBookingStatus("Confirmed");
            System.out.println("Cash payment successful! Booking confirmed.");
        } else {
            setPaymentStatus("Failed");
            System.out.println("Cash payment failed! Insufficient amount.");
        }
    
    
    }
    
    public void calculateChange() {
        double change = cashAmount - getTotalPrice();
        if (change > 0) {
            System.out.println("Change to be returned: " + change);
        } 
    }
    }