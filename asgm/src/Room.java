public class Room {
    private String roomId; // Change to String to match roomID format
    private String roomType;
    private double price;
    private boolean status;  

    public Room(String roomType, double price, String roomId) {
        this.roomId = roomId; // Assign roomId from the parameter
        this.roomType = roomType;
        this.price = price;
        this.status = true;  
    }

    // Getters and Setters
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

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailable(boolean status) {
        this.status = status;
    }

    // Methods to change room status
    public void checkIn() {
        if (status) { 
            status = false;  // Change to occupied
            System.out.println("Room " + roomId + " is now occupied.");
        } else {
            System.out.println("Room " + roomId + " is already occupied.");
        }
    }

    public void checkOut() {
        if (!status) { 
            status = true;  // Change to available
            System.out.println("Room " + roomId + " is now available.");
        } else {
            System.out.println("Room " + roomId + " is already available.");
        }
    }

    // Display room info
    public void displayRoom() {
        System.out.println("Room ID: " + roomId +
                           ", Room Type: " + roomType +
                           ", Price: RM " + price +
                           ", Status: " + (status ? "Available" : "Not Available"));
    }
}