import java.util.Random;


public class Card extends Payment {

    private String cardNo;
    private String cardHolderName;
    private int cvv;
    private int pin;

    public Card() {
        super();
        cardNo = "";
        cardHolderName = "";
        cvv = 0;
        pin = 0;
    }


    public Card(double amount, String paymentMethod, Booking booking, String cardNo, String cardHolderName, int cvv, int pin) {
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
    public int getCvv() {
        return cvv;
    }
    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
    public int getPin() {
        return pin;
    }
    public void setPin(int pin) {
        this.pin = pin;
    }

    public void validateCard() {
        Random random = new Random(6);
        int randomPin = random.nextInt();
        if (pin == randomPin) {
            setPaymentStatus("Completed");
            getBooking().setPaymentStatus("Completed");
            getBooking().setBookingStatus("Confirmed");
            System.out.println("Card payment successful! Booking confirmed.");
        } else {
            setPaymentStatus("Failed");
            System.out.println("Card payment failed! Invalid pin.");
        }
    }

    
}
