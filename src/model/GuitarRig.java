package model;

import model.channel.AmpChannelStrategy;
import model.pedal.EffectNode;

public class GuitarRig {
    private AmpChannelStrategy channel;
    private EffectNode headPedal;

    public GuitarRig(AmpChannelStrategy channel, EffectNode headPedal) {
        this.channel = channel;
        this.headPedal = headPedal;
    }

    public void setChannel(AmpChannelStrategy channel) {
        this.channel = channel;
    }

    public AmpChannelStrategy getChannel() {
        return channel;
    }

    public String processRiff(String rawRiff) {
        String processed = channel.processTone(rawRiff);
        if (headPedal != null) {
            processed = headPedal.effect(processed);
        }
        return processed;
    }
}