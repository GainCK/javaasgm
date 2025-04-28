public class Room {
    private int roomId;    
    private String roomType;
    private double price;
    private boolean status;  
    private static int roomCount = 1001;
    private RoomService roomService; 

    public Room(String roomType, double price) {
        this.roomId = roomCount++;
        this.roomType = roomType;
        this.price = price;
        this.status = true;  
        this.roomService = new RoomService(); // Initialize room service
    }

    // Getters and Setters
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

    public RoomService getRoomService() {
        return roomService;
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

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
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
