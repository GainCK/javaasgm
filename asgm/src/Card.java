//Author : Chuh Zhe Yong
//Module : Card Payment
//System : Hotel Management System
//Group  : DFT1G11


import java.util.Random;
import java.util.Scanner;

public class Card extends Payment {

    private String cardNo;
    private String cardHolderName;
    private String cvv;
    private String pin;

    public Card() {
        super();
        cardNo = "";
        cardHolderName = "";
        cvv = "";
        pin = "";
    }

    public Card(double amount, int paymentMethod, Booking booking, String cardNo, String cardHolderName, String cvv, String pin) {
        super(amount, paymentMethod, booking);
        this.cardNo = cardNo;
        this.cardHolderName = cardHolderName;
        this.cvv = cvv;
        this.pin = pin;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void validateCard(Scanner scanner) {
        // Cardholder name validation
        while (true) {
            System.out.print("Enter Card Holder Name: ");
            cardHolderName = scanner.nextLine();
            if (cardHolderName.matches("[a-zA-Z ]+")) {
                break;
            } else {
                System.out.println("Invalid card holder name. Please enter a valid name.");
            }
        }

        // Card number validation
        while (true) {
            System.out.print("Enter Card Number (16-digit): ");
            cardNo = scanner.nextLine();
            if (cardNo.length() == 16) {
                break; // Valid card number
            } else {
                System.out.println("Invalid card number. Please enter a valid 16-digit card number.");
            }
        }

        // CVV validation
        while (true) {
            System.out.print("Enter CVV Number (3-digit): ");
            cvv = scanner.nextLine();
            if (cvv.length() == 3) {
                break; // Valid CVV
            } else {
                System.out.println("Invalid CVV number. Please enter a valid 3-digit CVV number.");
            }
        }

        // Pin validation
        while (true) {
            System.out.print("Enter Pin Number (6-digit): ");
            pin = scanner.nextLine();
            if (pin.length() == 6) {
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
        int attemptCount = 0; // Track the number of OTP attempts

        while (attemptCount < 3) {  // Limit the number of attempts to 3
            System.out.print("Enter your OTP: ");
            otp = scanner.nextInt();
            scanner.nextLine(); // Consume newline left by nextInt()

            if (otp == randomOtp) {
                setPaymentStatus("Completed");
                getBooking().setPaymentStatus("Completed");
                getBooking().setBookingStatus("Confirmed");
                System.out.println("Card payment successful! Booking confirmed.");
                break; // OTP correct, break out of the loop
            } else {
                attemptCount++;
                if (attemptCount < 3) {
                    System.out.println("Invalid OTP. Please try again.");
                } else {
                    setPaymentStatus("Failed");
                    System.out.println("Card payment failed! Invalid OTP. Please try again later.");
                }
            }
        }
    }
}
