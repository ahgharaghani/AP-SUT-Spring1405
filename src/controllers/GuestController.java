package controllers;

import models.App;
import models.Menu;
import models.users.Guest;
import models.users.Host;
import repositories.BookingRepository;
import repositories.GuestRepository;
import repositories.HostRepository;
import repositories.StayRepository;

public class GuestController {
    private static GuestController instance;
    private HostRepository hostRepo;
    private GuestRepository guestRepo;
    private StayRepository stayRepo;
    private BookingRepository bookingRepo;
    private final App app;

    private GuestController() {
        this.app = App.getInstance();
        this.hostRepo = HostRepository.getInstance();
        this.guestRepo = GuestRepository.getInstance();
        this.stayRepo = StayRepository.getInstance();
        this.bookingRepo = BookingRepository.getInstance();
    }

    public static GuestController getInstance() {
        if (instance == null) instance = new GuestController();
        return instance;
    }

    private boolean validateCurrentMenu() {
        return app.getCurrentMenu() == Menu.GUEST;
    }

    public String getBalance() {
        if (!app.isLoggedIn() || !app.isGuest() || !validateCurrentMenu()) return "invalid command";

        Guest guest = (Guest) app.getLoggedInUser();
        return "your balance: $" + guest.getBalance();
    }

    public String chargeAccount(String amountStr) {
        int amount;
        try {
            amount = Integer.parseInt(amountStr);
            if (amount <= 0) {
                return "Amount must be greater than 0.";
            }
        } catch (NumberFormatException e) {
            return "Amount must be greater than 0.";
        }

        Guest currentUser = (Guest) app.getLoggedInUser();
        currentUser.addBalance(amount);
        return "Account charged successfully. Current balance: $" + currentUser.getBalance();
    }
}
