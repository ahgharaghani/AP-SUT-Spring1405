package controllers;

import dto.StaySearchDTO;
import dto.StayStatsDTO;
import models.App;
import models.Menu;
import models.Stay;
import repositories.HostRepository;
import repositories.StayRepository;

import java.util.ArrayList;
import java.util.List;

public class ExploreController {
    private static ExploreController instance;
    private HostRepository hostRepo;
    private StayRepository stayRepo;
    private App app;

    private ExploreController() {
        this.app = App.getInstance();
        this.hostRepo = HostRepository.getInstance();
        this.stayRepo = StayRepository.getInstance();
    }

    public static ExploreController getInstance() {
        if (instance == null) instance = new ExploreController();
        return instance;
    }

    private boolean validateCurrentMenu() {
        return app.getCurrentMenu() == Menu.EXPLORE;
    }

    public List<StaySearchDTO> searchStays(String city, String guestsNumStr) {
        if (!validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        int guestsNum;
        try {
            guestsNum = Integer.parseInt(guestsNumStr);
            if (guestsNum <= 0) throw new IllegalArgumentException("invalid guests count.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("invalid guests count.");
        }

        List<StaySearchDTO> StaySearchesDTO = new ArrayList<>();
        List<Stay> stays = stayRepo.getStaysByCity(city);
        for (Stay stay : stays) {
            if (stay.getCapacity() < guestsNum) continue;
            StaySearchDTO dto = new StaySearchDTO(
                    StaySearchesDTO.size() + 1,
                    stay.getName(),
                    stay.getPricePerNight(),
                    hostRepo.getHostByName(stay.getHostUsername()).getBrand()
            );
            StaySearchesDTO.add(dto);
        }

        if (StaySearchesDTO.isEmpty()) return null;

        return StaySearchesDTO;
    }

    public StayStatsDTO showStayStats(String name) {
        if (!validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Stay stay = stayRepo.getStayByName(name);
        if (name == null) return null;

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
