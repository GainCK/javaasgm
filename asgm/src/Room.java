public class Room {
    private String roomId;
    private String roomType;
    private double price;
    private boolean availability;
    private String status;
    private String cleanlinessStatus = "Clean";

    public Room(String roomType, double price, String roomId) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.price = price;
        this.availability = true;  // Initially available
        this.status = "Available";
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
        return availability;
    }
    public String getStatus() {
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

    public void setAvailable(boolean availability) {
        this.availability = availability;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Status changes
    public void checkIn() {
        if (availability) {
            availability = false; // Mark as occupied
            System.out.println("Room " + roomId + " is already occupied.");
        } else {
            System.out.println("Room " + roomId + " is now occupied.");
        }
    }


    // Display info
    public void displayRoom() {
        System.out.println("Room ID: " + roomId +
                           ", Room Type: " + roomType +
                           ", Price: RM " + price +
                           ", Status: " + (availability ? "Available" : "Occupied") +
                           ", Cleanliness: " + cleanlinessStatus);
    }
}
