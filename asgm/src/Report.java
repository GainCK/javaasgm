import java.util.ArrayList;
import java.text.DecimalFormat;

public class Report {
    private ArrayList<Booking> bookings;
    private DecimalFormat currencyFormat;
    
    public Report(ArrayList<Booking> bookings) {
        this.bookings = bookings;
        this.currencyFormat = new DecimalFormat("RM #,##0.00");
    }
    
    public void generateDailyReport() {
        double totalRevenue = 0;
        int singleRoomCount = 0;
        int doubleRoomCount = 0;
        int deluxeRoomCount = 0;
        double serviceRevenue = RoomService.getTotalRoomServiceFee();
        
        System.out.println("\n=== DAILY HOTEL REPORT ===");
        System.out.println("Date: " + java.time.LocalDate.now());
        System.out.println("----------------------------------");
        
        // Room type breakdown
        for (Booking booking : bookings) {
            totalRevenue += booking.getTotalPrice();
            
            switch (booking.getRoomType().getRoomType().toLowerCase()) {
                case "single":
                    singleRoomCount++;
                    break;
                case "double":
                    doubleRoomCount++;
                    break;
                case "deluxe":
                    deluxeRoomCount++;
                    break;
            }
        }
        
        // Add service revenue to total
        totalRevenue += serviceRevenue;
        
        System.out.println("Room Occupancy:");
        System.out.println("- Single Rooms: " + singleRoomCount);
        System.out.println("- Double Rooms: " + doubleRoomCount);
        System.out.println("- Deluxe Rooms: " + deluxeRoomCount);
        System.out.println("Total Rooms Booked: " + bookings.size());
        
        System.out.println("\nRevenue Breakdown:");
        System.out.println("- Room Revenue: " + currencyFormat.format(totalRevenue - serviceRevenue));
        System.out.println("- Service Revenue: " + currencyFormat.format(serviceRevenue));
        System.out.println("TOTAL REVENUE: " + currencyFormat.format(totalRevenue));
        
        System.out.println("\nAdditional Statistics:");
        System.out.println("- Total Room Service Calls: " + RoomService.getTotalRoomServiceCalls());
        System.out.println("----------------------------------");
    }
    
    public void generateBookingDetailsReport() {
        System.out.println("\n=== DETAILED BOOKING REPORT ===");
        System.out.printf("%-10s %-20s %-15s %-12s %-12s %-10s %-15s %-15s\n",
                         "BookingID", "Guest", "Room Type", "Check-In", "Check-Out", "Price", "Status", "Payment");
        
        for (Booking booking : bookings) {
            System.out.printf("%-10s %-20s %-15s %-12s %-12s %-10s %-15s %-15s\n",
                booking.getBookingID(),
                booking.getCustName(),
                booking.getRoomType().getRoomType(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                currencyFormat.format(booking.getTotalPrice()),
                booking.getBookingStatus(),
                booking.getPaymentStatus());
        }
    }
    
    public void generateRoomTypeAnalysis() {
        int[] roomCounts = new int[3]; // 0: single, 1: double, 2: deluxe
        double[] roomRevenue = new double[3];
        
        for (Booking booking : bookings) {
            String roomType = booking.getRoomType().getRoomType().toLowerCase();
            double price = booking.getTotalPrice();
            
            if (roomType.equals("single")) {
                roomCounts[0]++;
                roomRevenue[0] += price;
            } else if (roomType.equals("double")) {
                roomCounts[1]++;
                roomRevenue[1] += price;
            } else if (roomType.equals("deluxe")) {
                roomCounts[2]++;
                roomRevenue[2] += price;
            }
        }
        
       
    }
    
    public void generateGuestReport(ArrayList<Payment> paymentList) {
        System.out.println("\n=== Guest Payment Report ===");
    
        double grandTotal = 0.0;
    
        for (Payment payment : paymentList) {
            Booking booking = payment.getBooking();
            Guest guest = booking.getGuest();
    
            System.out.println("Guest Name: " + guest.getName());
            System.out.println("Booking ID: " + booking.getBookingID());
            System.out.printf("Room Price: RM %.2f%n", booking.getTotalPrice());
    
            double paidAmount = payment.getAmount();  // Already includes room service
            System.out.printf("Total Paid (with Room Service): RM %.2f%n", paidAmount);
            System.out.println("Payment Status: " + payment.getPaymentStatus());
            System.out.println("-----------------------------");
    
            grandTotal += paidAmount;
        }
    
        System.out.printf("Grand Total (All Guests): RM %.2f%n", grandTotal);
    }
    
}