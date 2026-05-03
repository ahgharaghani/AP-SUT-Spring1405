package dto;

public class BookingGuestDTO {
    public int num;
    public String bookingId;
    public String stayName;
    public String status;

    public BookingGuestDTO(int num, String bookingId, String stayName, String status) {
        this.num = num;
        this.bookingId = bookingId;
        this.stayName = stayName;
        this.status = status;
    }
}
