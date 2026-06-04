package model.observer;

import model.dto.PlayResultDTO;
import model.channel.AmpChannelStrategy;

public interface StudioObserver {
    void onPlayEvent(String rawRiff, AmpChannelStrategy currentChannel, PlayResultDTO result);
    void reset();
}
