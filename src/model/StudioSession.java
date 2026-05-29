package model;

import model.dto.PlayResultDTO;
import model.observer.BannedRiffObserver;
import model.observer.StudioObserver;
import model.observer.TubeAmpHeatMonitor;

import java.util.LinkedList;
import java.util.List;

public class StudioSession {
    private static StudioSession instance = null;

    private boolean isPowerOn;
    private List<StudioObserver> engineers;

    private StudioSession() {
        isPowerOn = true;
        engineers = new LinkedList<>();
    }

    public static StudioSession getInstance() {
        if (instance == null) instance = new StudioSession();
        return instance;
    }

    public PlayResultDTO playRiff(GuitarRig rig, String rawRiff) {
        PlayResultDTO result = new PlayResultDTO();
        for (StudioObserver observer : engineers) {
            observer.onPlayEvent(rawRiff, rig.getChannel(), result);
        }

        if (result.isOverheated()) return result;

        String processedRiff = rig.processRiff(rawRiff);
        result.setProcessedRiff(processedRiff);
        return result;
    }

    public void setPower(boolean power) { isPowerOn = power; }

    public boolean isPowerOn() {
        return isPowerOn;
    }

    public void attachEngineer(StudioObserver engineer) {
        engineers.add(engineer);
    }

    public void resetAmpHeatMonitor() {
        for (StudioObserver observer : engineers) {
            if (observer instanceof TubeAmpHeatMonitor) observer.reset();
        }
    }
}
