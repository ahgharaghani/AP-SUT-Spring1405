package models.users;

import models.Stay;
import java.util.ArrayList;
import java.util.List;

public class Host extends User {
    private String brand;
    private List<Stay> stays;

    public Host(String username, String password, String email, String brand) {
        super(username, password, email);
        this.brand = brand;
        this.stays = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "host";
    }

    public String getBrand() {
        return brand;
    }

    public List<Stay> getStays() {
        return stays;
    }

    public void addStay(Stay stay) {
        stays.add(stay);
    }
}