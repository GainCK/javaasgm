//Author : Chuh Zhe Yong
//Module : EWallet Payment
//System : Hotel Management System
//Group  : DFT1G11


import java.util.Random;
import java.util.Scanner;

public class EWallet extends Payment {

    private String phoneNo;
    private int pin;

    public EWallet() {
        super();
        this.phoneNo = "";
        this.pin = 0;
    }

    public EWallet(double amount, int paymentMethod, Booking booking, String phoneNo, int pin) {
        super(amount, paymentMethod, booking);
        this.phoneNo = phoneNo;
        this.pin = pin;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void validateEWallet(Scanner scanner) {
        // Phone number validation (must be numeric and 10 digits)
        while (true) {
            System.out.print("Enter Phone Number (10-digit): ");
            phoneNo = scanner.nextLine();

            if (phoneNo.matches("[0-9]{10}")) {  // Check for exactly 10 digits
                System.out.println("Valid phone number.");
                break; // Valid phone number
            } else {
                System.out.println("Invalid phone number. Please enter a valid 10-digit phone number.");
            }
        }

        // Pin validation (6-digit)
        while (true) {
            System.out.print("Enter Pin Number (6-digit): ");
            String pinInput = scanner.nextLine();

            if (pinInput.length() == 6 && pinInput.matches("[0-9]+")) {  // Ensure it's 6 digits
                pin = Integer.parseInt(pinInput);  // Convert to integer
                break; // Valid pin
            } else {
                System.out.println("Invalid pin number. Please enter a valid 6-digit pin number.");
            }
        }

        // OTP generation and validation
        Random random = new Random();
        int randomOtp = random.nextInt(900000) + 100000; // Generate a 6-digit OTP
        System.out.println("Your OTP is: " + randomOtp);

        int otp;
        int attemptCount = 0; // Track OTP attempts

        while (attemptCount < 3) {  // Limit attempts to 3
            System.out.print("Enter your OTP: ");
            otp = scanner.nextInt();
            scanner.nextLine(); // Consume newline left by nextInt()

            if (otp == randomOtp) {
                setPaymentStatus("Completed");
                getBooking().setPaymentStatus("Completed");
                getBooking().setBookingStatus("Confirmed");
                System.out.println("Payment successful! Booking confirmed.");
                break; // OTP correct, break out of the loop
            } else {
                attemptCount++;
                if (attemptCount < 3) {
                    System.out.println("Invalid OTP. Please try again.");
                } else {
                    setPaymentStatus("Failed");
                    System.out.println("Payment failed! Invalid OTP. Please try again later.");
                }
            }
        }
    }
}
