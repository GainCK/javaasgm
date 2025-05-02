//Author : Chuh Zhe Yong
//Module : Room Service Management
//System : Hotel Management System
//Group  : DFT1G11

public class RoomService {
    private Booking booking;
    private Payment payment;
    private Guest guest;
    private double fee;

    private boolean roomServiceActive;
    private boolean breakfastOrdered;

    private static int totalRoomServiceCalls = 0;
    private static double totalRoomServiceFee = 0.0;
    private static final double breakfastFee = 20.0;
    private static final double singleRoomServiceFee = 50.0;
    private static final double doubleRoomServiceFee = 70.0;
    private static final double deluxeRoomServiceFee = 100.0;

    public RoomService(Booking booking) {
        this.booking = booking;
        this.guest = booking != null ? booking.getGuest() : null;
        this.fee = 0.0;
        this.roomServiceActive = false;
        this.breakfastOrdered = false;
    }

    public RoomService() {
        this(null);
    }

    public Payment getPayment() {
        return payment;
    }

    public void connectPayment(Payment payment) {
        this.payment = payment;
    }

    public void callRoomService() {
        Room room = booking.getRoomType();
        if (guest == null || room == null) {
            System.out.println("Error: Guest or Room information is missing.");
            return;
        }

        if (!roomServiceActive) {
            roomServiceActive = true;
            double serviceFee = switch (room.getRoomType().toLowerCase()) {
                case "deluxe" -> deluxeRoomServiceFee;
                case "double" -> doubleRoomServiceFee;
                default -> singleRoomServiceFee;
            };
            fee += serviceFee;
            totalRoomServiceCalls++;
            totalRoomServiceFee += serviceFee;
            System.out.println("Room service called. Fee updated.");
        } else {
            System.out.println("Room service is already active.");
        }
    }

    public void cancelRoomService() {
        Room room = booking.getRoomType();
        if (guest == null || room == null) {
            System.out.println("Error: Guest or Room information is missing.");
            return;
        }

        if (roomServiceActive) {
            roomServiceActive = false;
            double serviceFee = switch (room.getRoomType().toLowerCase()) {
                case "deluxe" -> deluxeRoomServiceFee;
                case "double" -> doubleRoomServiceFee;
                default -> singleRoomServiceFee;
            };
            fee = Math.max(0.0, fee - serviceFee);
            totalRoomServiceFee = Math.max(0, totalRoomServiceFee - serviceFee);
            totalRoomServiceCalls = Math.max(0, totalRoomServiceCalls - 1);
            System.out.println("Room service cancelled.");
        } else {
            System.out.println("No active room service to cancel.");
        }
    }

    public void buyBreakfast() {
        Room room = booking.getRoomType();
        if (guest == null || room == null) {
            System.out.println("Error: Guest or Room information is missing.");
            return;
        }
        if (!breakfastOrdered) {
            breakfastOrdered = true;
            fee += breakfastFee;
            totalRoomServiceCalls++;
            totalRoomServiceFee += breakfastFee;
            System.out.println("Breakfast ordered.");
        } else {
            System.out.println("Breakfast already ordered.");
        }
    }

    public void cancelBreakfast() {
        Room room = booking.getRoomType();
        if (guest == null || room == null) {
            System.out.println("Error: Guest or Room information is missing.");
            return;
        }
        if (breakfastOrdered) {
            breakfastOrdered = false;
            fee = Math.max(0.0, fee - breakfastFee);
            totalRoomServiceFee = Math.max(0, totalRoomServiceFee - breakfastFee);
            totalRoomServiceCalls = Math.max(0, totalRoomServiceCalls - 1);
            System.out.println("Breakfast cancelled.");
        } else {
            System.out.println("No breakfast order to cancel.");
        }
    }

    public void addFeeToPayment() {
        Payment payment = booking.getPayment();
        if (payment == null) {
            System.out.println("No payment found for this booking.");
            return;
        }
        if (payment.getPaymentStatus().equalsIgnoreCase("Completed")) {
            System.out.println("Payment already completed. Cannot add additional fees.");
            return;
        }

        payment.setTotalPrice(payment.getTotalPrice() + fee);
        payment.setAmount(payment.getAmount() + fee);
        System.out.printf("Fee of RM %.2f added to payment.\nNew total: RM %.2f%n", fee, payment.getTotalPrice());

        fee = 0.0; 
    }

    public void displayServiceStatus() {
        Room room = booking.getRoomType();
        if (guest == null || room == null) {
            System.out.println("Error: Guest or Room information is missing.");
            return;
        }
        System.out.println("Room Service Status for Booking ID: " + booking.getBookingID());
        System.out.println("- Room Type: " + room.getRoomType());
        System.out.println("- Room Service Active: " + (roomServiceActive ? "Yes" : "No"));
        System.out.println("- Breakfast Ordered: " + (breakfastOrdered ? "Yes" : "No"));
        System.out.printf("- Current Additional Fee: RM %.2f%n", fee);
    }

    public static int getTotalRoomServiceCalls() {
        return totalRoomServiceCalls;
    }

    public static double getTotalRoomServiceFee() {
        return totalRoomServiceFee;
    }

    
}
