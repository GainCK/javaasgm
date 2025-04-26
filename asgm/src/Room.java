public class Room {
    private String roomType;
    private double price;
    private boolean status;
    private static int roomId = 1001;

    public Room(String roomType, double price) {
        this.roomType = roomType;
        this.price = price;
        this.status = true;
        roomId++;
    }


    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return status;
    }

    public void setAvailable(boolean status) {
        this.status = status;
    }

    public int getRoomId() {
        return roomId++;
    }
 
    public void updateRoom(String roomType, double price) {
        this.roomType = roomType;
        this.price = price;
        this.status = true;
    }

    public void displayRoom() {
        System.out.println(
                "Room Type: " + roomType + ", Price: " + price + ", Status: " + (status ? "Available" : "Not Available"));
    }
}

