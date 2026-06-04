package model.channel;

public class CleanChannel implements AmpChannelStrategy {
    @Override
    public String processTone(String riff) {
        return riff;
    }

    @Override
    public String getName() {
        return "CLEAN";
    }
}
