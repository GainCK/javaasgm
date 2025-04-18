public class Booking {
    private String custName;
    private Room roomType;
    private String checkInDate;
    private String checkOutDate;
    private double totalPrice;
    private String paymentStatus;
    private String bookingStatus;
    private static int bookingId = 001;

    public Booking(String custName, Room roomType, String checkInDate, String checkOutDate) {
        this.custName = custName;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = roomType.getPrice(); 
        this.paymentStatus = "Pending";
        this.bookingStatus = "Confirmed";
    }

    public String getCustName() {
        return custName;
    }

    public Room getRoomType() {
        return roomType;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public int getBookingId() {
        return bookingId++;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}