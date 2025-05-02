//Author : Trisha Mae Low Kar Mun
//Module : Housekeeping Management
//System : Hotel Management System
//Group  : DFT1G11

import java.util.ArrayList;

public class HouseKeeping {
    // Attributes
    private ArrayList<Room> rooms;

    // Constructor
    public HouseKeeping() {
        this.rooms = new ArrayList<>();
    }


    // Display dirty rooms (status = false)
    public void showDirtyRooms() {
        System.out.println("Dirty Rooms:");
        for (Room room : rooms) {
            if (!room.isAvailable()) {
                room.displayRoom();
            }
        }
    }

    // Display room ID (based on getRoomId method)
    public void displayRoomID(Room room) {
        System.out.println("Room ID: " + room.getRoomId());
    }

    // Display room status
    public void displayRoomStatus(String status) {
        System.out.println("Room Status: " + status);
    }

    // Clean all dirty rooms
    public void cleanRooms() {
        for (Room room : rooms) {
            if (!room.isAvailable()) {
                room.setAvailable(true);
                System.out.println("Room cleaned: " + room.getRoomType());
            }
        }
    }
}
