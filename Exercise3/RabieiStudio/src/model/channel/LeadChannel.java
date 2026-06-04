package model.channel;

public class LeadChannel implements AmpChannelStrategy {
    @Override
    public String processTone(String riff) {
        return riff.toUpperCase();
    }

    @Override
    public String getName() {
        return "LEAD";
    }
}
