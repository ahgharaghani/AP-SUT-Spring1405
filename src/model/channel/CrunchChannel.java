package model.channel;

public class CrunchChannel implements AmpChannelStrategy {
    @Override
    public String processTone(String riff) {
        return "[CRUNCH] " + riff;
    }

    @Override
    public String getName() {
        return "CRUNCH";
    }
}
