public class Room {
    private static int roomCounter = 1001; // Static counter shared by all rooms

    private int roomId;    // Unique ID per room
    private String roomType;
    private double price;
    private boolean status;

    public Room(String roomType, double price) {
        this.roomId = roomCounter++; // Assign ID and increment counter
        this.roomType = roomType;
        this.price = price;
        this.status = true; // Available by default
    }

    // Getters
    public int getRoomId() {
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

    // Setters
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailable(boolean status) {
        this.status = status;
    }

    // Update room details
    public void updateRoom(String roomType, double price) {
        this.roomType = roomType;
        this.price = price;
        this.status = true;
    }

    // Display room info
    public void displayRoom() {
        System.out.println("Room ID: " + roomId +
                           ", Room Type: " + roomType +
                           ", Price: RM " + price +
                           ", Status: " + (status ? "Available" : "Not Available"));
    }
}
