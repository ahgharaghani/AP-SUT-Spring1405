package model;

import model.channel.AmpChannelStrategy;
import model.pedal.EffectNode;

import java.util.LinkedList;
import java.util.List;

public class RigBuilder {
    private AmpChannelStrategy channel;
    private List<EffectNode> pedals;

    public RigBuilder() {
        channel = null;
        pedals = new LinkedList<>();
    }

    public RigBuilder addPedal(EffectNode pedal) {
        pedals.add(pedal);
        return this;
    }

    public RigBuilder setChannel(AmpChannelStrategy channel) {
        this.channel = channel;
        return this;
    }

    public GuitarRig build() {
        for (int i = 0; i < pedals.size() - 1; i++) {
            pedals.get(i).setNextNode(pedals.get(i + 1));
        }

        EffectNode headPedal = pedals.isEmpty() ? null : pedals.get(0);

        return new GuitarRig(channel, headPedal);
    }
}
