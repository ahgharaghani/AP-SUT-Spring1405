package controllers;

import dto.StaySearchDTO;
import dto.StayStatsDTO;
import models.App;
import models.enums.Menu;
import models.Repository;
import models.Stay;

import java.util.ArrayList;
import java.util.List;

public class ExploreController {
    private static ExploreController instance;

    private static boolean validateCurrentMenu() {
        return App.getCurrentMenu() == Menu.EXPLORE;
    }

    public static List<StaySearchDTO> searchStays(String city, String guestsNumStr) {
        if (!validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        int guestsNum;
        try {
            guestsNum = Integer.parseInt(guestsNumStr);
            if (guestsNum <= 0) throw new IllegalArgumentException("invalid guests count.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("invalid guests count.");
        }

        List<StaySearchDTO> StaySearchesDTO = new ArrayList<>();
        List<Stay> stays = Repository.getStaysByCity(city);
        for (Stay stay : stays) {
            if (stay.getCapacity() < guestsNum && !stay.isActive()) continue;
            StaySearchDTO dto = new StaySearchDTO(
                    StaySearchesDTO.size() + 1,
                    stay.getName(),
                    stay.getPricePerNight(),
                    Repository.getHostByName(stay.getHostUsername()).getBrand()
            );
            StaySearchesDTO.add(dto);
        }

        if (StaySearchesDTO.isEmpty()) return null;

        return StaySearchesDTO;
    }

    public static StayStatsDTO showStayStats(String name) {
        if (!validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Stay stay = Repository.getStayByName(name);
        if (stay == null) return null;

        return new StayStatsDTO(
                0,
                stay.getName(),
                stay.getCity(),
                stay.getAddress(),
                stay.getCapacity(),
                stay.getPricePerNight(),
                stay.getPolicy().getPolicyName(),
                (stay.isActive()) ? "active" : "inactive"
        );
    }
}
