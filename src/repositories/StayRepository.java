package repositories;

import models.Stay;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StayRepository {
    private static StayRepository instance;

    private List<Stay> stays;

    private StayRepository() {
        this.stays = new ArrayList<>();
    }

    public static StayRepository getInstance() {
        if (instance == null) instance = new StayRepository();
        return instance;
    }

    public void addStay(Stay newStay) {
        stays.add(newStay);
    }

    public List<Stay> getAllStays() {
        return new ArrayList<>(stays);
    }

    public Stay getStayByName(String name) {
        return stays.stream()
                .filter(stay -> stay.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Stay> getStaysByCity(String city) {
        return stays.stream()
                .filter(stay -> stay.getCity().equals(city))
                .collect(Collectors.toList());
    }
}
