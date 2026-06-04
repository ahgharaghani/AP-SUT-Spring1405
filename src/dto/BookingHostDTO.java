package dto;

public class BookingHostDTO {
    public int number;
    public String bookingID;
    public String stayName;
    public String guestName;
    public String fromDate;
    public String toDate;
    public int guestsNum;

    public BookingHostDTO(int num, String bookingID, String stayName, String guestName, String fromDate, String toDate, int guestsNum) {
        this.number = num;
        this.bookingID = bookingID;
        this.stayName = stayName;
        this.guestName = guestName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.guestsNum = guestsNum;
    }
}
