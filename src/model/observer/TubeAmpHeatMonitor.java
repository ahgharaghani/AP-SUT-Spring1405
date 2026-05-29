package model.observer;

import model.dto.PlayResultDTO;
import model.StudioSession;
import model.channel.AmpChannelStrategy;

public class TubeAmpHeatMonitor implements StudioObserver {
    private int heatLevel;

    public TubeAmpHeatMonitor() {
        this.heatLevel = 0;
    }

    @Override
    public void onPlayEvent(String rawRiff, AmpChannelStrategy currentChannel, PlayResultDTO result) {
        switch (currentChannel.getName()) {
            case "LEAD":
                heatLevel += 1; break;
            case "CLEAN":
                heatLevel -= 1; break;
        }
        if (heatLevel < 0) heatLevel = 0;

        result.setHeatLevel(heatLevel);
        if (heatLevel >= 5) {
            StudioSession.getInstance().setPower(false);
            result.setOverheated(true);
        }
    }

    public int getHeatLevel() { return heatLevel; }

    @Override
    public void reset() {
        heatLevel = 0;
    }
}
