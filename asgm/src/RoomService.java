public class RoomService {
    private Guest guest;
    private Room room;
    private double fee;
    private Payment payment;

    private boolean roomServiceActive;
    private boolean breakfastOrdered;

    private static int totalRoomServiceCalls = 0;  
    private static final double breakfastFee = 20.0; 
    private static final double singleRoomServiceFee = 50.0;
    private static final double doubleRoomServiceFee = 70.0;
    private static final double deluxeRoomServiceFee = 100.0;

    // Default constructor
    public RoomService() {
        this.guest = null;
        this.room = null;
        this.fee = 0.0;
        this.payment = null;
        this.roomServiceActive = false;
        this.breakfastOrdered = false;
    }

    // Constructor with guest and room
    public RoomService(Guest guest, Room room, double fee) {
        this.guest = guest;
        this.room = room;
        this.fee = fee;
        this.payment = null; // Payment can be linked later
        this.roomServiceActive = false;
        this.breakfastOrdered = false;
    }

    // Getter and Setter methods
    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    // Link a Payment object
    public void connectPayment(Payment payment) {
        this.payment = payment;
    }

    // Call room service with dynamic fee based on room type
    public void callRoomService() {
        if (guest == null || room == null) {
            System.out.println("Error: Guest or Room information is missing.");
            return;
        }
        if (!roomServiceActive) { // prevent double increment
            roomServiceActive = true;
            System.out.println("Room service called for " + guest.getName() + " in room " + room.getRoomId() + ".");

            // Adjust fee based on room type
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

            System.out.println("Additional fee added based on room type.");
            totalRoomServiceCalls++;
        } else {
            System.out.println("Room service is already active.");
        }
    }

    // Cancel room service
    public void cancelRoomService() {

        if (guest == null || room == null) {
            System.out.println("Error: Guest or Room information is missing.");
            return;
        }
        if (roomServiceActive) {
            roomServiceActive = false;
            System.out.println("Room service cancelled for " + guest.getName() + " in room " + room.getRoomId() + ".");

            // Adjust fee based on room type, ensuring no negative fee
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

            totalRoomServiceCalls = Math.max(0, totalRoomServiceCalls - 1); // Prevent negative calls
        } else {
            System.out.println("No active room service to cancel for " + guest.getName() + ".");
        }
    }

    // Buy breakfast
    public void buyBreakfast() {
        if (guest == null || room == null) {
            System.out.println("Error: Guest or Room information is missing.");
            return;
        }
        if (!breakfastOrdered) { // prevent double increment
            breakfastOrdered = true;
            System.out.println("Breakfast ordered for " + guest.getName() + " in room " + room.getRoomId() + ".");
            fee += breakfastFee;
            System.out.println("Additional fee of RM " + breakfastFee + " added.");
            totalRoomServiceCalls++;
        } else {
            System.out.println("Breakfast already ordered.");
        }
    }

    // Cancel breakfast
    public void cancelBreakfast() {
        if (guest == null || room == null) {
            System.out.println("Error: Guest or Room information is missing.");
            return;
        }
        if (breakfastOrdered) {
            breakfastOrdered = false;
            System.out.println("Breakfast cancelled for " + guest.getName() + " in room " + room.getRoomId() + ".");
            fee = Math.max(0.0, fee - breakfastFee); // Prevent negative fee
            totalRoomServiceCalls = Math.max(0, totalRoomServiceCalls - 1); // Prevent negative calls
        } else {
            System.out.println("No breakfast order to cancel for " + guest.getName() + ".");
        }
    }

    // Add fee to payment
    public void addFeeToPayment() {
        if (payment == null) {
            System.out.println("Error: No payment connected. Cannot add fees.");
            return;
        }
        payment.setTotalPrice(payment.getTotalPrice() + fee);
        System.out.printf("Fee of RM %.2f added to Payment ID %d.\n", fee, payment.getPaymentId());
        System.out.println("New Total Price: RM " + payment.getTotalPrice());
        fee = 0.0; // Reset fee after adding to payment
    }

    // Display current service status
    public void displayServiceStatus() {
        if (guest == null || room == null) {
            System.out.println("Guest or Room information is incomplete.");
            return;
        }
        System.out.println("Service Status for " + guest.getName() + " in Room " + room.getRoomId() + ":");
        System.out.println("- Room Service Active: " + (roomServiceActive ? "Yes" : "No"));
        System.out.println("- Breakfast Ordered: " + (breakfastOrdered ? "Yes" : "No"));
        System.out.println("- Current Additional Fee: RM " + fee);
    }

    // Get total room service calls across all guests
    public static int getTotalRoomServiceCalls() {
        return totalRoomServiceCalls;
    }
}
