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
        this.guest = booking != null ? booking.getGuest() : null; // Initialize guest from booking
        this.fee = 0.0;
        this.roomServiceActive = false;
        this.breakfastOrdered = false;
    }

    public RoomService() {
        this(null);
    }

    public Payment getPayment()    {
        return payment;
    }

    public void connectPayment(Payment payment) {
        this.payment = payment;
    }

    public void callRoomService() {
        Room room = booking.getRoomType();
        if (guest == null || room == null) {
            System.out.println("Error: Guest or Room information is missing.");
            return;}

        if (!roomServiceActive) {
            roomServiceActive = true;
            switch (room.getRoomType().toLowerCase()) {
                case "deluxe":
                    fee += deluxeRoomServiceFee;
                    break;
                case "double":
                    fee += doubleRoomServiceFee;
                    break;
                case "single":
                default:
                    fee += singleRoomServiceFee;
                    break;
            }
            totalRoomServiceCalls++;
            totalRoomServiceFee += fee;
            System.out.println("Room service called. Fee updated.");
        } else {
            System.out.println("Room service is already active.");
        }
    }

    public void cancelRoomService() {
        Room room = booking.getRoomType();
        if (guest == null || room == null) {
            System.out.println("Error: Guest or Room information is missing.");
            return;        }

        if (roomServiceActive) {
            roomServiceActive = false;
            switch (room.getRoomType().toLowerCase()) {
                case "deluxe":
                    fee = Math.max(0.0, fee - deluxeRoomServiceFee);
                    break;
                case "double":
                    fee = Math.max(0.0, fee - doubleRoomServiceFee);
                    break;
                case "single":
                default:
                    fee = Math.max(0.0, fee - singleRoomServiceFee);
                    break;
            }
            totalRoomServiceFee = Math.max(0, totalRoomServiceFee - fee);
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
            return;        }
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
            return;        }
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
    
        double oldTotal = payment.getTotalPrice();
        double newTotal = oldTotal + fee;
    
        payment.setTotalPrice(newTotal);
        payment.setAmount(newTotal);  // 💡 add this to reflect new amount
    
        System.out.printf("Fee of RM %.2f added to payment. New total: RM %.2f\n", fee, newTotal);
        fee = 0.0;  // reset after applying
    }

    public void displayServiceStatus() {
        Room room = booking.getRoomType();
        if (guest == null || room == null) {
            System.out.println("Error: Guest or Room information is missing.");
            return;        }
        System.out.println("Room Service Status for Booking ID: " + booking.getBookingID());
        System.out.println("- Room Type: " + booking.getRoomType().getRoomType());
        System.out.println("- Room Service Active: " + (roomServiceActive ? "Yes" : "No"));
        System.out.println("- Breakfast Ordered: " + (breakfastOrdered ? "Yes" : "No"));
        System.out.printf("- Current Additional Fee: RM %.2f\n", fee);
    }

    public static int getTotalRoomServiceCalls() {
        return totalRoomServiceCalls;
    }

    public static double getTotalRoomServiceFee() {
        return totalRoomServiceFee;
    }
}
