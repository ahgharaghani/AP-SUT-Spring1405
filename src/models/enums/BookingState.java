package models.enums;

public enum BookingState {
    REQUESTED("requested"),
    CONFIRMED("confirmed"),
    REJECTED("rejected"),
    COMPLETED("completed"),
    CANCELLED("cancelled");

    private final String displayName;

    BookingState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
