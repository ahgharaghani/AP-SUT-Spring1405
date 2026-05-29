package model.observer;

import model.dto.PlayResultDTO;
import model.channel.AmpChannelStrategy;

public class BannedRiffObserver implements StudioObserver {
    @Override
    public void onPlayEvent(String rawRiff, AmpChannelStrategy currentChannel, PlayResultDTO result) {
        if (rawRiff.contains("smoke on the water")
                || rawRiff.contains("sweet child o' mine")
                || rawRiff.contains("stairway to heaven")
                || rawRiff.contains("enter sandman")
        ) {
            result.setBanned(true);
        }
    }

    public void reset() {}
}
