public class Room {
    private String roomId;
    private String roomType;
    private double price;
    private boolean status;  // true = Available, false = Occupied
    private String cleanlinessStatus = "Clean";

    public Room(String roomType, double price, String roomId) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.price = price;
        this.status = true;  // Initially available
    }

    // Getters
    public String getRoomId() {
        return roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return status;
    }

    public String getCleanlinessStatus() {
        return cleanlinessStatus;
    }

    // Setters
    public void setCleanlinessStatus(String cleanlinessStatus) {
        this.cleanlinessStatus = cleanlinessStatus;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailable(boolean status) {
        this.status = status;
    }

    // Status changes
    public void checkIn() {
        if (status) {
            status = false; // Mark as occupied
            System.out.println("Room " + roomId + " is already occupied.");
        } else {
            System.out.println("Room " + roomId + " is now occupied.");
        }
    }

    public void checkOut() {
        if (!status) {
            status = true; // Mark as available
            cleanlinessStatus = "Dirty"; // Auto-set as dirty after checkout
            System.out.println("Room " + roomId + " has been checked out. Room marked as Dirty.");
        } else {
            System.out.println("Room " + roomId + " is already available.");
        }
    }

    // Display info
    public void displayRoom() {
        System.out.println("Room ID: " + roomId +
                           ", Room Type: " + roomType +
                           ", Price: RM " + price +
                           ", Status: " + (status ? "Available" : "Occupied") +
                           ", Cleanliness: " + cleanlinessStatus);
    }
}
